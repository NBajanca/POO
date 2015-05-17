package bayseanNetwork;

import java.util.Arrays;
import java.util.Random;

public class DAG {
	//Matriz de Adjac�ncias e respectivos m�todos
	public boolean [][] dag;
	public DataSet data_set;
	
	public DAG(DataSet data_set, Score score, int randrest){
		dag = new boolean[data_set.num_var*2][data_set.num_var];
		
		this.data_set = data_set;
		
		ganerateRandomDAG();
		
	
		new GHC(this,score, randrest);

	}

	public DAG(DAG master){	
		this.dag = master.dag;
		this.data_set = master.data_set;

	}
	
	private void ganerateRandomDAG() {
		Random random = new Random();
		for (int i = 0; i < data_set.num_var*2; i++) {
			for (int j = 0; j < data_set.num_var; j++) {
				if(random.nextInt(data_set.num_var) == 0){
					try {
						add(i, generalNode(j));
					} catch (IlegalOperation e) {
					}
				}
			}
		}
		
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
			//System.out.println("Vector correctamente adicionado1");
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
			//System.out.println("Nao foi possivel adicionar vector");
		}else{
			//System.out.println("Vector correctamente adicionado");
			//System.out.println(origem + " " + destino);
			this.dag[origem][convertDestination(destino)]=true;
		}
		
	}
	
	public	void remove(int linha, int coluna) throws IlegalOperation{ //Tem que gerar uma excep��o quando n�o � poss�vel
		
		if(coluna<this.data_set.num_var) throw new IlegalOperation();
		if (dag[linha][coluna-this.data_set.num_var] == false ) throw new IlegalOperation();
		else{
			dag[linha][coluna-this.data_set.num_var]=false;
		}
		
	}
	
	public void reverse(int origem, int destino) throws IlegalOperation{ //Tem que gerar uma excep��o quando n�o � poss�vel
		
		
		if(destino<this.data_set.num_var) throw new IlegalOperation();
		if(origem<this.data_set.num_var) throw new IlegalOperation();
		if(numParents(realNode(origem))>=3) throw new IlegalOperation();
		if(origem==destino) throw new IlegalOperation ();
		
		if(origem<this.data_set.num_var){
			//System.out.println("N�o � possivel reverter o n�");
		}
		if(this.dag[origem][convertDestination(destino)]==true){
			this.dag[origem][convertDestination(destino)]=false;
			boolean[] visitedVector = new boolean[2*this.data_set.num_var];
			for(int j=0;j<this.data_set.num_var;j++) visitedVector[j]=false;
			if(DFS(destino,origem,visitedVector)){
				this.dag[origem][convertDestination(destino)] = true;
				//System.out.println("N�o � possivel reverter a liga��o");
			}
			else{
				this.dag[destino][convertDestination(origem)]=true;
				//System.out.println("N� correctamente revertido");
			}
		}else{
			throw new IlegalOperation();
		}
		
		
	}
	
	//Returns Nijk in [0] and Nij in [1]
	public int[] calcNijk(int i, int j, int k){
		int[] counter = new int[2];
		
		int[][] parent_configuration = null;
		try{
			parent_configuration = fromParentConfiguration(i,j);
		}catch (PCInvalid e){
			e.printStackTrace();
		} catch (NoParent e) {
			//If there are no parents the calc is direct
			for (int[] data_line : data_set.data) {
				counter[1]++;
				if(data_line[i] == k){
					counter[0]++;
				}
			}
			return counter;
		}
		int correct_pc = 0;
		
		//The trains-set is iterated and the Nijk and Nij are calc
		for (int[] data_line : data_set.data) {
			for (int j1 = 0; j1 < parent_configuration.length; j1++) {
				if (data_line[parent_configuration[j1][0]] == parent_configuration[j1][1]){
					correct_pc ++;
				}else{
					break;
				}
			}
			if (correct_pc == parent_configuration.length){
				counter[1]++;
				if(data_line[i] == k){
					counter[0]++;
				}
			}
			correct_pc = 0;
		}
		
		return counter;
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
	public int generalNode(int node){
		if (node < this.data_set.num_var){
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

	
}
