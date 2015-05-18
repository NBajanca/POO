package bayseanNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The Interface EstablishArray.
 */
public abstract class EstablishArray {
	
	/** The data. Array list with the .csv train file values saved like [t(0)...t(n) t+1(0)...t+1(n)] */
	protected ArrayList<int[]> data;
	
	/** The num_var. Number of variables */
	private int num_var;
	
	/**
	 * Fill array.
	 *
	 * @param br the br
	 */
	protected void fillArray(BufferedReader br){
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
	 * Travel string and store.
	 *
	 * @param variaveis the variaveis
	 */
	protected void travelStringAndStore(String[] variaveis){
		
	}
	
	/**
	 * Print404.
	 * @param nome_ficheiro 
	 */
	protected  static void print404(String nome_ficheiro){
		System.out.println("O ficheiro" + nome_ficheiro + " nao existe!");
		System.err.println("[7] FILE NOT FOUND");
		System.exit(7);
	}

	/**
	 * @return the num_var
	 */
	public int getNum_var() {
		return num_var;
	}

	/**
	 * @param num_var the num_var to set
	 */
	protected void setNum_var(int num_var) {
		this.num_var = num_var;
	}

}
