package bayseanNetwork;

import java.util.ArrayList;

import fileRead.TestData;


/**
 * The Class ParameterLearning.
 */
public class ParameterLearning {
	
	/** The dag. */
	private DAG dag;
	
	/** The test_data. */
	private TestData test_data;
	
	/** The learned_parameters. List of matrix (One Matrix for each node, One Line for each parent configuration, One Collum for each value possible */
	private ArrayList<double[][]> learned_parameters = new ArrayList<double[][]>();
	
	/**
	 * Instantiates a new parameter learning.
	 * This constructor only ends after all tetas are calculated
	 *
	 * @param dag the dag
	 * @param test_data the test_data
	 */
	public ParameterLearning(DAG dag, TestData test_data){
		this.dag = dag;
		this.test_data = test_data;
		
		//Creates a table for each node to record each teta
		for (int i = 0; i < this.dag.data_set.getNum_var(); i++) {
			double [][] learned_parameter;
			try {
				learned_parameter = new double[this.dag.maxq(i + this.dag.data_set.getNum_var())][this.dag.data_set.getRi()[i]];
			} catch (NoParent e) {
				learned_parameter = new double[1][this.dag.data_set.getRi()[i]];
			}
			learned_parameters.add(learned_parameter);
		}
		
		learnTeta();
	}
	
	/**
	 * Learn teta for each node combination
	 * learned_parameter ArrayList is complete after this funtion.
	 */
	private void learnTeta(){
		int i = 0;
		int [] Nijk;
		for (double[][] learned_node : learned_parameters) {
			for (int j = 0; j < learned_node.length; j++) {
				for (int k = 0; k < learned_node[j].length; k++) {
					Nijk = dag.calcNijk(i + this.dag.data_set.getNum_var() , j, k);
					learned_node[j][k] = ((Nijk[0] + 0.5)/(Nijk[1]+dag.data_set.getRi()[i]*0.5));
				}
			}
			i++;
		}
	}
	
	/**
	 * Predict value for a node.
	 * Changes data string in test data
	 *
	 * @param node node to predict
	 */
	private void predictNode(int node){
		int [] data_t0 = new int[test_data.getNum_var()];
		
		for (int[] data : test_data.getData()) {
			for (int i = 0; i < data_t0.length; i++) {
				data_t0[i] = data[i];
				data[node] =  inferNode(data_t0, node);
			}
		}
	}
	
	/**
	 * Predict value for all nodes.
	 * Changes data string in test data
	 */
	private void predictAll() {
		for (int i = 0; i < test_data.getNum_var(); i++) {
			predictNode(dag.generalNode(i));
		}
		
	}
	
	/**
	 * Infer node.
	 *
	 * @param data_t0 - node values in t=0
	 * @param node the node
	 * @return most probable value for node
	 */
	private int inferNode(int[] data_t0, int node){
		int node_value = 0;
		double max_prob = 0, prob_aux = 0;
		
		for (int i = 0; i < dag.data_set.getRi()[dag.realNode(node)]; i++) {
			prob_aux = calcProb(data_t0, node, i);
			if (max_prob < prob_aux){
				max_prob = prob_aux;
				node_value = i;
			}
		}
		
		return node_value;
	}
	
	/**
	 * Calculates the prob of a node in t=1 having a value given the t=0 values.
	 *
	 * @param data_t0 - t=0 nodes value
	 * @param node - node t+1
	 * @param value - value of the node
	 * @return the double
	 */
	private double calcProb(int[] data_t0, int node, int value){
		double prob = 0;
		int[] data = new int[dag.data_set.getNum_var()*2];
		
		//Values in t=0
		for (int i = 0; i < data_t0.length; i++) {
			data[i] = data_t0[i];
		}
		
		//Value in this hypothese
		data[node] = value;
		
		prob = calcProbAux(data, node, dag.data_set.getNum_var() - 1);
		
		return prob;
	}
	
	/**
	 * Calc prob aux.
	 * Recursion Function to walk the three. Stop condition is next node being a leaf
	 *
	 * @param data - the value of the nodes (the value for the node corresponding to the node where this function is stoping by is changed)
	 * @param node - node t+1 to calcProb
	 * @param nodes_remaining - number of node until leaf
	 * @return the double
	 */
	private double calcProbAux(int[] data, int node, int nodes_remaining){
		double prob = 0;
		int actual_node = actualNode(node, nodes_remaining);
		nodes_remaining--;
		if (nodes_remaining != 0){
			//if next node is a var t+1
			for (int i = 0; i < dag.data_set.getRi()[dag.realNode(actual_node)]; i++) {
				data[actual_node] = i;
				prob += calcProbAux(data, node, nodes_remaining);
			}
		}else{
			//if next node is a leaf
			for (int i = 0; i < dag.data_set.getRi()[dag.realNode(actual_node)]; i++) {
				data[actual_node] = i;
				prob += calcProbNode(data, node);
			}
		}
		return prob;
	}
	
	/**
	 * Calculates the prob of a node t=1 having a value given all the information (values of t=0 and t=1).
	 *
	 * @param data - values of t and t+1
	 * @param node - node
	 * @return prob
	 */
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

	/**
	 * Actual node.
	 * Help function for recursion
	 *
	 * @param node the node
	 * @param nodes_remaining the nodes_remaining
	 * @return the int
	 */
	private int actualNode(int node, int nodes_remaining) {
		int actual_node;
		actual_node = dag.data_set.getNum_var()*2 - nodes_remaining;
		
		if (actual_node <= node){
			actual_node--;
		}
		
		return actual_node;
	}
	
	/**
	 * Prints the inference.
	 */
	public void printInference() {
		predictAll();
		int i = 1;
		for (int [] data : test_data.getData()) {
			System.out.print("-> instance " + i + ": ");
			for (int j = test_data.getNum_var(); j < data.length; j++) {
				if (j != test_data.getNum_var()) System.out.print(" , ");
				System.out.print(data[j]);
			}
			System.out.println("");
			i++;
		}
	}

	/**
	 * Prints the inference.
	 *
	 * @param generalNode the general node
	 */
	public void printInference(int generalNode) {
		predictNode(generalNode);
		int i = 1;
		for (int [] data : test_data.getData()) {
			System.out.println("-> instance " + i + ": " + data[generalNode]);
			i++;
		}
		
	}

	
	

}
