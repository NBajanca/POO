package bayseanNetwork;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataSet {

	
	public ArrayList<int[]> data;
	public int num_var;/* inteiro numero de variaveis*/
	public int[] ri;   /* vector de inteiros r - maximum values each variable*/
	public DAG dag;
	
	
	public DataSet(String nome_ficheiro){
		
		this.data = new ArrayList<int[]>();
		BufferedReader br=null;
		String line= "";
		
		System.out.println(nome_ficheiro);
	
		try {
			 
			br = new BufferedReader(new FileReader(nome_ficheiro));
			line = br.readLine();
			num_var = this.obtain_num_variables(line);
			/*System.out.println("Numero de zeros =" + num_var);*/
			//countervirgulas=this.obtain_num_virgulas(line);
			/*System.out.println("Numero de indices =" + countervirgulas/num_var);*/
			this.ri = new int[num_var];
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
	
	
	//Returns Nijk in [0] and Nij in [1]
	public int[] calcNijk(int i, int q, int r){
		int[] counter = new int[2];
		
		int[][] parent_configuration = null;
		try{
			parent_configuration = dag.fromParentConfiguration(i,q);
		}catch (PCInvalid e){
			e.printStackTrace();
		} catch (NoParent e) {
			//If there are no parents the calc is direct
			for (int[] data_line : data) {
				counter[1]++;
				if(data_line[i] == r){
					counter[0]++;
				}
			}
			return counter;
		}
		int correct_pc = 0;
		
		//The trains-set is iterated and the Nijk and Nij are calc
		for (int[] data_line : data) {
			for (int j = 0; j < parent_configuration.length; j++) {
				if (data_line[parent_configuration[j][0]] == parent_configuration[j][1]){
					correct_pc ++;
				}else{
					break;
				}
			}
			if (correct_pc == parent_configuration.length){
				counter[1]++;
				if(data_line[i] == r){
					counter[0]++;
				}
			}
			correct_pc = 0;
		}
		
		return counter;
	}
	
	int obtain_num_variables(String line){
		
		int counter = 0;
		
		for( int i=0; i<line.length(); i++ ) {
		    if( line.charAt(i) == '_' && line.charAt(i+1)=='0' ) {
		    	counter++;
		    }
		}
		return counter;
		
	}
	
	int obtain_num_virgulas(String line){
		
		int countervirgulas=0;
		
		for( int i=0; i<line.length(); i++ ) {
		    if( line.charAt(i) == ',' ) {
		    	countervirgulas++;
		    } 
		    
		}
		return countervirgulas;
	}
	
	// This function travels the .csv file and calls other functions
	// to make the correct storage of usefull information in the file
	void fill_array(BufferedReader br){
		
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
	
	// This function verifies if the maximum value should be replaced in the stored int []
	void verify_maximum(int[] generico){
		if(generico[0]>=this.ri[0]) this.ri[0]=generico[0] + 1;
		if(generico[1]>=this.ri[1]) this.ri[1]=generico[1] + 1;
		if(generico[2]>=this.ri[2]) this.ri[2]=generico[2] + 1;
	}
	// This function travels the string[] and stores each 
	//	group of 2*num_var fields in the array
	void travel_string_and_store(String[] variaveis){
		int j=0; 
		int k=0;
		while(j+this.num_var-1<variaveis.length){
			int [] generico = new int[this.num_var*2];
			for(int i=0; i<num_var*2; i++,j++){
				
				if(i==0 && k!=0){
					j=j-num_var;
				}
				k=1;
				generico[i]=Integer.parseInt(variaveis[j]);	
			}
			verify_maximum(generico);
			this.data.add(generico);
		}
	}
}
