package bayseanNetwork;

import java.util.Arrays;

public class DAG {
	//Matriz de Adjacências e respectivos métodos
	int[][] dag;
	
	public DAG(DataSet data_set){
		dag = new int[data_set.num_var*2][data_set.num_var];
		
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

}
