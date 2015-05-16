package bayseanNetwork;

public class GHC {
	
	public DAG dag;
	float score;
	
	public GHC(DAG dag){
		
		this.dag = dag;
		// compute score for this.dag here ...
		// call function executeGHC
		
		
	}
	
	public GHC(GHC ghc){
		
		try {
			this.dag = ghc.dag.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.score = ghc.score;
		
		
	}
	
	public void executeGHC(){
		
		GHC besttillnow = new GHC(this);
		GHC test = new GHC(this);
		
		do{
			for(int n=0; n<this.dag.data_set.num_var*2;n++){
				for(int j=this.dag.data_set.num_var;j<this.dag.data_set.num_var*2;j++){
				
					try {
						test.dag.add(n, j);
						//compute score here
						if(besttillnow.score<test.score){
							besttillnow.dag.add(n, j);
							besttillnow.score = test.score; //check if reset test is ok???
						}else{
							test.dag.dag = this.dag.dag.clone();
							test.score = this.score;
						}
					} catch (IlegalOperation e) {
						e.printStackTrace();
					}
					
					
				}
			
			}
			for (int n=0; n<this.dag.data_set.num_var*2;n++){
				for(int j=this.dag.data_set.num_var; j<this.dag.data_set.num_var*2;j++){
					
					test.dag.remove(n, j);
					// compute test score here
					if (besttillnow.score<test.score){
						besttillnow.dag.remove(n, j);
						besttillnow.score = test.score;
					}
						test.dag.dag = this.dag.dag.clone();
						test.score = this.score;
					}
					
				}
			for (int n=this.dag.data_set.num_var; n<this.dag.data_set.num_var*2;n++){
				for (int j=this.dag.data_set.num_var; j<this.dag.data_set.num_var*2;j++){
					try {
						test.dag.reverse(n,j);
						// compute test score here
						if (besttillnow.score<test.score){
							besttillnow.dag.reverse(n,j);
							besttillnow.score=test.score;
						}
						test.dag.dag = this.dag.dag.clone();
						test.score = this.score;
					} catch (IlegalOperation e) {
						e.printStackTrace();
					}
				}
			}
			
			if(besttillnow.score>this.score){
				this.score=besttillnow.score;
				this.dag.dag= besttillnow.dag.dag.clone();
			}
			
		}while(besttillnow.score>this.score);	
		
	}
		
		
		
		
		
		
	
	
	
	
	

}
