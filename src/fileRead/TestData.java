package fileRead;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class TestData.
 */
public class TestData extends EstablishArray {

	/**
	 * Instantiates a new test data.
	 *
	 * @param nome_ficheiro the nome_ficheiro
	 * @param num_var the num_var
	 */
	public TestData(String nome_ficheiro, int num_var){
		
		this.setData(new ArrayList<int[]>());
		this.setNum_var(num_var);
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(nome_ficheiro));
			String line = br.readLine();
			this.obtaiNameVariables(line);
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
	@Override
	protected void travelStringAndStore(String[] variaveis){
		int [] generico = new int[this.getNum_var()*2];
		for(int i=0; i<this.getNum_var(); i++){
			variaveis[i] = variaveis[i].replaceAll("\\s","");
			generico[i]=Integer.parseInt(variaveis[i]);
		}
		this.getData().add(generico);
		
	}
	
	/**
	 * This functions obtains the name of var.
	 *
	 * @param line the line
	 * @return the int
	 */
	private void obtaiNameVariables(String line){
		String csvSplitBy = ",";
		setVar_names(line.split(csvSplitBy));
		String[] aux = getVar_names();
		for(int i=0; i<this.getNum_var(); i++){
			aux[i] = aux[i].replaceAll("\\s","");
		}
		setVar_names(aux);
		return;
	}

	/* (non-Javadoc)
	 * @see fileRead.EstablishArray#setData(java.util.ArrayList)
	 * Overides so that the Array can be edited by classes outside of the package
	 */
	@Override
	public void setData(ArrayList<int[]> data) {
		// TODO Auto-generated method stub
		super.setData(data);
	}
	
	

}
