package bayseanNetwork;

import java.util.Random;

/**
 * The Class DAGGHC.
 * Extends Dag. Changes: Has score (double), Overrides clone(method), Implements random(method)
 */
class DAGGHC extends DAG {
	
	/** The score. */
	double score;

	/**
	 * Instantiates a new DAG and computes it's score
	 *
	 * @param master the master
	 * @param score the score
	 */
	DAGGHC(DAG master, Score score) {
		super(master);
		this.score = score.compute(this);
	}
	
	/**
	 * Instantiates a new DAG and saves it's score
	 *
	 * @param master the master
	 * @param score the score
	 */
	DAGGHC(DAG master, double score) {
		super(master);
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected DAGGHC clone() {
		
		DAG objecto = new DAG(this);
		objecto.data_set = new DataSet (this.data_set);
		objecto.dag = new boolean[this.data_set.num_var*2][this.data_set.num_var];
		for(int n=0; n<objecto.data_set.num_var*2;n++){
			for(int j=0; j<objecto.data_set.num_var;j++){
				objecto.dag[n][j]=this.dag[n][j];
			}
		}
		
		DAGGHC dag_test =  new DAGGHC (objecto , this.score);
		return dag_test;
	}
	
	/**
	 * Executes random operations to this DAG
	 * 
	 * Description:
	 * Goes to all nodes of the DAG and randomly choses to do add, remove, reverse or nothing to the nodes selected.
	 * The Probability of one of the operations being done is lesser as the DAG gets bigger.
	 * 
	 */
	void random() {
		Random random = new Random();
		for (int i = 0; i < data_set.num_var*2; i++) {
			for (int j = 0; j < data_set.num_var; j++) {
				switch(random.nextInt(2 + data_set.num_var)){
					case 0:
						try {
							add(i, generalNode(j));
						} catch (IlegalOperation e) {
						}
						break;
					case 1:
						try {
							remove(i, generalNode(j));
						} catch (IlegalOperation e) {
						}
						break;
					case 2:
						try {
							reverse(i, generalNode(j));
						} catch (IlegalOperation e) {
						}
						break;
					default:
						break;
				}
			}
		}

		return;
	}

	
}
