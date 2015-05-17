package bayseanNetwork;

public class GHC {
	private DAG dag;
	private DAGGHC dag_best;
	private DAGGHC dag_best_iteration;
	private DAGGHC dag_test;
	
	public GHC(DAG dag, Score score){
		this.dag = dag;
		dag_best = new DAGGHC (dag,  score);
		dag_best_iteration =  new DAGGHC (dag.clone(),  score);
		dag_test =  new DAGGHC (dag.clone(),  score);
		
		calcGHC(score);
	}

	private void calcGHC(Score score) {
		while(true){
			for (int i = 0; i < dag_best.data_set.num_var*2; i++) {
				for (int i1 = 0; i1 < dag_best.data_set.num_var*2; i1++) {
					try{
						dag_test.add(i, i1);
						scoreVerify(score.compute(dag_test));
						
					}catch(IlegalOperation e){
						
					}
					try{
						dag_test.remove(i, i1);
						scoreVerify(score.compute(dag_test));
						
					}catch(IlegalOperation e){
						
					}
					try{
						dag_test.reverse(i, i1);
						scoreVerify(score.compute(dag_test));
						
					}catch(IlegalOperation e){
						
					}
				}
			}
			if (stopCondition()) break;
		}
		
		
	}

	private boolean stopCondition() {
		if(dag_best_iteration.score > dag_best.score){
			dag_best = dag_best_iteration;
			dag_best_iteration = dag_best.clone();
			dag_test = dag_best.clone();
		}else{
			dag.dag = dag_best.dag;
			return true;
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
	

}
