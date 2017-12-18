/**
 * @author Sean Hill @ Gantrock
 * @date 12/18/2017
 */
package model;

public class Pairer {

	/**
	 * A method to find whether a string contains a predetermined set of paired characters.
	 * These characters (and their pair) are: (/), {/}, and [/].
	 * @param theBase, the String to be checked
	 * @return a boolean indicating whether all of the potential pairs are in fact paired
	 */
	public boolean findPair(String theBase) {
		boolean isFound = false;
		String toPair = "";
		
		//Find the index of the parenthesis, bracket, or curly brace.
		int indPar = theBase.length() + 1;
		int indBrack = theBase.length() + 1;
		int indCurl = theBase.length() + 1;
		if(theBase.contains("(")) {
			indPar = theBase.indexOf("(");
		}
		if(theBase.contains("[")) {
			indBrack = theBase.indexOf("[");
		}
		if(theBase.contains("{")) {
			indCurl = theBase.indexOf("{");
		}
		
		//Determine which index is first.
		if(indPar < indBrack && indPar < indCurl) {
			toPair = "(";
		} else if (indBrack < indCurl && indBrack < indPar) {
			toPair = "[";
		} else if (indCurl < indBrack && indCurl < indPar) {
			toPair = "{";
		} else {
			toPair = "false";
			System.out.println("No opening parenthesis, brackets, or curly brackets found");
		}
		
		/*Check to find pair,if paired char is found keep searching until no more pairs 
		 * to find OR a broken pair is found
		*/
		if(findLast(theBase, toPair) < theBase.length() + 1 && !toPair.equals("false")) {
			isFound = findPair(theBase.substring(theBase.indexOf(toPair) + 1, findLast(theBase, toPair)));
		} else if (toPair.equals("false")) {
			isFound = true;
		} else {
			System.out.println(toPair + "'s match not found");
		}
		return isFound;
	}
	
	/**
	 * Finds the last instance of any of the indicated pairs
	 * @param theBase, the string to be searched
	 * @param thePair, the Character to be paired against
	 * @return the index where the paired character was found or theBase.length() + 1 if not found
	 */
	private int findLast(String theBase, String thePair){
		int index = 0;
		String pair = "";
		//Determine which item needs to be checked.
		if (thePair.equals("(")){
			pair = ")";
		} else if (thePair.equals("[")) {
			pair = "]";
		} else if (thePair.equals("{")) {
			pair = "}";
		}/* else {
			System.out.println("Error, wrong item found " + thePair + " is not pairable");
		}*/
		
		//Check to see if pair is present, if not send error number (theBase.length() + 1)
		
		if(theBase.contains(pair)) {
			index = theBase.lastIndexOf(pair);
		} else {
			index = theBase.length() + 1;
		}
		return index;
	}
}
