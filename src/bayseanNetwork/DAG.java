package bayseanNetwork;

public class DAG {
	//Matriz de Adjac�ncias e respectivos m�todos
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
	
	
	void add(){ //Tem que gerar uma excep��o quando n�o � poss�vel
		
	}
	
	void remove(){ //Tem que gerar uma excep��o quando n�o � poss�vel
		
	}
	
	void reverse(){ //Tem que gerar uma excep��o quando n�o � poss�vel
		
	}

}
