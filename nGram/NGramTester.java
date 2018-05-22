import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class NGramTester {

	public static void main(String[] args) {
		Problem1 prob = new Problem1();
		Problem2 prob2 = new Problem2();
		Problem3 prob3 = new Problem3();
		try {
			prob.problem1();
			prob2.problem2();
			prob3.problem3();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
