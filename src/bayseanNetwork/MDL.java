package bayseanNetwork;

public class MDL extends LL {

	public MDL(DAG dag) {
		super(dag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double compute() {
		double value, b = 0;
		int N =  dag.data_set.num_var*2;
		value =  super.compute();
		for (int i = 0; i < N; i++) {
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
