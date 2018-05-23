import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Emission {
	
	final double BASE = 0.1;

	HashMap <String, double[]> eTable = new HashMap<String, double[]>();
	//All part-of-speech tag counts start with .1 for smoothing. These values represent the total for each tag.
	double noun = 0.1;
	double verb = 0.1;
	double conj = 0.1;
	double pro = 0.1;
	
	/**
	 * The one parameter constructor for an Emission table.
	 * @param filename
	 */
	public Emission(String filename){
		buildTable(filename);
	}
	
	/**
	 * Takes a file pointing to a training file and creates the table.
	 * @param filename is the name of the training file
	 */
	public void buildTable(String filename) {
		String tuple = "";
		String type = "";
		String word = "";
		//the index of a space to be used in parsing the training data.
		int slashFind = 0;
		File toRead = new File(filename);
		Scanner reader;
		try {
			reader = new Scanner(toRead);
			while(reader.hasNext()) {
				tuple = reader.next();
				if(tuple.contains("/")) {
					slashFind = tuple.indexOf("/");
					//System.out.println("Word " + tuple + " " + slashFind);
					word = tuple.substring(0, slashFind);
					type = tuple.substring(slashFind+1);
					//if the table contains a word add it as a key and it's part-of-speech tag.
					if(eTable.containsKey(word)) {
						//adds the word to the table and increments it's type count.
						addType(word, type);							
					} else {
						/*If the word is new, creates a double array of size 5.
						 * after adding it each index is filled with the base value */
						eTable.put(word, new double[5]);
						eTable.get(word)[0] += BASE;
						eTable.get(word)[1] += BASE;
						eTable.get(word)[2] += BASE;
						eTable.get(word)[3] += BASE;
						eTable.get(word)[4] += 4 * BASE;
						addType(word, type);
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//Prints the table.
		System.out.println("\t\tNOUN\tVERB\tCONJ\tPRO");
		for(String key : eTable.keySet()) {
			System.out.println(key + "\t\t" + eTable.get(key)[0] + "\t" + eTable.get(key)[1] + "\t" + eTable.get(key)[2] + "\t" + eTable.get(key)[3]);
		}
		System.out.println("Totals\t\t" + noun + "\t" + verb + "\t" + conj + "\t" + pro);

	}
	/**
	 * Adds the word to the table and increments the totals for the specificed type.
	 * @param theWord
	 * @param theType
	 */
	public void addType(String theWord, String theType) {
		// N = 1, V = 2, CONJ = 3, PRO = 4
		switch (theType) {
		case "N":
			eTable.get(theWord)[0]++;
			noun++;
			break;
		case "V":
			eTable.get(theWord)[1]++;
			verb++;
			break;
		case "CONJ":
			eTable.get(theWord)[2]++;
			conj++;
			break;
		case "PRO":
			eTable.get(theWord)[3]++;
			pro++;
			break;
		default:
			System.out.println("Type not found " + theType + " found instead");
			eTable.get(theWord)[4]--;
			}
		//if this method is reached we have a word (or an error in types for the training set)
		eTable.get(theWord)[4]++;
	}
	
	/**
	 * Finds the highest value tag for the specified word
	 * @param theWord
	 * @return returns a String representing a part-of-speech tag.
	 */
	public String maxTag(String theWord) {
		String tag = "";
		double max = 0;
		/*if the table doesn't contain the theWord pick the highest total 
		 * part-of-speech tag then add the word to the eTable*/
		if(!eTable.containsKey(theWord)) {
			if(noun > verb && noun > conj && noun > pro) {
				tag = "N";
			} else if (verb > conj && verb > pro) {
				tag = "V";
			} else if (conj > pro) {
				tag = "CONJ";
			} else {
				tag = "PRO";
			}
			eTable.put(theWord, new double[5]);
			eTable.get(theWord)[0] += BASE;
			eTable.get(theWord)[1] += BASE;
			eTable.get(theWord)[2] += BASE;
			eTable.get(theWord)[3] += BASE;
			eTable.get(theWord)[4] += 4 * BASE;
		} else {
			/*if the table contains theWord iterate through the 
			 * part-of-speech totals and find the max. We only go up to four
			 * because the fifth index contains the total.*/
			for(int i = 0; i < 4; i++) {
					if(eTable.get(theWord)[i] > max){
						max = eTable.get(theWord)[i];
						if(i == 0) {
							tag = "N";
						} else if (i == 1) {
							tag = "V";
						} else if (i == 2) {
							tag = "CONJ";
						} else if (i == 3) {
							tag = "PRO";
						}
					}
			}	
		}
	
		return tag;
	}
	
	/**
	 * Returns the probability of theTag in relation to theWord
	 * @param theWord
	 * @param theTag
	 * @return the probability of theWord having theTag
	 */
	public double probAtTag(String theWord, String theTag){
		double probTag = 0.0;
		if(eTable.containsKey(theWord)) {
			switch (theTag) {
			case"N":
				probTag = eTable.get(theWord)[0];
				break;
			case"V":
				probTag = eTable.get(theWord)[1];
				break;
			case"CONJ":
				probTag = eTable.get(theWord)[2];
				break;
			case"PRO":
				probTag = eTable.get(theWord)[3];
				break;
			}
			probTag = probTag / eTable.get(theWord)[4];
		} else {
			probTag = BASE;
		}
		
		return probTag;
	}
	
}
