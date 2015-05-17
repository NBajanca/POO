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
	
	GHC(DAG dag, Score score, int randrest){
		this.dag = dag;
		this.randrest = randrest;
		dag_randrest = new DAGGHC (dag,  score); 
		dag_best = dag_randrest.clone();
		dag_best_iteration =  dag_randrest.clone();
		dag_test =  dag_randrest.clone();
		
		calcGHC(score);
	}

	private void calcGHC(Score score) {
		while(true){
			for (int i = 0; i < dag_best.data_set.num_var*2; i++) {
				for (int i1 = dag_best.data_set.num_var; i1 < dag_best.data_set.num_var*2; i1++) {
					try{
						dag_test.add(i, i1);
						if (TABUVerify()){
							scoreVerify(score.compute(dag_test));
						}
					}catch(IlegalOperation e){
					}
					try{
						dag_test.remove(i, i1);
						if (TABUVerify()){
							scoreVerify(score.compute(dag_test));
						}
					}catch(IlegalOperation e){
					}
					try{
						dag_test.reverse(i, i1);
						if (TABUVerify()){
							scoreVerify(score.compute(dag_test));
						}
					}catch(IlegalOperation e){
					}
				}
			}
			if (stopCondition()) break;
		}
		
		
	}

	private boolean TABUVerify() {
		int hash_dag_test = Arrays.deepHashCode(dag_test.dag);
		for (Integer hash_dag_tabu : TABU) {
			if (hash_dag_tabu == hash_dag_test) return false;
		}
		return true;
	}

	private boolean stopCondition() {
		if(dag_best_iteration.score > dag_best.score){
			TABU.add(Arrays.deepHashCode(dag_best.dag));
			dag_best = dag_best_iteration;
			dag_best_iteration = dag_best.clone();
			dag_test = dag_best.clone();
		}else{
			return randomRestart();
		}
			
		return false;
	}

	private void scoreVerify(double score) {
		dag_test.score = score;
		if (score > dag_best_iteration.score){
			dag_best_iteration = dag_test;
		}
		dag_test = dag_best.clone();
		
	}
	
	private boolean randomRestart(){
		if (dag_randrest.score < dag_best.score){
			dag_randrest = dag_best;
			dag.dag = dag_randrest.dag;
		}
		
		if (randrest == 0) return true;
		else randrest --;
		dag_best = dag_randrest.clone();
		dag_best.random();
		
		return false;
	}
	

}
