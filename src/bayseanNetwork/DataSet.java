/*
 * 
 */
package bayseanNetwork;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class DataSet.
 */
public class DataSet implements EstablishArray{

	
	/** The data. Array list with the .csv train file values saved like [t(0)...t(n) t+1(0)...t+1(n)] */
	public ArrayList<int[]> data;
	
	/** The num_var. Number of variables */
	public int num_var;
	
	/** The ri. Maximum value each var take*/
	public int[] ri;
	
	/**
	 * Instantiates a new data set.
	 *
	 * @param nome_ficheiro the nome_ficheiro
	 */
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
	
	
	/**
	 * Instantiates a new data set.
	 *
	 * @param master the master
	 */
	public DataSet(DataSet master){
		this.data = master.data;
		this.num_var = master.num_var;
		this.ri = master.ri;
	}
	
	
	/**
	 * This functions obtains the number of variables based on _0
	 *
	 * @param line the line
	 * @return the int
	 */
	int obtaiNumVariables(String line){
		int counter = 0;
		for( int i=0; i<line.length(); i++ ) {
		    if( line.charAt(i) == '_' && line.charAt(i+1)=='0' ) {
		    	counter++;
		    }
		}
		return counter;
	}
	
	/* (non-Javadoc)
	 * @see bayseanNetwork.EstablishArray#fillArray(java.io.BufferedReader)
	 */
	/**
	 * This function travels the .csv file and calls other functions
	 *
	 * @param line the line
	 * @return the int
	 */
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
	

	/**
	 *  This function verifies if the maximum value should be replaced in the stored int []
	 *
	 * @param generico the generico
	 */
	void verifyMaximum(int[] generico){
		for (int i = 0; i < this.num_var; i++) {
			if(generico[i]>=this.ri[i]) this.ri[i]=generico[i] + 1;
		}
	}
	

	/**
	 *  This function travels the string[] and stores each 
	 *  group of 2*num_var fields in the array
	 *
	 * @param variaveis the String [] variaveis
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		string.append("\n");
		for(int i=0;i<this.data.size();i++){
			string.append("[");
			for (int j=0; j<this.num_var*2; j++){
				string.append(this.data.get(i)[j] +" ");
			}
			string.append("]\n");
		}
		return string.toString();
	}
	
	/**
	 *  Just to print FILE NOT FOUND
	 * 
	 *
	 * @param 
	 */
	public void print404(){
		System.out.println("Ficheiro nao existe!");
		System.err.println("[7] FILE NOT FOUND");
		System.exit(7);
	}
	
}
