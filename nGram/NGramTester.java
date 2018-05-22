import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class NGramTester {

	public static void main(String[] args) {
		//alter these two if you wanted to use your own files.
		//The following methods were designed in plaintext in mind, not including the extension or using the wrong filetype might not work.
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
