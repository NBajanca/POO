package bayseanNetwork;

public class LL implements Score {
	@Override
	public double compute(DAG dag) {
		double ll = 0;
		for (int i = 0; i < dag.data_set.num_var * 2; i++) {
			int maxq;
			try {
				maxq = dag.maxq(i);
			} catch (NoParent e) {
				maxq = 1;
			}
			for (int j = 0; j < maxq; j++) {
				for (int k = 0; k < dag.data_set.ri[dag.realNode(i)]; k++) {
					int[] Nijk = dag.calcNijk(i, j, k);
					if (Nijk[0] == 0 || Nijk[1] == 0) continue;
					ll += Nijk[0] * (Math.log((double)Nijk[0]/Nijk[1])/Math.log(2));
				}
			}
		}
		return ll;
	}

}