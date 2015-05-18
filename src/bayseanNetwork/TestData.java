package bayseanNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class TestData.
 */
public class TestData implements EstablishArray {
	
	/** The data. */
	public ArrayList<int[]> data;  /*Array list para guardar valores do ficheiro .csv*/
	
	/** The num_var. */
	public int num_var; /* inteiro numero de variaveis*/

	// TestData constructor
	/**
	 * Instantiates a new test data.
	 *
	 * @param nome_ficheiro the nome_ficheiro
	 * @param numvar the numvar
	 */
	public TestData(String nome_ficheiro, int numvar){
		
		this.data = new ArrayList<int[]>();
		this.num_var = numvar;
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(nome_ficheiro));
			br.readLine();
			this.fillArray(br);	
		} catch (FileNotFoundException e){
			System.out.println("O ficheiro" + nome_ficheiro + "nao existe!");
			System.err.println("[7] FILE NOT FOUND");
			System.exit(7);
		} catch (IOException e) {
		} finally {
			if (br !=null){
				try{
					br.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	// This function travels the .csv file and calls other functions
	// to make the correct storage of usefull information in the file
	/* (non-Javadoc)
	 * @see bayseanNetwork.EstablishArray#fillArray(java.io.BufferedReader)
	 */
	public void fillArray(BufferedReader br) {
		String line=null;
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
	
	/* (non-Javadoc)
	 * @see bayseanNetwork.EstablishArray#travelStringAndStore(java.lang.String[])
	 */
	public void travelStringAndStore(String[] variaveis){
		int [] generico = new int[this.num_var*2];
		for(int i=0; i<this.num_var; i++){
			variaveis[i] = variaveis[i].replaceAll("\\s","");
			generico[i]=Integer.parseInt(variaveis[i]);
		}
		this.data.add(generico);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
	
	/* (non-Javadoc)
	 * @see bayseanNetwork.EstablishArray#print404()
	 */
	@Override
	public void print404() {
		System.out.println("Ficheiro nao existe!");
		System.err.println("[7] FILE NOT FOUND");
		System.exit(7);
	}
	

}
