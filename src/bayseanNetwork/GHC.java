package bayseanNetwork;

public class GHC {
	
	public DAG dag;
	double score;
	
	public GHC(DAG dag, Score score){
		
		this.dag = dag;
		// compute score for this.dag here ...
		// call function executeGHC
		this.score = score.compute(this.dag);
		
		executeGHC(score);
		
		
	}
	
	public GHC(GHC ghc){
		
		this.dag = ghc.dag.clone();
		this.score = ghc.score;
		
	}
	
	public void executeGHC(Score score){
		
		GHC besttillnow = new GHC(this);
		GHC test = new GHC(this);
		
		int k=0;
		
		while(true){
			for(int n=0; n<this.dag.data_set.num_var*2;n++){
				for(int j=this.dag.data_set.num_var;j<this.dag.data_set.num_var*2;j++){
				
					try {
						test.dag.add(n, j);
						test.score = score.compute(test.dag);
						if(test.score>besttillnow.score){
							besttillnow.dag.add(n, j);
							besttillnow.score = test.score;//check if reset test is ok???
						}
							test.dag = this.dag.clone();
							test.score = this.score;
					} catch (IlegalOperation e) {
					}
					
					
				}
			
			}
			for (int n=0; n<this.dag.data_set.num_var*2;n++){
				for(int j=this.dag.data_set.num_var; j<this.dag.data_set.num_var*2;j++){
					test.dag.remove(n, j);
					test.score = score.compute(test.dag);
					if (besttillnow.score<test.score){
						besttillnow.dag.remove(n, j);
						besttillnow.score = test.score;
					}
							test.dag = this.dag.clone();
							test.score = this.score;
					}
					
				}
			for (int n=this.dag.data_set.num_var; n<this.dag.data_set.num_var*2;n++){
				for (int j=this.dag.data_set.num_var; j<this.dag.data_set.num_var*2;j++){
					try {
						test.dag.reverse(n,j);
						test.score = score.compute(test.dag);
						if (besttillnow.score<test.score){
							besttillnow.dag.reverse(n,j);
							besttillnow.score=test.score;
						}
						test.dag = this.dag.clone();
						test.score = this.score;
					} catch (IlegalOperation e) {
					}
					
				}
			}
			if(besttillnow.score>this.score){
				this.score=besttillnow.score;
				this.dag.dag= besttillnow.dag.dag.clone();
				k++;
				continue;
			}else{
				k++;
				break;
			}
			
		}
	}
	
	
		
		
		
		
		
		
	
	
	
	
	

}
