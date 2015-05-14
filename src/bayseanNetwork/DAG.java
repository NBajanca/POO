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
	
	//Receives Parents Configuration and returns int with parent configuration
	public int toParentConfiguration (int node, int[][] parent_configuration){
		int j= 1; //empty configuration standart
		
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
		System.out.println("configuration: " + j);
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
	private int realNode(int node){
		return node - this.data_set.num_var;
	}
	
	//Calculates the number of parents
	private int numParents(int real_node){
		int num_parents = 0;
		for (int i = 0; i < this.data_set.num_var*2 ; i++) {
			if (dag[i][real_node] == 1){
				num_parents ++;
			}
		}
		return num_parents;
	}
	
	//Find the ri of each parent of the node
	private int [][] riParents(int real_node, int num_parents){
		int [][] ri_parents = new int[num_parents][2];
		
		int j = 0;
		for (int i = 0; i < this.data_set.num_var*2 ; i++) {
			if (dag[i][real_node] == 1){
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
}
