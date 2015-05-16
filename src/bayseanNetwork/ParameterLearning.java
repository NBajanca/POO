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
	
//	double calcProb(int[] data_t0, int node, int value){
//		double prob = 0, prob_aux = 1, parent_configuration = 1;
//		
//		int real_node = dag.realNode(node), real_node_aux;
//		int num_parents = dag.numParents(real_node), num_parents_aux;
//		
//		//if (num_parents == 0)
//		int [][] ri_parents = dag.riParents(real_node, num_parents);
//		int [][] ri_parents_aux;
//		
//		for (int i = 0; i < dag.data_set.num_var ; i++) {
//			if (i == node) continue;
//			for (int j = 0; j < dag.data_set.ri[i]; j++) {
//				for (int i1 = 0; i1 < dag.data_set.num_var ; i1++) {
//					if (i1 == node) continue;
//					real_node_aux = dag.realNode(i1);
//					num_parents_aux = dag.numParents(real_node_aux); //falta para sem pais
//					ri_parents_aux = dag.riParents(real_node_aux, num_parents_aux);
//					
//					for (int k = 0; k < ri_parents_aux.length; k++) {
//						if (k < dag.data_set.num_var){
//							ri_parents_aux[k][1] = data_t0[k];
//						}else{
//							ri_parents_aux[k][1] = 
//						}
//					}
//					
//					parent_configuration = dag.toParentConfiguration( real_node_aux, parent_configuration)
//					prob_aux = 
//				}
//			}
//			
//		}
//		
//		
//		return prob;
//	}
	
	//Calculates the prob of a node in t=1 having a value given the t=0 values
	public double calcProb(int[] data_t0, int node, int value){
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
	public double calcProbNode(int[] data, int node){
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
