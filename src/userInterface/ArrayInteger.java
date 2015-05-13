package userInterface;

public class ArrayInteger {

	public ArrayInteger (int[] array_received, int size){
		
		int [] array = new int[size];
		for (int i=0; i<size; i++){
			array[i]=array_received[i];
		}
		
		
	}
	/*public ArrayInteger (int size){
		ArrayInteger array = new int[size];
		return array;
		
	}
	
	public ArrayInteger add(int i, ArrayInteger generico){
		generico[i]= i;
		return generico;
	}
	
	public int[] addIntegers(int [] array_received, ArrayInteger generico,int size){
		
		for (int i=0; i<size; i++){
			generico[i]=array_received[i];
		}
		return array;
		
	}*/
}
