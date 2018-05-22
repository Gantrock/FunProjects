import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Bigram {
	Map<String, HashMap<String, Double>> count = new HashMap<String, HashMap<String, Double>>();
	Map<String, Double> prob = new HashMap<String, Double>();
	Map<String, HashMap<String, Double>> probFind = new HashMap<String, HashMap<String, Double>>();
	
	public int problem2() throws FileNotFoundException, UnsupportedEncodingException {
		double tempNum = 0.0;
		HashMap<String, Double> tempMap = new HashMap<String,Double>();
		String secKey = "";
		File toRead = new File("doyle-27.txt");
		Scanner fileRead = new Scanner(toRead);
		String line = "";
		if(fileRead.hasNext())
			line = fileRead.next().toLowerCase();
		while(fileRead.hasNext()) {
			secKey = fileRead.next().toLowerCase();
			if(count.containsKey(line)) {
				if(count.get(line).containsKey(secKey)) {
					tempNum = count.get(line).get(secKey) + 1.0;
					//tempMap = count.get(line);
					//tempMap.put(secKey, tempNum);
					count.get(line).put(secKey, tempNum);
				} else {
					//tempMap = new HashMap<String, Double>();
					//tempMap.put(secKey, 1.0);
					count.get(line).put(secKey, 1.0);
				}
				//count.put(line, count.get(line) + 1.0);
			} else {
				tempMap = new HashMap<String, Double>();
				tempMap.put(secKey, 1.0);
				count.put(line, tempMap);
			}
			line = secKey;
		}
		fileRead.close();
		double prevCount = 0.0;
		for(String key : count.keySet()) {
			prevCount = 0.0;
			for(String curr : count.get(key).keySet()) {
				prevCount += count.get(key).get(curr);
			}
			for(String toCount : count.get(key).keySet()) {
				prob.put(("P(" + key + "|" + toCount + ")"), (count.get(key).get(toCount)/prevCount));
				System.out.println("key: " + key + " subkey: " + toCount);
				tempMap = new HashMap<String, Double>();
				tempMap.put(toCount, (count.get(key).get(toCount)/prevCount));
				probFind.put(key, tempMap);
			}
		}
		
		PrintWriter write = new PrintWriter("bigram_probs.txt", "UTF-8");
		Random dice = new Random();
		int index = 0;
		int target = 0;
		for(int i = 0; i <= 100; i++) {
			index = 0;
			target = dice.nextInt(prob.size());
			for(String key : prob.keySet()) {
				index++;
				if(index == target) {
					write.write(key + "=" + prob.get(key) + "\r\n");
				}
			}
		}
		write.close();
		int sentences = 0;
		double sentProb = 1;
		double sentLen = 0;
		String targetWord = "";
		String prevWord = "";
		File toCalc = new File("doyle-case-27.txt");
		PrintWriter probWriter = new PrintWriter("bigram_eval.txt", "UTF-8");
		Scanner fileCalc = new Scanner(toCalc);
			while(fileCalc.hasNextLine() && sentences<=100) {
				sentLen = 0.0;
				line = fileCalc.nextLine();
				while(line.length()>0) {
					prevWord = targetWord;
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
					if(probFind.containsKey(prevWord)) {
						if(probFind.get(prevWord).containsKey(targetWord)) {
								sentProb *= probFind.get(prevWord).get(targetWord);						
						}
					} else {
						System.out.println("Problem 2");
					}
				}
				sentences++;
				probWriter.write("Sentence " + sentences + ": " + (1.0/Math.pow(sentProb, 1.0/sentLen)) + "\r\n");
				sentProb = 1.0;
			}
		fileCalc.close();
		probWriter.close();
		return 0;
	}
	
	public String showProb() {
		String show = "";
		for(String key : prob.keySet()) {
			/*if(key.equals("clothes")) {
				System.out.println(count.get(key).keySet());
			}*/
			if(prob.get(key) != 1.0) {
				show += key + "=" + String.format("%.2f", prob.get(key)) + "\r\n";
			}
		}
		return show;
	}
	
	public String toString() {
		String out = "";
		out += "key \t secondKey \t count\n";
		for(String key: count.keySet()) {
			for(String sec : count.get(key).keySet()) {
				out += key + " \t" + sec + " \t" + count.get(key).get(sec) + "\r\n";
			}
		}
		return out;
	}
}
