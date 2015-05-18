package bayseanNetwork;

/**
 * The Class LL
 * Implements Score
 */
public class LL implements Score {
	
	/* (non-Javadoc)
	 * @see bayseanNetwork.Score#compute(bayseanNetwork.DAG)
	 */
	@Override
	public double compute(DAG dag) {
		double ll = 0;
		//For all nodes
		for (int i = 0; i < dag.getData_set().getNum_var() * 2; i++) {
			int maxq;
			try {
				maxq = dag.maxq(i);
			} catch (NoParent e) {
				maxq = 1;
			}
			//And all Parent Configurations
			for (int j = 0; j < maxq; j++) {
				//And all possible values
				for (int k = 0; k < dag.getData_set().getRi()[dag.realNode(i)]; k++) {
					int[] Nijk = dag.calcNijk(i, j, k);
					//If Nijk or Nij are 0 returns LL  for that equals to 0
					if (Nijk[0] == 0 || Nijk[1] == 0) continue;
					ll += Nijk[0] * (Math.log((double)Nijk[0]/Nijk[1])/Math.log(2));					
				}
			}
		}
		return ll;
	}

}