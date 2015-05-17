package bayseanNetwork;

public class DAGGHC extends DAG {
	double score;

	public DAGGHC(DAG master, Score score) {
		super(master);
		this.score = score.compute(this);
	}
	
	public DAGGHC(DAG master, double score) {
		super(master);
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see bayseanNetwork.DAG#clone()
	 */
	@Override
	protected DAGGHC clone() {
		
		DAG objecto = new DAG(this);
		objecto.data_set = new DataSet (this.data_set, objecto);
		objecto.dag = new boolean[this.data_set.num_var*2][this.data_set.num_var];
		for(int n=0; n<objecto.data_set.num_var*2;n++){
			for(int j=0; j<objecto.data_set.num_var;j++){
				objecto.dag[n][j]=this.dag[n][j];
			}
		}
		
		DAGGHC dag_test =  new DAGGHC (objecto , this.score);
		return dag_test;
	}
	
	protected DAGGHC random()  {
		DAG objecto = new DAG(this);
		objecto.data_set = new DataSet (this.data_set, objecto);
		objecto.dag = new boolean[this.data_set.num_var*2][this.data_set.num_var];
		objecto.ganerateRandomDAG();
		
		DAGGHC dag_test =  new DAGGHC (objecto , this.score);
		return dag_test;
	}

	
}
