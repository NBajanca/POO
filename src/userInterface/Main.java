package userInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;




public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
			int randrest = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			System.out.println("randrest argument should be a number");
			System.err.println("[5] INVALID RANDREST ARGUMENT");
			System.exit(5);
		}
		
		try {
			int var = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			System.out.println("var argument should be a number");
			System.err.println("[6] INVALID VAR ARGUMENT");
			System.exit(6);
		}
		
		BufferedReader br=null;
		String line= "";
		String csvSplitBy = ",";
		
		System.out.println(train);
	
		try {
			 
			br = new BufferedReader(new FileReader(train));
			line = br.readLine();
			
			int counter = 0, countervirgulas = 0;
			for( int i=0; i<line.length(); i++ ) {
			    if( line.charAt(i) == '_' && line.charAt(i+1)=='0' ) {
			    	counter++;
			    } 
			}
			System.out.println("Numero de zeros =" + counter);
			for( int i=0; i<line.length(); i++ ) {
			    if( line.charAt(i) == ',' ) {
			    	countervirgulas++;
			    } 
			}
			System.out.println("Numero de indices =" + countervirgulas/counter);
			ArrayList<Integer> DBN = new ArrayList<Integer>(counter*2);
			while ((line = br.readLine()) != null) {
	 
			        // use comma as separator
				String[] variaveis = line.split(csvSplitBy);
				
				System.out.println("String = " + variaveis[0]  );
	 
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br !=null){
				try{
					br.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		
		System.exit(0);
	}

}
