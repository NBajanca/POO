package bayseanNetwork;

import java.util.Random;

/**
 * The Class DAGGHC 
 * Contains a DAG and also a score value associated, very useful to construct the DBN.
 * Extends Dag. Changes: Has score (double), Overrides clone(method), Implements random(method)
 */
class DAGGHC extends DAG {
	
	/** The score. */
	private double score;

	/**
	 * Instantiates a new DAG and computes it's score
	 *
	 * @param master the master
	 * @param score the score
	 */
	DAGGHC(DAG master, Score score) {
		super(master);
		this.setScore(score.compute(this));
	}
	
	/**
	 * Instantiates a new DAG and saves it's score
	 *
	 * @param master the master
	 * @param score the score
	 */
	DAGGHC(DAG master, double score) {
		super(master);
		this.setScore(score);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected DAGGHC clone() {
		
		DAG objecto = new DAG(this);
		objecto.setData_set(this.getData_set());
		objecto.setDag(new boolean[this.getData_set().getNum_var()*2][this.getData_set().getNum_var()]);
		for(int n=0; n<objecto.getData_set().getNum_var()*2;n++){
			for(int j=0; j<objecto.getData_set().getNum_var();j++){
				objecto.getDag()[n][j]=this.getDag()[n][j];
			}
		}
		
		DAGGHC dag_test =  new DAGGHC (objecto , this.getScore());
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
		for (int i = 0; i < getData_set().getNum_var()*2; i++) {
			for (int j = 0; j < getData_set().getNum_var(); j++) {
				switch(random.nextInt(2 + getData_set().getNum_var())){
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

	/**
	 * @return the score
	 */
	protected double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	protected void setScore(double score) {
		this.score = score;
	}

	
}
