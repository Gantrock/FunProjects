import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class NGramTester {

	public static void main(String[] args) {
		//alter these two if you wanted to use your own files.
		String fileName = "doyle-27.txt";
		String secFilename = "doyle-case-27.txt";
		
		Unigram one = new unigram();
		Bigram two = new Bigram();
		Smoothed plusDelta = new Smoothed();
		try {
			one.probabilities(fileName, secFileName);
			two.probabilities(fileName, secFileName);
			plusDelta.probabilities(fileName, secFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
