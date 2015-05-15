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
	
	int [] calcInference(int[] data_t0){
		int[] data_t1 = new int[data_t0.length];
		
		
		
		return data_t1;
	}
	
	
	

}
