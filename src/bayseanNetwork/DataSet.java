package bayseanNetwork;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataSet {

	
	public ArrayList<int[]> data;
	public int num_var;
	public int[] ri;   /* vector de inteiros r - maximum values each variable*/
	public DAG dag;
	
	
	public DataSet(String nome_ficheiro){
		
		
		BufferedReader br=null;
		String line= "";
		String csvSplitBy = ",";
		
		System.out.println(nome_ficheiro);
	
		try {
			 
			br = new BufferedReader(new FileReader(nome_ficheiro));
			line = br.readLine();
			
			int counter = 0, countervirgulas = 0;
			for( int i=0; i<line.length(); i++ ) {
			    if( line.charAt(i) == '_' && line.charAt(i+1)=='0' ) {
			    	counter++;
			    } 
			}
			System.out.println("Numero de zeros =" + counter);
			num_var= counter;
			for( int i=0; i<line.length(); i++ ) {
			    if( line.charAt(i) == ',' ) {
			    	countervirgulas++;
			    } 
			}
			System.out.println("Numero de indices =" + countervirgulas/counter);
			
			
			ArrayList<int[]> lista=new ArrayList<int[]>();
			ri = new int[counter];
			
			
			while ((line = br.readLine()) != null) {
				int[] generico = new int[counter*2];
				int j=0; int k=0;
			        // use comma as separator
				String[] variaveis = line.split(csvSplitBy);
				
				while(j+counter-1<variaveis.length){	
					for(int i=0; i<counter*2; i++,j++){
						if(i==0 && k!=0){
							j=j-counter;
						}
						k=1;
						generico[i]=Integer.parseInt(variaveis[j]);
						
					}
					
					if(generico[0]>=ri[0]) ri[0]=generico[0] + 1;
					if(generico[1]>=ri[1]) ri[1]=generico[1] + 1;
					if(generico[2]>=ri[2]) ri[2]=generico[2] + 1;
					
					System.out.print(generico[0]);
					System.out.print(generico[1]);
					System.out.print(generico[2]);
					System.out.print(generico[3]);
					System.out.print(generico[4]);
					System.out.print(generico[5]);
					
					System.out.println();
					
					lista.add(generico);
					
				}
				
			}
			data=lista;
		} catch (FileNotFoundException e){
			e.printStackTrace();
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
	
	public int calcNijk(int i, int q, int r){
		int counter = 0;
		
		int[][] parent_configuration = null;
		try{
			parent_configuration = dag.fromParentConfiguration(i,q);
		}catch (PCInvalid e){
			e.printStackTrace();
		} catch (NoParent e) {
			for (int[] data_line : data) {
				if(data_line[i] == r){
					counter++;
				}
			}
			return counter;
		}
		int correct_pc = 0;
		
		for (int[] data_line : data) {
			for (int j = 0; j < parent_configuration.length; j++) {
				if (data_line[parent_configuration[j][0]] == parent_configuration[j][1]){
					correct_pc ++;
				}else{
					break;
				}
			}
			if(data_line[i] == r && correct_pc == parent_configuration.length){
				counter++;
			}
			correct_pc = 0;
		}
		
		return counter;
	}
	
	
}
