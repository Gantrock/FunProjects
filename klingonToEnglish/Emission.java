import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Emission {

	HashMap <String, double[]> eTable = new HashMap<String, double[]>();
	//All part-of-speech tag counts start with .1 for smoothing.
	double noun = 0.1;
	double verb = 0.1;
	double conj = 0.1;
	double pro = 0.1;
	
	public Emission(String filename){
		buildTable(filename);
	}
	
	public void buildTable(String filename) {
		String tuple = "";
		String type = "";
		String word = "";
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
					if(eTable.containsKey(word)) {
						addType(word, type);							
					} else {
						eTable.put(word, new double[4]);
						eTable.get(word)[0] += 0.1;
						eTable.get(word)[1] += 0.1;
						eTable.get(word)[2] += 0.1;
						eTable.get(word)[3] += 0.1;
						addType(word, type);
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("\t\tNOUN\tVERB\tCONJ\tPRO");
		for(String key : eTable.keySet()) {
			System.out.println(key + "\t\t" + eTable.get(key)[0] + "\t" + eTable.get(key)[1] + "\t" + eTable.get(key)[2] + "\t" + eTable.get(key)[3]);
		}
		System.out.println("Totals\t\t" + noun + "\t" + verb + "\t" + conj + "\t" + pro);

	}
	
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
			}
	}
	
	public String maxTag(String theWord) {
		String tag = "";
		double max = 0;
		for(int i = 0; i < 4; i++) {
			if(eTable.containsKey(theWord)) {
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
			} else {
				if(noun > verb && noun > conj && noun > pro) {
					tag = "N";
				} else if (verb > conj && verb > pro) {
					tag = "V";
				} else if (conj > pro) {
					tag = "CONJ";
				} else {
					tag = "PRO";
				}
				eTable.put(theWord, new double[4]);
				eTable.get(theWord)[0] += 0.1;
				eTable.get(theWord)[1] += 0.1;
				eTable.get(theWord)[2] += 0.1;
				eTable.get(theWord)[3] += 0.1;
			}
		}		
		return tag;
	}
	
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
		} else {
			probTag = 0.1;
		}
		return probTag;
	}
	
 	public double probE(String theWord, String theType) {
		double sent = 0.0;
		switch (theType) {
		case "N":
			sent = eTable.get(theWord)[0]/noun;
			break;
		case "V":
			sent = eTable.get(theWord)[1]/verb;
			break;
		case "CONJ":
			sent = eTable.get(theWord)[2]/conj;
			break;
		case "PRO":
			sent = eTable.get(theWord)[3]/pro;
			break;
		}
		return sent;
	}
}
