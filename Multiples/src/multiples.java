
public class multiples {

	public static void main(String[] args) {
		System.out.println("3: " + calcMultiples(3, 1000));
		System.out.println("5: " + calcMultiples(5, 1000));
	}
	
	private static int calcMultiples(int myTarget, int myLimit) {
		int value = 0;
		for(int i = 0; i < myLimit; i++) {
			if(i % myTarget == 0) {
				value += i;
			}
		}
		
		return value;
	}

}
