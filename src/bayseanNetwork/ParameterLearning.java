package bayseanNetwork;

import java.util.ArrayList;

public class ParameterLearning {
	private DAG dag;
	ArrayList<int[][]> learned_parameters = new ArrayList<int[][]>();
	
	public ParameterLearning(DAG dag){
		this.dag = dag;
		
		//Creates a table for each node to record each teta
		for (int i = 0; i < this.dag.data_set.num_var; i++) {
			int [][] learned_parameter;
			try {
				learned_parameter = new int[this.dag.maxq(i + this.dag.data_set.num_var)][this.dag.data_set.ri[i]];
			} catch (NoParent e) {
				learned_parameter = new int[1][this.dag.data_set.ri[i]];
			}
			//Uncoment to see this DAG x and w
			//System.out.println("w: " + learned_parameter.length + " x: " + learned_parameter[0].length);
			learned_parameters.add(learned_parameter);
		}
	}
	
	
	

}
