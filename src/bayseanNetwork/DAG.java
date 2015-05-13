package bayseanNetwork;

import java.util.Arrays;

public class DAG {
	//Matriz de Adjacências e respectivos métodos
	int[][] dag;
	DataSet data_set;
	
	public DAG(DataSet data_set){
		dag = new int[data_set.num_var*2][data_set.num_var];
		this.data_set = data_set;
		
		//For test purpose
		dag[0][1] = 1;
		dag[1][2] = 1;
		dag[2][2] = 1;
		dag[3][1] = 1;
		//To delete after implementation
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DAG \n" + Arrays.toString(dag[0]) + "\n" + Arrays.toString(dag[1]) + "\n" + Arrays.toString(dag[2]) + "\n" + Arrays.toString(dag[3]) + "\n" + Arrays.toString(dag[4]) + "\n" + Arrays.toString(dag[5]);
	}


	void add(){ //Tem que gerar uma excepção quando não é possível
		
	}
	
	void remove(){ //Tem que gerar uma excepção quando não é possível
		
	}
	
	void reverse(){ //Tem que gerar uma excepção quando não é possível
		
	}
	
	public int[] FromParentConfiguration (int node, int parent_configuration){
		int[] q;
		if (node <= this.data_set.num_var){
			q = new int[1];
			q[0] = -1; //Empty Configuration
		}else{
			int real_node = node - this.data_set.num_var;
			
			//calc number of parents
			int num_parents = 0;
			for (int i = 0; i < this.data_set.num_var*2 ; i++) {
				if (dag[i][real_node] == 1){
					num_parents ++;
				}
			}
			
			
			//Find each parent configuration
			q = new int[num_parents];
			int [][] ri_parents = new int[num_parents][2];
			
			num_parents = 0;
			for (int i = 0; i < this.data_set.num_var*2 ; i++) {
				if (dag[i][real_node] == 1){
					ri_parents [num_parents][0] = i;
					if (i < data_set.num_var){
						ri_parents [num_parents][1] = this.data_set.ri[i];
					}else{
						ri_parents [num_parents][1] = this.data_set.ri[i-data_set.num_var];
					}
					
					System.out.println(ri_parents [num_parents][0] + ri_parents [num_parents][1]);
					num_parents ++;
				}
			}
			
		}
		return q;
	}
}
