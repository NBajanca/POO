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
	TestData test_data = new TestData(test,data_set.getNum_var());
	
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
	
	System.out.print(dag.toString());
	
	System.out.println("=== Scores");
	Score score_algorithm_aux = new LL();
	System.out.println("LL " + score_algorithm_aux.compute(dag));
	
	score_algorithm_aux = new MDL();
	System.out.println("MDL " + score_algorithm_aux.compute(dag));
	
	//Starting Inference
	start_time = System.nanoTime();
	System.out.println("Performing inference: ");
	ParameterLearning parameter_learning = new ParameterLearning(dag, test_data);
	
	if (var != -1){
		parameter_learning.printInference(dag.generalNode(var));
	}else{
		parameter_learning.printInference();
	}
	
	end_time = System.nanoTime();
	
	//Printing DAG Time
	System.out.println("Infering with DBN: " + (end_time - start_time)/1000000 + "ms");
	
	}

	

}
