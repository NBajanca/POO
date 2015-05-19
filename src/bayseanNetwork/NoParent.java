package bayseanNetwork;
/**
* The Class NoParent 
* Implements exception for DAG methods namely calcNijk, maxq, fromParentConfiguration
*/
@SuppressWarnings("serial")
public class NoParent extends Exception {
	
	public NoParent() {
		// TODO Auto-generated constructor stub
		super("NoParent");
	}
	
	public NoParent(String message) {
		 super(message);
	} 

}
