package bayseanNetwork;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class DataSet {

	
	public ArrayList data;
	public int num_var;
	public int[] r1;   /* vector de inteiros r - maximum values each variable*/
	
	
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
			
			int[] generico = new int[counter*2];
			ArrayList<int[]> lista=new ArrayList<int[]>();
			r1 = new int[counter];
			
			
			while ((line = br.readLine()) != null) {
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
					
					if(generico[0]>r1[0]) r1[0]=generico[0];
					if(generico[1]>r1[1]) r1[1]=generico[1];
					if(generico[2]>r1[2]) r1[2]=generico[2];
					
					System.out.print(generico[0]);
					System.out.print(generico[1]);
					System.out.print(generico[2]);
					System.out.print(generico[3]);
					System.out.print(generico[4]);
					System.out.print(generico[5]);
					
					System.out.println();
					
					lista.add(generico);
					data=lista;
				}
		
			}
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
	
	
}
