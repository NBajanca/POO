package bayseanNetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class GHC {
	private DAG dag;
	private int randrest = 0;
	private DAGGHC dag_randrest;
	private DAGGHC dag_best;
	private DAGGHC dag_best_iteration;
	private DAGGHC dag_test;
	private ArrayList<Integer> TABU = new ArrayList<Integer>();
	private Score score;
	
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
	
	//Perform Algorathim 2 - GHC algorithm for learning DBN's, with TABU list and random restarts
	private void calcGHC(Score score) {
		
		//While C is not satisfied
		while(true){
			// N'' (dag_best_iteration) = Best from neighbourhood (best of the dag_test)
			for (int i = 0; i < dag_best.data_set.num_var*2; i++) {
				for (int i1 = dag_best.data_set.num_var; i1 < dag_best.data_set.num_var*2; i1++) {
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
	
	private boolean stopCondition() {
		
		if(dag_best_iteration.score > dag_best.score){
			//If score of N''(dag_best_iteration.score) > N_rest(dag_best.score) does Nres = N''
			TABU.add(Arrays.deepHashCode(dag_best.dag));
			dag_best = dag_best_iteration;
			dag_best_iteration = dag_best.clone();
			dag_test = dag_best.clone();
		}else{
			//If Score of N'(dag_best.score) > N''(dag_best_iteration.score) does random(N')
			return randomRestart();
		}
			
		return false;
	}

	
	private boolean randomRestart(){
		if (dag_randrest.score < dag_best.score){
			dag_randrest = dag_best;
			dag.dag = dag_randrest.dag;
		}
		
		//Stoping Condition (Max Random Restarts reached)
		if (randrest == 0) return true;
		else randrest --;
		dag_best = dag_randrest.clone();
		dag_best.random();
		
		return false;
	}
	
	//Calls Score Verification of N(dag_test) - that belongs to the neighbourhood of N'(dag_best)
	//If N is not in the TABU list
	private void TABUVerify() {
		int hash_dag_test = Arrays.deepHashCode(dag_test.dag);
		for (Integer hash_dag_tabu : TABU) {
			if (hash_dag_tabu == hash_dag_test) return;
		}
		scoreVerify(score.compute(dag_test));
		return;
	}
	
	//Verifies if N(dag_test) has better score than N''(dag_best_iteration)
	//Clones N' to test next neighbour
	private void scoreVerify(double score) {
		dag_test.score = score;
		if (score > dag_best_iteration.score){
			dag_best_iteration = dag_test;
		}
		dag_test = dag_best.clone();
		
	}
	

}
