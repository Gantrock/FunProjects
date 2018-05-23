public class KtoEMain {
	//mughwI' = translator?
	public static void main(String[] args) {
		String filename = "Klingon_Train.txt";
    //change target string below to test against a custom phrase/sentence
		String target = "tera’ngan legh yaS";
    //Creates the Emission table
		Emission eTable = new Emission(filename);
    //Creates the Transition table
		Transition tTable = new Transition(filename);
    //Uses the Emission and Transition tables to decode the phrase.
		Decoding decoder = new Decoding(eTable, tTable);
		decoder.decode(target);
		System.out.println();
		System.out.println("Complete");

	}

}
