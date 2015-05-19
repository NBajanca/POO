package bayseanNetwork;

/**
* The Class IlegalOperation 
* Implements exception for most commonly used DAG methods, mainely add() remove() and reverse()
*/
@SuppressWarnings("serial")
public class IlegalOperation extends Exception {
	
	public IlegalOperation(String message){
		//System.out.println(message);
	}
	
	public IlegalOperation(){
		//System.out.println("Ilegal Operation");
	}

}
