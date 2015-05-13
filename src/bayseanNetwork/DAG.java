package bayseanNetwork;

public class DAG {
	//Matriz de Adjacências e respectivos métodos
	int[][] dag;
	
	DAG(DataSet data_set){
		dag = new int[data_set.num_var*2][data_set.num_var];
		
		//For test purpose
		dag[0][1] = 1;
		dag[1][2] = 1;
		dag[2][2] = 1;
		dag[3][1] = 1;
		//To delete after implementation
	}
	
	
	void add(){ //Tem que gerar uma excepção quando não é possível
		
	}
	
	void remove(){ //Tem que gerar uma excepção quando não é possível
		
	}
	
	void reverse(){ //Tem que gerar uma excepção quando não é possível
		
	}

}
