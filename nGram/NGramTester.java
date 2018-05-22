import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class NGramTester {

	public static void main(String[] args) {
		Unigram one = new unigram();
		Bigram two = new Bigram();
		Smoothed plusDelta = new Smoothed();
		try {
			one.probabilities();
			two.probabilities();
			plusDelta.probabilities();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
