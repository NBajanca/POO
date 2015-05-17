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
	System.out.print("Paramaters: " + train + " " +  test + " " + score + " " + randrest);
	
	if (var != -1){
		System.out.println(" " + var);
	}else{
		System.out.println("");
	}
	
	//Read Files
	DataSet data_set= new DataSet(train);
	TestData test_data = new TestData(test,data_set.num_var);
	
	//Building the DAG
	long start_time = System.nanoTime();
	Score score_algorithm = null;
	
	switch (score) {
	case "MDL":
		score_algorithm = new MDL();
		break;
	case "LL":
		score_algorithm = new LL();
		break;
	}
	
	DAG dag = new DAG(data_set,score_algorithm, randrest);
	long end_time = System.nanoTime();
	
	//Printing DAG Time
	System.out.println("Building DBN: " + (end_time - start_time)/1000000 + "ms");
	
	//Printing Transition network
	System.out.println("Transition network:");
	
	System.out.println("=== Inter-slice connectivity");
	printNetwork(0, dag);
	
	System.out.println("=== Intra-slice connectivity");
	printNetwork(1, dag);
	
	System.out.println("=== Scores");
	Score score_algorithm_aux = new LL();
	System.out.println("LL " + score_algorithm_aux.compute(dag));
	
	score_algorithm_aux = new MDL();
	System.out.println("MDL " + score_algorithm_aux.compute(dag));
	
	//Starting Inference
	start_time = System.nanoTime();
	System.out.println("Performing inference: ");
	ParameterLearning parameter_learning = new ParameterLearning(dag);
	parameter_learning.learnTeta();
	
	if (var != -1){
		parameter_learning.predictNode(test_data, dag.generalNode(var));
		printInference(test_data,  dag.generalNode(var));
	}else{
		parameter_learning.predictAll(test_data);
		printInference(test_data);
	}
	
	end_time = System.nanoTime();
	
	//Printing DAG Time
	System.out.println("Infering with DBN: " + (end_time - start_time)/1000000 + "ms");

	
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

	private static void printNetwork(int network, DAG dag) {
		for (int i = 0; i < dag.data_set.num_var; i++) {
			System.out.print(i +  " : ");
			int num_parents = dag.numParents(i);
			int first_time = 0;
			
			//no parents means empty parent configuration
			if (num_parents == 0){
				System.out.println("");
			}else{
				int [][] ri_parents = dag.riParents(i, num_parents);
				for (int j = 0; j < ri_parents.length; j++) {
					if (ri_parents[j][0] >= dag.data_set.num_var && network == 0) break;
					if (ri_parents[j][0] < dag.data_set.num_var && network == 1) continue;
					else first_time ++;
					if ( (j !=0 && network == 0) || (first_time > 1 && network == 1)) System.out.print(" , ");
					System.out.print(ri_parents[j][0]);
				}
				System.out.println("");
			}
		}
		
	}

}
