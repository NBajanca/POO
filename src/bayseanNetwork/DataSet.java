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
		
		
		BufferedReader br=null;
		String line= "";
		int countervirgulas;
		
		System.out.println(nome_ficheiro);
	
		try {
			 
			br = new BufferedReader(new FileReader(nome_ficheiro));
			line = br.readLine();
			
			num_var = this.obtain_num_variables(line);
			System.out.println("Numero de zeros =" + num_var);
			countervirgulas=this.obtain_num_virgulas(line);
			System.out.println("Numero de indices =" + countervirgulas/num_var);
			this.ri = new int[num_var];
			this.fill_array(br);
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("O ficheiro" + nome_ficheiro + "nao existe!");
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
	
	int NijkCalc(int i, int q, int r){
		int counter = 0;
		
		
		
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
	
	void fill_array(BufferedReader br){
		
		String line;
		String csvSplitBy = ",";
		int [] generico = new int[this.num_var*2];
		ArrayList<int[]> lista = new ArrayList<int[]>();
		
			try {
				while ((line = br.readLine()) != null) {
					int j=0; 
					int k=0;
					
					String[] variaveis = line.split(csvSplitBy);
					while(j+this.num_var-1<variaveis.length){	
						
						for(int i=0; i<num_var*2; i++,j++){
							
							if(i==0 && k!=0){
								j=j-num_var;
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
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(NullPointerException e){
				e.printStackTrace();
			}
		this.data=lista;
	}
	
}
