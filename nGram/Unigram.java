import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Unigram {
	//These are Instance variables so that they can be used with the toString() method as well as probabilities().
	Map<String, Double> dictionary = new HashMap<String, Double>();
	Map<String, Double> prob = new HashMap<String, Double>();
	
	public int probabilities(String filename, String secFileName) throws FileNotFoundException, UnsupportedEncodingException {
		//the total number of words read.
		int numWords = 0;
		double tempNum = 0.0;
		File toRead = new File(filename);
		Scanner fileRead = new Scanner(toRead);
		String line = "";
		//read every word from File
		while(fileRead.hasNext()) {
			numWords++;
			line = fileRead.next().toLowerCase();
			if(dictionary.containsKey(line)) {
				dictionary.put(line, dictionary.get(line) + 1.0);
			} else {
				dictionary.put(line, 1.0);
			}
			
		}
		fileRead.close();
		//for each through the dictionary and add it to the prob Hashmap
		for(String key : dictionary.keySet()) {
			tempNum = dictionary.get(key) / numWords;
			prob.put(key, tempNum);
		}
		PrintWriter write = new PrintWriter("unigram_probs.txt", "UTF-8");
		write.write("Key \t Probability\r\n");
		//iterate through the prob Hashmap and print each key, pair on a newline.
		for(String key : prob.keySet()) {
			write.write(key + " \t" + prob.get(key) + "\r\n");
		}
		write.close();
		/* Writing eval stuff
		 */
		int sentences = 0;
		double sentProb = 1;
		double sentLen = 0;
		String targetWord = "";
		File toCalc = new File(secFileName);
		PrintWriter probWriter = new PrintWriter("unigram_eval.txt", "UTF-8");
		Scanner fileCalc = new Scanner(toCalc);
		//The second file has capital letters and they need to be removed. This loop also prints 100 random sentences and their data to a file.
		while(fileCalc.hasNextLine() && sentences<=100) {
			sentLen = 0.0;
			line = fileCalc.nextLine();
			while(line.length()>0) {
				if(line.indexOf(" ") > 0) {
					targetWord = line.substring(0, line.indexOf(" ")).toLowerCase();
				} else if(line.indexOf(" ") == 0) {
					while(line.indexOf(" ") == 0) {
						line = line.substring(line.indexOf(" ")+1);
					}
					if(line.indexOf(" ") > 0) {
						targetWord = line.substring(0, line.indexOf(" ")).toLowerCase();
					}
				}else {
					targetWord = line.toLowerCase();
					line = "";
				}
				sentLen += line.indexOf(" ");
				line = line.substring(line.indexOf(" ") + 1);
				if(prob.containsKey(targetWord)) {
					sentProb *= prob.get(targetWord);
				} else {
					System.out.println("Error " + targetWord + " not found");
				}
			}
			sentences++;
			probWriter.write("Sentence " + sentences + ": " + (1.0/Math.pow(sentProb, 1.0/sentLen)) + "\r\n");
			sentProb = 1.0;
			
		}
		fileCalc.close();
		probWriter.close();
		return numWords;
	}
	
	public String toString() {
		String out = "";
		out += "key \t count\n";
		for(String key: dictionary.keySet()) {
			out += key + " \t" + dictionary.get(key) + "\r\n";
		}
		return out;
	}
}

