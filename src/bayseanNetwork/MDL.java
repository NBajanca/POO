package bayseanNetwork;

public class MDL extends LL {
	/* (non-Javadoc)
	 * @see bayseanNetwork.LL#compute(bayseanNetwork.DAG)
	 */
	@Override
	public double compute(DAG dag) {
		double value, b = 0;
		int N =  dag.data_set.data.size();
		value =  super.compute(dag);
		
		for (int i = 0; i < dag.data_set.num_var*2; i++) {
			int q = 1;
			try {
				q = dag.maxq(i);
			} catch (NoParent e) {
				q = 1;
			}
			b += (dag.data_set.ri[dag.realNode(i)]-1)*q;
		}	
		
		value -= 0.5*(Math.log(N)/Math.log(2))*Math.abs(b);
		return value;
	}
	
	

}