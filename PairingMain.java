/**
 * @author Sean Hill @ Gantrock
 * @date 12/18/2017
 */
package model;

public class PairingMain {

	public static void main(String[] args) {
		/*Test cases used/ Result
		 * "Ted"/True
		 * "(Ted)"/True
		 * "[Ted]"/True
		 * "{Ted}"/True
		 * "{([Ted])}"/True
		 * "{([Ted)}"/False
		 * "{([Ted]}"/False
		 * "{([Ted"/False
		 * "{(Turtle)Ted}"/True
		 * "{(Turtle)Ted[Square]}"
		 * "{(Turtle())Ted[Square]}"
		 * "{}"
		 * 
		 */
		String test = "{}";
		Pairer finder = new Pairer();
		
		System.out.println(test + " is " + finder.findPair(test));

	}

}
