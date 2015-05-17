package bayseanNetwork;

public class GHC {
	
	public DAG dag;
	double score;
	
	public GHC(DAG dag, Score score){
		
		this.dag = dag;
		// compute score for this.dag here ...
		// call function executeGHC
		this.score = score.compute(this.dag);
		System.out.println(this.score);
		
		executeGHC(score);
		System.out.println(this.dag.toString());
		
		
	}
	
	public GHC(GHC ghc){
		
		this.dag = ghc.dag.clone();
		this.score = ghc.score;
		
	}
	
	public void executeGHC(Score score){
		
		GHC besttillnow = new GHC(this);
		GHC test = new GHC(this);
		
		System.out.println(this.dag);
		System.out.println(besttillnow.dag);
		System.out.println(test.dag);
		int k=0;
		boolean exit=false;
		
		while(true){
			for(int n=0; n<this.dag.data_set.num_var*2;n++){
				for(int j=this.dag.data_set.num_var;j<this.dag.data_set.num_var*2;j++){
				
					try {
						System.out.println("origem:" + n + " destino:" + j);
						test.dag.add(n, j);
						test.score = score.compute(test.dag);
						System.out.println(test.score);
						if(test.score>besttillnow.score){
							besttillnow.dag.add(n, j);
							besttillnow.score = test.score;//check if reset test is ok???
						}
							test.dag = this.dag.clone();
							test.score = this.score;
					} catch (IlegalOperation e) {
						e.printStackTrace();
					}
					
					
				}
			
			}
			for (int n=0; n<this.dag.data_set.num_var*2;n++){
				for(int j=this.dag.data_set.num_var; j<this.dag.data_set.num_var*2;j++){
					System.out.println("origem:" + n + " destino:" + j);
					test.dag.remove(n, j);
					test.score = score.compute(test.dag);
					System.out.println(test.score);
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
						System.out.println("origem:" + n + " destino:" + j);
						test.dag.reverse(n,j);
						test.score = score.compute(test.dag);
						System.out.println(test.score);
						if (besttillnow.score<test.score){
							besttillnow.dag.reverse(n,j);
							besttillnow.score=test.score;
						}
						test.dag = this.dag.clone();
						test.score = this.score;
					} catch (IlegalOperation e) {
						e.printStackTrace();
					}
					
				}
			}
			System.out.println(this.score);
			System.out.println(besttillnow.score);
			System.out.println(test.score);
			if(besttillnow.score>this.score){
				this.score=besttillnow.score;
				this.dag.dag= besttillnow.dag.dag.clone();
				k++;
				System.out.println("estou dentro do best!");
				continue;
			}else{
				k++;
				System.out.println("k: "+k);
				break;
			}
			
		}	
		
		System.out.println(besttillnow.score);
		besttillnow.dag.toString();
		
		
		
	}
	
	
		
		
		
		
		
		
	
	
	
	
	

}
