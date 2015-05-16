package bayseanNetwork;

import java.util.ArrayList;

public class ParameterLearning {
	private DAG dag;
	public ArrayList<double[][]> learned_parameters = new ArrayList<double[][]>();
	
	public ParameterLearning(DAG dag){
		this.dag = dag;
		
		//Creates a table for each node to record each teta
		for (int i = 0; i < this.dag.data_set.num_var; i++) {
			double [][] learned_parameter;
			try {
				learned_parameter = new double[this.dag.maxq(i + this.dag.data_set.num_var)][this.dag.data_set.ri[i]];
			} catch (NoParent e) {
				learned_parameter = new double[1][this.dag.data_set.ri[i]];
			}
			//Uncoment to see this DAG x and w
			//System.out.println("w: " + learned_parameter.length + " x: " + learned_parameter[0].length);
			learned_parameters.add(learned_parameter);
		}
	}
	
	//Learn the parameter for each node combination
	public void learnTeta(){
		int i = 0;
		int [] Nijk;
		for (double[][] learned_node : learned_parameters) {
			for (int j = 0; j < learned_node.length; j++) {
				for (int k = 0; k < learned_node[j].length; k++) {
					Nijk = dag.data_set.calcNijk(i + this.dag.data_set.num_var , j, k);
					learned_node[j][k] = ((Nijk[0] + 0.5)/(Nijk[1]+dag.data_set.ri[i]*0.5));
				}
			}
			i++;
		}
	}
	
	//Returns the most probable value for that node given the t=0 values
	public int inferNode(int[] data_t0, int node){
		int node_value = 0;
		double max_prob = 0, prob_aux = 0;
		
		for (int i = 0; i < dag.data_set.ri[dag.realNode(node)]; i++) {
			prob_aux = calcProb(data_t0, node, i);
			if (max_prob < prob_aux){
				max_prob = prob_aux;
				node_value = i;
			}
		}
		
		return node_value;
	}
	
	public void predictNode(TestData test_data, int node){
		int [] data_t0 = new int[test_data.num_var];
		
		for (int[] data : test_data.data) {
			for (int i = 0; i < data_t0.length; i++) {
				data_t0[i] = data[i];
				data[node] =  inferNode(data_t0, node);
			}
		}
	}
	
	public void predictAll(TestData test_data) {
		for (int i = 0; i < test_data.num_var; i++) {
			predictNode(test_data, dag.generalNode(i));
		}
		
	}
	
	//Calculates the prob of a node in t=1 having a value given the t=0 values
	private double calcProb(int[] data_t0, int node, int value){
		double prob = 0;
		int[] data = new int[dag.data_set.num_var*2];
		
		//Values in t=0
		for (int i = 0; i < data_t0.length; i++) {
			data[i] = data_t0[i];
		}
		
		//Value in this hypothese
		data[node] = value;
		
		prob = calcProbAux(data, node, dag.data_set.num_var - 1);
		
		return prob;
	}
	
	//Recursion Function
	private double calcProbAux(int[] data, int node, int nodes_remaining){
		double prob = 0;
		int actual_node = actualNode(node, nodes_remaining);
		nodes_remaining--;
		if (nodes_remaining != 0){
			for (int i = 0; i < dag.data_set.ri[dag.realNode(actual_node)]; i++) {
				data[actual_node] = i;
				prob += calcProbAux(data, node, nodes_remaining);
			}
		}else{
			for (int i = 0; i < dag.data_set.ri[dag.realNode(actual_node)]; i++) {
				data[actual_node] = i;
				prob += calcProbNode(data, node);
			}
		}
		return prob;
	}
	

	private int actualNode(int node, int nodes_remaining) {
		int actual_node;
		actual_node = dag.data_set.num_var*2 - nodes_remaining;
		
		if (actual_node <= node){
			actual_node--;
		}
		
		return actual_node;
	}

	//Calculates the prob of a node t=1 having a value given all the information (values of t=0 and t=1)
	private double calcProbNode(int[] data, int node){
		double prob;
		int parent_configuration = 0;
		
		int real_node = dag.realNode(node);
		int num_parents = dag.numParents(real_node);
		
		if (num_parents != 0){
			int [][] ri_parents = dag.riParents(real_node, num_parents);
			
			for (int i = 0; i < ri_parents.length; i++) {
				ri_parents[i][1] = data[ri_parents[i][0]];
			}	
			parent_configuration = dag.toParentConfiguration(node, ri_parents);
		}
		
		prob = learned_parameters.get(real_node)[parent_configuration][data[node]];
		for (int i = 0; i < data.length/2; i++) {
			if (i == dag.realNode(node)) continue;
			int parent_configuration_aux = 0;
			int num_parents_aux = dag.numParents(i);
			
			if (num_parents_aux != 0){
				int [][] ri_parents_aux = dag.riParents(i, num_parents_aux);
				
				for (int i1 = 0; i1 < ri_parents_aux.length; i1++) {
					ri_parents_aux[i1][1] = data[ri_parents_aux[i1][0]];
				}	
				parent_configuration_aux = dag.toParentConfiguration(dag.generalNode(i), ri_parents_aux);
			}
			prob = prob * learned_parameters.get(i)[parent_configuration_aux][data[dag.generalNode(i)]];
		}
		
		return prob;
	}

	

}
