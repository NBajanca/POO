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
	System.out.println(data_set.ri[0]+ "," + data_set.ri[1]+ ","+ data_set.ri[2]);
	data_set.dag = new DAG(data_set);
	System.out.println(data_set.dag.toString());
	
	
	//For test
	try{
		data_set.dag.fromParentConfiguration(3,1);
	}catch (PCInvalid e){
		e.printStackTrace();
	} catch (NoParent e) {
		System.out.println("no parents");
	}
	//Delete after debug
	
	/*DAG daga = new DAG(data_set);
	System.out.println(daga.dag[0][1]);
	daga.add(4,4);
	daga.add(0, 3);
	daga.add(0, 4);
	daga.add(0, 5);
	daga.add(1, 3);
	daga.add(1, 4);
	daga.add(1, 5);
	daga.add(2, 3);
	daga.add(2, 4);
	daga.add(2, 5);
	daga.add(4, 3);
	daga.add(5, 4);
	daga.add(3,5);*/
	
	System.exit(0);
	}

}
