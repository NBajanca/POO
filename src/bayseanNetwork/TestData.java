package bayseanNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The Class TestData.
 */
public class TestData extends EstablishArray {

	/**
	 * Instantiates a new test data.
	 *
	 * @param nome_ficheiro the nome_ficheiro
	 * @param numvar the numvar
	 */
	public TestData(String nome_ficheiro, int num_var){
		
		this.data = new ArrayList<int[]>();
		this.setNum_var(num_var);
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(nome_ficheiro));
			br.readLine();
			this.fillArray(br);	
		} catch (FileNotFoundException e){
			print404(nome_ficheiro);
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
	
	/* (non-Javadoc)
	 * @see bayseanNetwork.EstablishArray#travelStringAndStore(java.lang.String[])
	 */
	protected void travelStringAndStore(String[] variaveis){
		int [] generico = new int[this.getNum_var()*2];
		for(int i=0; i<this.getNum_var(); i++){
			variaveis[i] = variaveis[i].replaceAll("\\s","");
			generico[i]=Integer.parseInt(variaveis[i]);
		}
		this.data.add(generico);
		
	}
	

}
