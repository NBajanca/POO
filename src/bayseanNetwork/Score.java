package bayseanNetwork;

/**
 * The Interface Score
 * Provides a method to compute DAG scores.
 */
public interface Score {
	
	/**
	 * Compute the score of the DAG
	 *
	 * @param dag the dag
	 * @return the double
	 */
	double compute(DAG dag);

}