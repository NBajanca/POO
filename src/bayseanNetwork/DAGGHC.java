package bayseanNetwork;

import java.util.Random;

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
	
	protected void random() {
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
