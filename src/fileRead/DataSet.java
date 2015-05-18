package fileRead;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * The Class DataSet.
 */
public class DataSet extends EstablishArray{
	
	/** The ri. Maximum value each var take*/
	private int[] ri;
	
	/**
	 * Instantiates a new data set.
	 *
	 * @param nome_ficheiro the nome_ficheiro
	 */
	public DataSet(String nome_ficheiro){

		this.setData(new ArrayList<int[]>());
		BufferedReader br=null;
		String line= null;
	
		try {
			br = new BufferedReader(new FileReader(nome_ficheiro));
			line = br.readLine();
			setNum_var(this.obtaiNumVariables(line));
			this.ri = new int[getNum_var()];
			this.fillArray(br);
		} catch (FileNotFoundException e){
			print404(nome_ficheiro);
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
	 * This functions obtains the number of variables based on _0.
	 *
	 * @param line the line
	 * @return the int
	 */
	private int obtaiNumVariables(String line){
		int counter = 0;
		for( int i=0; i<line.length(); i++ ) {
		    if( line.charAt(i) == '_' && line.charAt(i+1)=='0' ) {
		    	
		    	counter++;
		    }
		}
		return counter;
	}
	

	/**
	 *  This function verifies if the maximum value should be replaced in the stored int [].
	 *
	 * @param generico the generico
	 */
	private void verifyMaximum(int[] generico){
		for (int i = 0; i < this.getNum_var(); i++) {
			if(generico[i]>=this.ri[i]) this.ri[i]=generico[i] + 1;
		}
	}
	

	/**
	 *  This function travels the string[] and stores each 
	 *  group of 2*num_var fields in the array.
	 *
	 * @param variaveis the String [] variaveis
	 */
	@Override
	protected void travelStringAndStore(String[] variaveis){
		int j=0; 
		int k=0;
		while(j+this.getNum_var()-1<variaveis.length){
			int [] generico = new int[this.getNum_var()*2];
			for(int i=0; i<getNum_var()*2; i++,j++){
				if(i==0 && k!=0){
					j=j-getNum_var();
				}
				k=1;
				variaveis[j] = variaveis[j].replaceAll("\\s","");
				generico[i]=Integer.parseInt(variaveis[j]);	
			}
			verifyMaximum(generico);
			this.getData().add(generico);
		}
	}


	/**
	 * Gets the ri.
	 *
	 * @return the ri
	 */
	public int[] getRi() {
		return ri;
	}


	/**
	 * @param var_names the var_names to set
	 */
	@Override
	public void setVar_names(String[] var_names) {
		super.setVar_names(var_names);
	}
}
