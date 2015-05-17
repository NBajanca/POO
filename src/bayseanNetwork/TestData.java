package bayseanNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestData implements EstablishArray {
	
	public ArrayList<int[]> data;
	public int num_var;

	public TestData(String nome_ficheiro, int numvar){
		
		this.data = new ArrayList<int[]>();
		this.num_var = numvar;
		BufferedReader br=null;
		String line= "";
		
		try {
			 
			br = new BufferedReader(new FileReader(nome_ficheiro));
			line = br.readLine();
			this.fill_array(br);	
		} catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("O ficheiro" + nome_ficheiro + "nao existe!");
			System.err.println("[7] FILE NOT FOUND");
			System.exit(7);
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
		
	
	}
	
	public void fill_array(BufferedReader br) {
		
		String line;
		String csvSplitBy = ",";

		try {
			while ((line = br.readLine()) != null) {
				String[] variaveis = line.split(csvSplitBy);
				travel_string_and_store(variaveis);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}

	public void travel_string_and_store(String[] variaveis){
		int [] generico = new int[this.num_var*2];
		for(int i=0; i<this.num_var; i++){
			variaveis[i] = variaveis[i].replaceAll("\\s","");
			generico[i]=Integer.parseInt(variaveis[i]);
		}
		this.data.add(generico);
		
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
	

}
