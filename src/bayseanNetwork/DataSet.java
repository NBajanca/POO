package bayseanNetwork;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataSet implements EstablishArray{

	
	public ArrayList<int[]> data; /*Array list para guardar valores do ficheiro .csv*/
	public int num_var;/* inteiro numero de variaveis*/
	public int[] ri;   /* vector de inteiros r - maximum values each variable*/
	
	//Data general set constructor 
	public DataSet(String nome_ficheiro){

		this.data = new ArrayList<int[]>();
		BufferedReader br=null;
		String line= null;
	
		try {
			br = new BufferedReader(new FileReader(nome_ficheiro));
			line = br.readLine();
			num_var = this.obtaiNumVariables(line);
			this.ri = new int[num_var];
			this.fillArray(br);
		} catch (FileNotFoundException e){
			print404();
		} catch (IOException e){
		}finally {
			if (br !=null){
				try{
					br.close();
				} catch (IOException e){
				}
			}
		}	
	}
	// DataSet constructor for other purposes
	public DataSet(DataSet master){
		this.data = master.data;
		this.num_var = master.num_var;
		this.ri = master.ri;
	}
	
	//This functions obtains the number of variables based on _0
	int obtaiNumVariables(String line){
		int counter = 0;
		for( int i=0; i<line.length(); i++ ) {
		    if( line.charAt(i) == '_' && line.charAt(i+1)=='0' ) {
		    	counter++;
		    }
		}
		return counter;
	}
	
	// This function travels the .csv file and calls other functions
	// to make the correct storage of usefull information in the file
	public void fillArray(BufferedReader br){
		
		String line;
		String csvSplitBy = ",";

		try {
			while ((line = br.readLine()) != null) {
				String[] variaveis = line.split(csvSplitBy);
				travelStringAndStore(variaveis);
			}
		} catch (NumberFormatException e) {
		} catch (IOException e) {
		} catch(NullPointerException e){
		}
	}
	
	// This function verifies if the maximum value should be replaced in the stored int []
	void verifyMaximum(int[] generico){
		for (int i = 0; i < this.num_var; i++) {
			if(generico[i]>=this.ri[i]) this.ri[i]=generico[i] + 1;
		}
	}
	
	// This function travels the string[] and stores each 
	//	group of 2*num_var fields in the array
	public void travelStringAndStore(String[] variaveis){
		int j=0; 
		int k=0;
		while(j+this.num_var-1<variaveis.length){
			int [] generico = new int[this.num_var*2];
			for(int i=0; i<num_var*2; i++,j++){
				if(i==0 && k!=0){
					j=j-num_var;
				}
				k=1;
				variaveis[j] = variaveis[j].replaceAll("\\s","");
				generico[i]=Integer.parseInt(variaveis[j]);	
			}
			verifyMaximum(generico);
			this.data.add(generico);
		}
	}

	@Override
	public String toString() {
		System.out.println("");
		for(int i=0;i<this.data.size();i++){
			System.out.print("[");
			for (int j=0; j<this.num_var*2; j++){
				System.out.print(this.data.get(i)[j] +" ");
			}
			System.out.println("]");
		}
		return "";
	}
	// Just to print FILE NOT FOUND
	public void print404(){
		System.out.println("Ficheiro nao existe!");
		System.err.println("[7] FILE NOT FOUND");
		System.exit(7);
	}
	
}
