import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Decoding {
/*tera'ngan legh yaS*/
	private Emission eMitr;
	private Transition tRnstr;
	private PriorityQueue<Double> q = new PriorityQueue<Double>();
	private PriorityQueue<Double> t = new PriorityQueue<Double>();
	
	public Decoding(Emission theETable, Transition theTTable) {
		eMitr = theETable;
		tRnstr = theTTable;
	}
	
	public void decode(String target) {
		Scanner key = new Scanner(target);
		String word = "";
		String predictTag = "";
		double prevValue = 0.0;
		Queue<String> words = new LinkedList<String>();
		Queue<String> tag = new LinkedList<String>();
		Queue<Double> results = new LinkedList<Double>();
			addAllInLine("START");
			predictTag = tRnstr.maxTag("START");
			tag.add(predictTag);
			//results.add(1 * t.poll());
			prevValue = (1 * t.poll());
		while(key.hasNext()) {
			word = key.next();
			words.add(word);
			addAllInLine(predictTag);
			tag.add(tRnstr.maxTag(predictTag));
			predictTag = tRnstr.maxTag(predictTag);
			q.add(eMitr.probAtTag(word, predictTag));
			prevValue = q.poll() * t.poll() * prevValue;
			results.add(prevValue);			
		}
		key.close();
		while(!words.isEmpty() && !tag.isEmpty()) {
			System.out.print(words.poll() + "/" + tag.poll() + " ");
		}

	}
	private void addAllInLine(String theTag) {
		t.add(tRnstr.probT(theTag, "N"));
		t.add(tRnstr.probT(theTag, "V"));
		t.add(tRnstr.probT(theTag, "CONJ"));
		t.add(tRnstr.probT(theTag, "PRO"));
		removeT();
	}
	
	/**
	 * Removes all but the largest entry in the priority queue
	 */
	private void removeT() {
		while(t.size() > 1) {
			t.poll();
		}
	}
}
