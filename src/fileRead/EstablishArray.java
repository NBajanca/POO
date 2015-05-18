package fileRead;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The Class EstablishArray.
 * This class defines some methods that allow the read of csv files.
 * Performs the creation of a ArrayList of the values in the files.
 * 
 */
public abstract class EstablishArray {
	
	/** The data. Array list with the .csv train file values saved like [t(0)...t(n) t+1(0)...t+1(n)] */
	private ArrayList<int[]> data;
	
	/** The num_var. Number of variables */
	private int num_var;
	
	/** The var_name. - name of the vars */
	private String[] var_names;
	
	/**
	 * Fill array list.
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
	 * Travel string and stores t and t+1 in the same int[] adding it to the array list
	 * Needs to be overided in the son's classe's
	 * 
	 *
	 * @param variaveis the variaveis
	 */
	protected void travelStringAndStore(String[] variaveis){
		
	}
	
	/**
	 * Print404.
	 *
	 * @param nome_ficheiro the nome_ficheiro
	 */
	protected  static void print404(String nome_ficheiro){
		System.out.println("O ficheiro" + nome_ficheiro + " nao existe!");
		System.err.println("[7] FILE NOT FOUND");
		System.exit(7);
	}

	/**
	 * Gets the num_var.
	 *
	 * @return the num_var
	 */
	public int getNum_var() {
		return num_var;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public ArrayList<int[]> getData() {
		return data;
	}

	/**
	 * Sets the num_var.
	 *
	 * @param num_var the num_var to set
	 */
	protected void setNum_var(int num_var) {
		this.num_var = num_var;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the data to set
	 */
	protected void setData(ArrayList<int[]> data) {
		this.data = data;
	}
	
	/**
	 * @return the var_names
	 */
	public String[] getVar_names() {
		return var_names;
	}


	/**
	 * @param var_names the var_names to set
	 */
	protected void setVar_names(String[] var_names) {
		this.var_names = var_names;
	}

}
