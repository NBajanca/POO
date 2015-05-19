package bayseanNetwork;

/**
* The Class NoParent 
* Implements exception if ParentConfiguration is invalid.
*/
@SuppressWarnings("serial")
public class PCInvalid extends Exception {
	//Parent Configuration Invalid
	
	public PCInvalid() {
		// TODO Auto-generated constructor stub
		super("PCInvalid");
	}
	
	public PCInvalid(String message) {
		 super(message);
	} 

}
