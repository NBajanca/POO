package userInterface;

import bayseanNetwork.*;

public class Main {

	public static void main(String[] args) {
		
		if (args.length != 5){
			System.out.println("Correct program call has the following arguments: train test score randrest var");
			System.err.println("[1] NUMBER OF ARGUMENTS IS INVALID");
			System.exit(1);
		}
		
		String train = args[0];
		if (train.matches(".*\\.csv") != true){
			System.out.println("Correct train argument should be a file .csv");
			System.err.println("[2] INVALID TRAIN FILE FORMAT");
			System.exit(2);
		}
			
		String test = args[1];
		if (test.matches(".*\\.csv") != true){
			System.out.println("Correct test argument should be a file .csv");
			System.err.println("[3] INVALID TEST FILE FORMAT");
			System.exit(3);
		}
		
		String score = args[2];
		if (score.matches("LL") != true && score.matches("MDL") != true){
			System.out.println("Correct score argument should be \"LL\" or \"MDL\"");
			System.err.println("[4] INVALID SCORE ARGUMENT");
			System.exit(4);
		}
		
		try {
			@SuppressWarnings("unused")
			int randrest = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			System.out.println("randrest argument should be a number");
			System.err.println("[5] INVALID RANDREST ARGUMENT");
			System.exit(5);
		}
		
		try {
			@SuppressWarnings("unused")
			int var = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			System.out.println("var argument should be a number");
			System.err.println("[6] INVALID VAR ARGUMENT");
			System.exit(6);
		}
		
	DataSet data_set= new DataSet(args[0]);
	
	//Debug only
//	System.out.println(data_set.ri[0]+ "," + data_set.ri[1]+ ","+ data_set.ri[2]);
	//Delete after debug
	
	data_set.dag = new DAG(data_set);
	
	//Debug only
//	System.out.println(data_set.dag.toString());
	//Delete after debug
	
	//For test
//	int[][] parent_configuration = null;
//	try{
//		parent_configuration = data_set.dag.fromParentConfiguration(5,2);
//	}catch (PCInvalid e){
//		e.printStackTrace();
//	} catch (NoParent e) {
//		System.out.println("no parents");
//	}
//	
//	for (int i = 0; i < parent_configuration.length; i++) {
//		System.out.println("parent " + parent_configuration[i][0] + ", configuration: " + parent_configuration[i][1]);
//	}
//	
//	
//	
//	data_set.dag.toParentConfiguration (5, parent_configuration);
	//Delete after debug
	
	//Debug only
//	int[] counter = data_set.calcNijk(5, 2 , 2);
//	System.out.println("Nijk = " + counter[0] + " Nij = " + counter[1] );
	//Delete after debug
	
	
	ParameterLearning parameter_learning = new ParameterLearning(data_set.dag);
	parameter_learning.learnTeta();
	
	//Debug only
//	int i = 0;
//	for (double[][] learned_node : parameter_learning.learned_parameters) {
//		for (int j = 0; j < learned_node.length; j++) {
//			for (int k = 0; k < learned_node[j].length; k++) {
//				System.out.println("Teta"+i+j+k+ " = " + learned_node[j][k]);
//			}
//		}
//		i++;
//	}
	//Delete after debug
	
	//int[] test_data_complete = new int[]{1,0,2,1,3,2};
	//System.out.println(parameter_learning.calcProbNode(test_data_complete, 3));
	
	int[] test_data = new int[]{1,0,2};
	System.out.println(parameter_learning.calcProb(test_data, 3, 1));
	
	System.exit(0);
	}

}
