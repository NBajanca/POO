package userInterface;

import bayseanNetwork.*;

public class Main {

	public static void main(String[] args) {
		
		if (args.length < 4){
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
		
		int randrest = 0;
		try {
			randrest = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			System.out.println("randrest argument should be a number");
			System.err.println("[5] INVALID RANDREST ARGUMENT");
			System.exit(5);
		}
		
		int var = 0;
		try {
			var = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			System.out.println("var argument should be a number");
			System.err.println("[6] INVALID VAR ARGUMENT");
			System.exit(6);
		}catch (ArrayIndexOutOfBoundsException e){
			var = -1;
		}
	
	// Print parameters
	System.out.println("Paramaters: " + train + " " +  test + " " + score + " " + randrest + " " + var);
	
	//Read Files
	DataSet data_set= new DataSet(train);
	TestData test_data = new TestData(test,data_set.num_var);
	
	//Debug only
//	data_set.toString();
//	test_data.toString();
	//Delete after debug
	
	//Building the DAG
	long start_time = System.nanoTime();
	data_set.dag = new DAG(data_set);
	long end_time = System.nanoTime();
	
	//Printing DAG Time
	System.out.println("Building DBN: " + (end_time - start_time)/1000000 + "ms");
	
	//Pirnting Transition network
	System.out.println("Transition network:");
	
	System.out.println("=== Inter-slice connectivity");
	printNetwork(0, data_set);
	
	System.out.println("=== Intra-slice connectivity");
	printNetwork(1, data_set);
	
	System.out.println("=== Scores");
	Score score_algorithm_aux = new LL();
	System.out.println("LL " + score_algorithm_aux.compute(data_set.dag));
	
	score_algorithm_aux = new MDL();
	System.out.println("MDL " + score_algorithm_aux.compute(data_set.dag));
	
	
	
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
	
	
	//Starting Inference
	start_time = System.nanoTime();
	System.out.println("Performing inference: ");
	ParameterLearning parameter_learning = new ParameterLearning(data_set.dag);
	parameter_learning.learnTeta();
	
	if (var != -1){
		System.out.println("node: " + data_set.dag.generalNode(var));
		parameter_learning.predictNode(test_data, data_set.dag.generalNode(var));
		printInference(test_data,  data_set.dag.generalNode(var));
	}else{
		parameter_learning.predictAll(test_data);
		printInference(test_data);
	}
	
	end_time = System.nanoTime();
	
	//Printing DAG Time
	System.out.println("Infering with DBN: " + (end_time - start_time)/1000000 + "ms");
	
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
	
	
	//Debug only
//	int[] test_data1 = new int[]{1,3,2};
//	System.out.println(parameter_learning.inferNode(test_data1, 5));
	//Delete after debug
	
	
//	Score score_algorithm = new LL(data_set.dag);
//	System.out.println("LL: " + score_algorithm.compute());
//	
//	score_algorithm = new MDL(data_set.dag);
//	System.out.println("MDL: " + score_algorithm.compute());
	
	
	System.exit(0);
	}

	private static void printInference(TestData test_data) {
		int i = 1;
		for (int [] data : test_data.data) {
			System.out.print("-> instance " + i + ": ");
			for (int j = test_data.num_var; j < data.length; j++) {
				if (j != test_data.num_var) System.out.print(" , ");
				System.out.print(data[j]);
			}
			System.out.println("");
			i++;
		}
	}

	private static void printInference(TestData test_data, int generalNode) {
		int i = 1;
		for (int [] data : test_data.data) {
			System.out.println("-> instance " + i + ": " + data[generalNode]);
			i++;
		}
		
	}

	private static void printNetwork(int network, DataSet data_set) {
		for (int i = 0; i < data_set.num_var; i++) {
			System.out.print(i +  " : ");
			int num_parents = data_set.dag.numParents(i);
			int first_time = 0;
			
			//no parents means empty parent configuration
			if (num_parents == 0){
				System.out.println("");
			}else{
				int [][] ri_parents = data_set.dag.riParents(i, num_parents);
				for (int j = 0; j < ri_parents.length; j++) {
					if (ri_parents[j][0] >= data_set.num_var && network == 0) break;
					if (ri_parents[j][0] < data_set.num_var && network == 1) continue;
					else first_time ++;
					if ( (j !=0 && network == 0) || (first_time > 1 && network == 1)) System.out.print(" , ");
					System.out.print(ri_parents[j][0]);
				}
				System.out.println("");
			}
		}
		
	}

}
