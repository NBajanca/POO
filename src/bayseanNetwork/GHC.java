package bayseanNetwork;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class GHC 
 * Implements the algorithm 2, using TABU list and random restarts in order obtain the DBN
 */
public class GHC {
	
	/** The dag - DAG from where inference is going to be done */
	private DAG dag;
	
	/** The randrest - Number of Random Restarts. Inserted by the user */
	private int randrest = 0;
	
	/** The dag_randrest - Best DAG from the several Random Restart done */
	private DAGGHC dag_randrest;
	
	/** The dag_best - Best DAG since the last Random Restart */
	private DAGGHC dag_best;
	
	/** The dag_best_iteration - Best DAG from the neigbouthood being evaluated */
	private DAGGHC dag_best_iteration;
	
	/** The dag_test - DAG being tested */
	private DAGGHC dag_test;
	
	/** The TABU - list with the hash of DAGs visited */
	private ArrayList<Integer> TABU = new ArrayList<Integer>();
	
	/** The score - Score to use. Inserted by the user */
	private Score score;
	
	/**
	 * Instantiates a new ghc.
	 * Creates a DAGGHC for randrest and clones this object for each one of the other DAGGHC
	 *
	 * @param dag the dag
	 * @param score the score
	 * @param randrest the randrest
	 */
	GHC(DAG dag, Score score, int randrest){
		this.dag = dag;
		this.randrest = randrest;
		this.score = score;
		dag_randrest = new DAGGHC (dag,  score); 
		dag_best = dag_randrest.clone();
		dag_best_iteration =  dag_randrest.clone();
		dag_test =  dag_randrest.clone();
		
		calcGHC(score);
	}
	
	/**
	 * Performs Algorathim 2 - GHC algorithm for learning DBN's, with TABU list and random restarts
	 *
	 * @param score the score
	 */
	private void calcGHC(Score score) {
		
		//While C is not satisfied
		while(true){
			// N'' (dag_best_iteration) = Best from neighbourhood (best of the dag_test)
			for (int i = 0; i < dag_best.getData_set().getNum_var()*2; i++) {
				for (int i1 = dag_best.getData_set().getNum_var(); i1 < dag_best.getData_set().getNum_var()*2; i1++) {
					try{
						dag_test.add(i, i1);
						TABUVerify();
					}catch(IlegalOperation e){
						try{
							dag_test.remove(i, i1);
							TABUVerify();
						}catch(IlegalOperation e1){
							continue;
						}
						try{
							dag_test.reverse(i, i1);
							TABUVerify();
						}catch(IlegalOperation e2){}
					}
				}
			}
			if (stopCondition()) break;
		}	
	}
	
	/**
	 * Stop condition function
	 * If local optima is reached calls random.
	 * If random restarts maximum number is reached the stop condition is reached.
	 *
	 * @return true, if successful
	 */
	private boolean stopCondition() {
		
		if(dag_best_iteration.getScore() > dag_best.getScore()){
			//If score of N''(dag_best_iteration.score) > N_rest(dag_best.score) does Nres = N''
			TABU.add(Arrays.deepHashCode(dag_best.getDag()));
			dag_best = dag_best_iteration;
			dag_best_iteration = dag_best.clone();
			dag_test = dag_best.clone();
		}else{
			//If Score of N'(dag_best.score) > N''(dag_best_iteration.score) does random(N')
			return randomRestart();
		}
			
		return false;
	}

	
	/**
	 * Random restart.
	 * Creates a random DAG from best DAG until now
	 *
	 * @return true, if successful
	 */
	private boolean randomRestart(){
		if (dag_randrest.getScore() < dag_best.getScore()){
			dag_randrest = dag_best;
			dag.setDag(dag_randrest.getDag());
		}
		
		//Stoping Condition (Max Random Restarts reached)
		if (randrest == 0) return true;
		else randrest --;
		dag_best = dag_randrest.clone();
		dag_best.random();
		
		return false;
	}
	
	/**
	 * Calls Score Verification of N(dag_test) - that belongs to the neighbourhood of N'(dag_best)
	 * If N is not in the TABU list
	 * 
	 * Details:
	 * TABU list is implement thrue an hash of the DAG
	 */
	private void TABUVerify() {
		int hash_dag_test = Arrays.deepHashCode(dag_test.getDag());
		for (Integer hash_dag_tabu : TABU) {
			if (hash_dag_tabu == hash_dag_test) return;
		}
		scoreVerify(score.compute(dag_test));
		return;
	}
	
	
	/**
	 * Verifies if N(dag_test) has better score than N''(dag_best_iteration)
	 * Clones N' to test next neighbour
	 *
	 * @param score the score
	 */
	private void scoreVerify(double score) {
		dag_test.setScore(score);
		if (score > dag_best_iteration.getScore()){
			dag_best_iteration = dag_test;
		}
		dag_test = dag_best.clone();
		
	}
	

}
