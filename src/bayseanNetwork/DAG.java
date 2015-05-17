package bayseanNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class DAG {
	//Matriz de Adjac�ncias e respectivos m�todos
	public boolean [][] dag;
	public DataSet data_set;
	
	public DAG(DataSet data_set, Score score){
		dag = new boolean[data_set.num_var*2][data_set.num_var];
		this.data_set = data_set;
		data_set.dag=this;

		 dag[0][1] = true;
		 dag[0][2] = true;
		 dag[0][0] = true;
		 dag[3][1] = true;
		
		GHC ghcgenerico = new GHC(this,score);
		//For test purpose

		//To delete after implementation
		
//		dag[0][1] = true;
//		dag[1][2] = true;
//		dag[2][2] = true;
//		dag[3][1] = true;
		//To delete after implementation
		
		//For test purpose

//		dag[0][0] = true;
//		dag[0][1] = true;
//		dag[0][2] = true;
//		dag[1][0] = true;
//		dag[1][1] = true;
//		dag[1][2] = true;
//		dag[2][1] = true;
//		dag[2][2] = true;
//		dag[4][0] = true;

	}
	public DAG(DAG master){
		
		this.dag = new boolean[master.data_set.num_var*2][master.data_set.num_var];
		this.data_set = new DataSet(master.data_set, this);

	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DAG \n" + Arrays.toString(dag[0]) + "\n" + Arrays.toString(dag[1]) + "\n" + Arrays.toString(dag[2]) + "\n" + Arrays.toString(dag[3]) + "\n" + Arrays.toString(dag[4]) + "\n" + Arrays.toString(dag[5]);
	}


	public void add(int origem, int destino) throws IlegalOperation{ //Tem que gerar uma excep��o quando n�o � poss�vel
		
		
		
		if(destino<this.data_set.num_var) throw new IlegalOperation();
		if(numParents(realNode(destino))>=3) throw new IlegalOperation();
		if(this.dag[origem][convertDestination(destino)]==true) throw new IlegalOperation("Edge already exists!");
		if(origem==destino) throw new IlegalOperation ();
		if(origem<this.data_set.num_var){
			System.out.println("Vector correctamente adicionado1");
			this.dag[origem][convertDestination(destino)]=true;
			return;
		}
		if (origem>=this.data_set.num_var){
			if (dag[destino][origem-this.data_set.num_var]==true) throw new IlegalOperation();
		}else{
			
			if (dag[convertDestination(destino)][origem]==true) throw new IlegalOperation();
		}
		
		boolean[] visitedVector = new boolean[2*this.data_set.num_var];
		for(int j=0;j<this.data_set.num_var;j++) visitedVector[j]=false;
		if(DFS(origem,destino,visitedVector)){
			System.out.println("Nao foi possivel adicionar vector");
		}else{
			System.out.println("Vector correctamente adicionado");
			System.out.println(origem + " " + destino);
			this.dag[origem][convertDestination(destino)]=true;
		}
		
	}
	
	public	void remove(int linha, int coluna){ //Tem que gerar uma excep��o quando n�o � poss�vel
		
		if(coluna<this.data_set.num_var) return;
		try{
				dag[linha][coluna-this.data_set.num_var]=false;
			} catch (ArrayIndexOutOfBoundsException e){
				System.out.println("ArrayIndexOutOfBounds");
			}
		}
	
	public void reverse(int origem, int destino) throws IlegalOperation{ //Tem que gerar uma excep��o quando n�o � poss�vel
		
		
		if(destino<this.data_set.num_var) throw new IlegalOperation();
		if(destino<this.data_set.num_var) throw new IlegalOperation();
		if(origem==destino) throw new IlegalOperation ();
		
		if(origem<this.data_set.num_var){
			System.out.println("N�o � possivel reverter o n�");
		}
		if(this.dag[origem][convertDestination(destino)]==true){
			this.dag[origem][convertDestination(destino)]=false;
			boolean[] visitedVector = new boolean[2*this.data_set.num_var];
			for(int j=0;j<this.data_set.num_var;j++) visitedVector[j]=false;
			if(DFS(destino,origem,visitedVector)){
				this.dag[origem][convertDestination(destino)] = true;
				System.out.println("N�o � possivel reverter a liga��o");
			}
			else{
				this.dag[destino][convertDestination(origem)]=true;
				System.out.println("N� correctamente revertido");
			}
		}
		
		
	}
	
	//Receives Parents Configuration and returns int with parent configuration
	public int toParentConfiguration (int node, int[][] parent_configuration){
		int j= 0; //empty configuration standart
		
		//Check if node is from t+1 (Nodes from t have empty parent configuration)
		if (node < this.data_set.num_var) return j;
		
		int real_node = realNode(node);
		int num_parents = numParents(real_node);
		
		//no parents means empty parent configuration
		if (num_parents == 0) return j;
		
		int [][] ri_parents = riParents(real_node, num_parents);
		
		j = parent_configuration[0][1];
		for (int i = 1; i < num_parents; i++) {
			j = j * ri_parents[i][1];
			j = j + parent_configuration[i][1];
		}
		return j;
	}
	
	//Receives Parent Configuration and returns array with parent node number and configuration
	public int[][] fromParentConfiguration (int node, int parent_configuration) throws PCInvalid, NoParent{
		if (parent_configuration < 0) throw new PCInvalid("NegativePC");
		
		int[][] q;
		
		if (parent_configuration >= maxq(node)) throw new PCInvalid();
		
		int real_node = realNode(node);
		int num_parents = numParents(real_node);
		
		if (num_parents == 0) throw new NoParent();
		
		q = new int[num_parents][2];
		int [][] ri_parents = riParents(real_node, num_parents);
		
		int parent_configuration_aux = parent_configuration;
		for (int i = ri_parents.length - 1; i >= 0 ; i--) {
			q[i][0] = ri_parents[i][0];
			q[i][1] = parent_configuration_aux % ri_parents[i][1];
			parent_configuration_aux = (parent_configuration_aux - q [i][1])/ri_parents[i][1];
		}
		return q;
	}
	
	//Converts t+1 node number to the value in the DAG
	int realNode(int node){
		if (node >= this.data_set.num_var){
			return node - this.data_set.num_var;
		}else{
			return node;
		}
		
	}
	
	//Converts t+1 node number to the value in the DAG
	int generalNode(int node){
		if (node >= this.data_set.num_var){
			return node + this.data_set.num_var;
		}else{
			return node;
		}
	}
	
	//Calculates the number of parents
	public int numParents(int real_node){
		int num_parents = 0;
		for (int i = 0; i < this.data_set.num_var*2 ; i++) {
			if (dag[i][real_node] == true){
				num_parents ++;
			}
		}
		return num_parents;
	}
	
	//Find the ri of each parent of the node
	public int [][] riParents(int real_node, int num_parents){
		int [][] ri_parents = new int[num_parents][2];
		
		int j = 0;
		for (int i = 0; i < this.data_set.num_var*2 ; i++) {
			if (dag[i][real_node] == true){
				ri_parents [j][0] = i;
				if (i < data_set.num_var){
					ri_parents [j][1] = this.data_set.ri[i];
				}else{
					ri_parents [j][1] = this.data_set.ri[i-data_set.num_var];
				}
				j ++;
			}
		}
		return ri_parents;
	}


	int maxq(int node) throws NoParent {
		//Check if node is from t+1 (Nodes from t have empty parent configuration)
		if (node < this.data_set.num_var) throw new NoParent();
		
		int real_node = realNode(node);
		int num_parents = numParents(real_node);
		
		if (num_parents == 0) throw new NoParent();
		
		int [][] ri_parents = riParents(real_node, num_parents);
		
		int max_q = 1;
		for (int i = 0; i < ri_parents.length; i++) {
			max_q = max_q * ri_parents [i][1];
		}
		
		return max_q;
	}
	
	private int convertDestination(int destino){
		destino = destino-data_set.num_var;
		return destino;
	}
	
	private boolean DFS(int origem, int destino,boolean[] visitedVector){
		
		for(int i=0; i<this.data_set.num_var; i++){
			if(this.dag[destino][i]==true && visitedVector[i]==false){
				if(i+this.data_set.num_var==origem) return true;
				visitedVector[i+this.data_set.num_var]=true;
				if(DFS(origem,i+this.data_set.num_var,visitedVector)) return true;
			}
			
		}
		return false;		
	}


	@Override
	protected DAG clone()  {
		DAG objecto = new DAG(this);
		for(int n=0; n<this.data_set.num_var*2;n++){
			for(int j=0; j<this.data_set.num_var;j++){
				objecto.dag[n][j]=this.dag[n][j];
			}
		}
		System.out.println(objecto.toString());
		return objecto;
	}
	
	protected void makeThemEqual(boolean[][] original){
		for(int n=0; n<this.data_set.num_var*2;n++){
			for(int j=0; j<this.data_set.num_var;j++){
				this.dag[n][j]=original[n][j];
			}
		}
	}
	
}
