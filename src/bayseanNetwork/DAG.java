package bayseanNetwork;

import fileRead.DataSet;


/**
 * The Class DAG 
 * Provides the proper methods to create a Directed Acyclic Graph regarding some restrictions, namely the parent number each node can take  
 * 
 */
public class DAG {
	/** The dag - Adjacency Matrix. Only the nodes t+1 are in the collums because there can't be any connection reaching t nodes */
	private boolean [][] dag;
	
	/** The max_parents. Defined in project guide */
	private final int max_parents = 3;
	
	/** The data_set. */
	private DataSet data_set;
	
	/**
	 * Instantiates a new DAG and runs the GHC alghoritm.
	 *
	 * @param data_set the data_set
	 * @param score the score
	 * @param randrest the randrest
	 */
	public DAG(DataSet data_set, Score score, int randrest){
		setDag(new boolean[data_set.getNum_var()*2][data_set.getNum_var()]);
		this.setData_set(data_set);
		new GHC(this,score, randrest);
	}
	
	
	/**
	 * Instantiates a new dag (used for DAGGHC objects)
	 * Only used to point the variables to the right objects.
	 *
	 * @param master the master
	 */
	DAG(DAG master){	
		this.setDag(master.getDag());
		this.setData_set(master.getData_set());
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		
		string.append("=== Inter-slice connectivity\n");
		string.append(toStringNetwork(0));
		
		string.append("=== Intra-slice connectivity\n");
		string.append(toStringNetwork(1));
		
		return string.toString();
	}
	
	
	/**
	 * To string network.
	 *
	 * @param network the network
	 * @return the string
	 */
	private String toStringNetwork(int network) {
		StringBuilder string = new StringBuilder();
		String[] var_names = getData_set().getVar_names();
		
		for (int i = 0; i < getData_set().getNum_var(); i++) {
			string.append(var_names[i] +  " : ");
			int num_parents = numParents(i);
			int first_time = 0;
			
			//no parents means empty parent configuration
			if (num_parents == 0){
				string.append("\n");
			}else{
				int [][] ri_parents = riParents(i, num_parents);
				for (int j = 0; j < ri_parents.length; j++) {
					if (ri_parents[j][0] >= getData_set().getNum_var() && network == 0) break;
					if (ri_parents[j][0] < getData_set().getNum_var() && network == 1) continue;
					else first_time ++;
					if ( (j !=0 && network == 0) || (first_time > 1 && network == 1)) string.append(" , ");
					string.append(var_names[realNode(ri_parents[j][0])]);
				}
				string.append("\n");
			}
		}
		return string.toString();
	}

	/**
	 * Adds edge to the DAG after verifing  if it is possible
	 * Throws exception if add is not possible .
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * @throws IlegalOperation the ilegal operation
	 */
	protected void add(int origin, int destiny) throws IlegalOperation{
		verifyIlegalOperationsAdd(origin, destiny);
		
		// If origin node is in t no verification is needed
		if(origin<this.getData_set().getNum_var()){ 
			this.getDag()[origin][realNode(destiny)]=true;
			return;
		} else {
			if (getDag()[destiny][origin-this.getData_set().getNum_var()]==true) throw new IlegalOperation();
		}
		
		//Runs DFS to assure new matrix is a DAG
		boolean[] visitedVector = new boolean[2*this.getData_set().getNum_var()];
		if(DFS(origin,destiny,visitedVector)){
			throw new IlegalOperation();
		}else{
			this.getDag()[origin][realNode(destiny)]=true;
		}
	}
	
	/**
	 * Removes an edge from DAG .
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * @throws IlegalOperation the ilegal operation
	 */
	protected void remove(int origin, int destiny) throws IlegalOperation{ 
		verifyIlegal(origin, destiny);
		
		//Edge doesn't exists
		if (getDag()[origin][destiny-this.getData_set().getNum_var()] == false ) throw new IlegalOperation();
		else getDag()[origin][destiny-this.getData_set().getNum_var()]=false;
		
	}
	
	/**
	 * Reverses edge in the DAG after verifing  if it is possible
	 * Throws exception if reverse is not possible .
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * @throws IlegalOperation the ilegal operation
	 */
	protected void reverse(int origin, int destiny) throws IlegalOperation{ //Tem que gerar uma excep��o quando n�o � poss�vel
		verifiesIlegalOperationReverse(origin,destiny);
		
		//Reverses the connection
		this.getDag()[origin][realNode(destiny)]=false;
		boolean[] visited_vector = new boolean[2*this.getData_set().getNum_var()];
		
		//Runs DFS to assure new matrix is a DAG
		if(DFS(destiny,origin,visited_vector)){
			this.getDag()[origin][realNode(destiny)] = true;
			throw new IlegalOperation();
		}else{
			this.getDag()[destiny][realNode(origin)]=true;
		}
	}
	
	/**
	 * Verifies ilegal operations add.
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * 
	 * @throws IlegalOperation the ilegal operation
	 */
	private void verifyIlegalOperationsAdd(int origin, int destiny) throws IlegalOperation {
		verifyIlegal(origin, destiny);
		
		//Max number of parents
		if(numParents(realNode(destiny)) >=	max_parents) throw new IlegalOperation();
		
		//Edge already there
		if(this.getDag()[origin][realNode(destiny)]==true) throw new IlegalOperation();
		
		return;
	}
	
	/**
	 * Verifies ilegal operation reverse.
	 * The DAG atributes are always assured if this function is used.
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * 
	 * @throws IlegalOperation the ilegal operation
	 */
	private void verifiesIlegalOperationReverse(int origin, int destiny) throws IlegalOperation {
		verifyIlegal(origin, destiny);
		
		//The origin(future destiny) needs to be t+1
		if(origin<this.getData_set().getNum_var()) throw new IlegalOperation();
		
		//Max number of parents
		if(numParents(realNode(origin)) >= max_parents) throw new IlegalOperation();
		
		//Edge doesn't exist
		if(this.getDag()[origin][realNode(destiny)]==false) throw new IlegalOperation();
		return;
	}
	
	/**
	 * Verifies ilegal operation
	 * The destiny can't be in t
	 * The origin and destiny need to be diferent nodes.
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * 
	 * @throws IlegalOperation the ilegal operation
	 */
	private void verifyIlegal(int origin, int destiny) throws IlegalOperation {
		if(destiny<this.getData_set().getNum_var()) throw new IlegalOperation(); 
		if(origin==destiny) throw new IlegalOperation(); 
		return;
	}
	
	/**
	 * Calc nijk.
	 * Returns Nijk in [0] and Nij in [1]
	 *
	 * @param i the i
	 * @param j the j
	 * @param k the k
	 * @return the int[]
	 */
	int[] calcNijk(int i, int j, int k){
		int[] counter = new int[2];
		
		int[][] parent_configuration = null;
		try{
			parent_configuration = fromParentConfiguration(i,j);
		}catch (PCInvalid e){
			e.printStackTrace();
		} catch (NoParent e) {
			//If there are no parents the calc is direct
			for (int[] data_line : getData_set().getData()) {
				counter[1]++;
				if(data_line[i] == k){
					counter[0]++;
				}
			}				
			return counter;
		}
		int correct_pc = 0;
		
		//The trains-set is iterated and the Nijk and Nij are calc
		for (int[] data_line : getData_set().getData()) {
			for (int j1 = 0; j1 < parent_configuration.length; j1++) {
				if (data_line[parent_configuration[j1][0]] == parent_configuration[j1][1]){
					correct_pc ++;
				}else{
					break;
				}
			}
			if (correct_pc == parent_configuration.length){
				counter[1]++; //Nij
				if(data_line[i] == k){
					counter[0]++; //Nijk
				}
			}
			correct_pc = 0;
		}		
		return counter;
	}
	
	/**
	 * Receives Parents Configuration and returns int with parent configuration.
	 *
	 * @param node the node
	 * @param parent_configuration the parent_configuration
	 * @return the int
	 */
	int toParentConfiguration (int node, int[][] parent_configuration){
		int j= 0; //empty configuration standart
		
		//Check if node is from t+1 (Nodes from t have empty parent configuration)
		if (node < this.getData_set().getNum_var()) return j;
		
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
	
	/**
	 * Receives Parent Configuration and returns array with parent node number and configuration.
	 *
	 * @param node the node
	 * @param parent_configuration the parent_configuration
	 * @return the int[][]
	 * @throws PCInvalid the PC invalid
	 * @throws NoParent the no parent
	 */
	 int[][] fromParentConfiguration (int node, int parent_configuration) throws PCInvalid, NoParent{
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
	
	/**
	 * Real node.
	 * Converts t+1 node number to the value in the DAG
	 * If the node is t this function returns the nodes
	 *
	 * @param node the node
	 * @return the int
	 */
	int realNode(int node){
		if (node >= this.getData_set().getNum_var()){
			return node - this.getData_set().getNum_var();
		}else{
			return node;
		}
		
	}
	
	/**
	 * General node.
	 * Converts t+1 node number in the DAG to the value in the Data-Set
	 * If the node is t+1 in DataSet this function returns the nodes
	 *
	 * @param node the node
	 * @return the int
	 */
	public int generalNode(int node){
		if (node < this.getData_set().getNum_var()){
			return node + this.getData_set().getNum_var();
		}else{
			return node;
		}
	}
	
	/**
	 * Num parents.
	 * Calculates the number of parents
	 *
	 * @param real_node the real_node
	 * @return the int
	 */
	int numParents(int real_node){
		int num_parents = 0;
		for (int i = 0; i < this.getData_set().getNum_var()*2 ; i++) {
			if (getDag()[i][real_node] == true){
				num_parents ++;
			}
		}
		return num_parents;
	}

	
	/**
	 * Ri parents.
	 * Find the ri of each parent of the node
	 *
	 * @param real_node the real_node
	 * @param num_parents the num_parents
	 * @return the int[][]
	 */
	int [][] riParents(int real_node, int num_parents){
		int [][] ri_parents = new int[num_parents][2];
		
		int j = 0;
		for (int i = 0; i < this.getData_set().getNum_var()*2 ; i++) {
			if (getDag()[i][real_node] == true){
				ri_parents [j][0] = i;
				if (i < getData_set().getNum_var()){
					ri_parents [j][1] = this.getData_set().getRi()[i];
				}else{
					ri_parents [j][1] = this.getData_set().getRi()[i-getData_set().getNum_var()];
				}
				j ++;
			}
		}
		return ri_parents;
	}


	/**
	 * Returns the q (product of parent's r).
	 *
	 * @param node the node
	 * @return the int
	 * @throws NoParent the no parent
	 */
	int maxq(int node) throws NoParent {
		//Check if node is from t+1 (Nodes from t have empty parent configuration)
		if (node < this.getData_set().getNum_var()) throw new NoParent();
		
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


	/**
	 * Dfs.
	 * Recursive function used to assure DAG.
	 *
	 * @param origin the origin
	 * @param destiny the destiny
	 * @param visitedVector the visited vector
	 * @return true, if successful
	 */
	private boolean DFS(int origin, int destiny ,boolean[] visitedVector){
		
		for(int i=0; i<this.getData_set().getNum_var(); i++){
			if(this.getDag()[destiny][i]==true && visitedVector[i]==false){
				if(i+this.getData_set().getNum_var()==origin) return true;
				visitedVector[i+this.getData_set().getNum_var()]=true;
				if(DFS(origin,i+this.getData_set().getNum_var(),visitedVector)) return true;
			}
			
		}
		return false;		
	}


	/**
	 * @return the data_set
	 */
	protected DataSet getData_set() {
		return data_set;
	}


	/**
	 * @param data_set the data_set to set
	 */
	protected void setData_set(DataSet data_set) {
		this.data_set = data_set;
	}


	/**
	 * @return the dag
	 */
	protected boolean [][] getDag() {
		return dag;
	}


	/**
	 * @param dag the dag to set
	 */
	protected void setDag(boolean [][] dag) {
		this.dag = dag;
	}
	
}
