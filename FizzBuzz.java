public class Fizzbuzz {

	public static void main(String[] args) {
		for(int i = 1; i < 101; i++) {
			System.out.println(fizzbuzz(i));
		}

	}
	
	private static String fizzbuzz(int num) {
		String result = "";
		if(num % 3 == 0) {
			result += "fizz";
		}
		if(num % 5 == 0) {
			result += "buzz";
		}
		if(num % 3 != 0 && num % 5 !=0) {
			result = String.valueOf(num);
		}
		return result;
	}

}
