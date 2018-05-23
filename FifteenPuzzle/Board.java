import java.util.HashMap;
import java.util.Map;

public class Board {
	String map;
	String winner;
	String winner2;
	Map<Character, Coords> direct;
	Map<Character, Coords> direct2;
	char win[][];
	char win2[][];
	char oneLine[][];
	int x = 2;
	int y = 1;
	
	public Board(String origin) {
		map = origin;
		oneLine = new char[4][4];
		win = new char[4][4];
		win2 = new char[4][4];
		direct = new HashMap<Character, Coords>();
		direct2 = new HashMap<Character, Coords>();
		winner = "123456789ABCDEF ";
		winner2 = "123456789ABCDFE ";
		//x = map.indexOf(' ');
		for(int i = 0; i < oneLine.length; i++) {
			for(int j = 0; j < oneLine[0].length; j++) {
				if(origin.length() > 0) {
					if(origin.charAt(0) == ' ') {
						x = i;
						y = j;
					}
					win[i][j] = winner.charAt(0);
					direct.put(winner.charAt(0), new Coords(i, j));
					winner = winner.substring(1);
					win2[i][j] = winner2.charAt(0);
					direct2.put(winner2.charAt(0), new Coords(i, j));
					winner2 = winner2.substring(1);
					oneLine[i][j] = origin.charAt(0);
					origin = origin.substring(1);
				}
			}
		}
	}
	
	/**
	 * Uses inversions to determine if a board is solvable, will return a negative response
	 * if it isn't
	 * @return
	 */
	public boolean solvabilitty() {
		boolean isSolveable = false;
		int inversion = countInversions();
		if(x % 2 == 0 && inversion % 2 == 1) {
			isSolveable = true;
		} else if (x % 2 == 1 && inversion % 2 == 0) {
			isSolveable = true;
		}
		System.out.println(isSolveable + " with " + inversion + " inversions");
		return isSolveable;
	}
	/**
	 * Counts the number of inversions
	 * @return
	 */
	public int countInversions() {
		String line = toString();
		int inversion = 0;
		for(int i = 0; i < line.length(); i++) {
			for(int j = i + 1; j < line.length(); j++) {
				if(charToInt(line.charAt(i)) > charToInt(line.charAt(j)) && (charToInt(line.charAt(j)) != 0)) {
					inversion++;
				}
			}
		}
		return inversion;
	}
	/**
	 * Checks to see if a potential swap is out of bounds
	 * @param dir
	 * @return
	 */
	public boolean testSwap(String dir) {
		boolean canDo = false;
		switch (dir) {
		case "u": if(isValid(x-1, y)){
					canDo = true;
					}
					break;
		case "r": if(isValid(x, y+1)){
					canDo = true;
			}
					break;
		case "l": if(isValid(x, y-1)){
			canDo = true;
			}
					break;
		case "d": if(isValid(x+1, y)){
					canDo = true;
			}
					break;
		default:
					System.out.println("Invalid command " + dir);
					break;
		}
		return canDo;
	}
	
	/**
	 * Swaps two characters (the ' ') and another character
	 * @param dir
	 * @return
	 */
	public boolean swap(String dir) {
		//StringBuilder builder = new StringBuilder(map);
		int tempX = x;
		int tempY = y;
		char temp;
		boolean moved = false;
		switch (dir) {
		case "u": 	if(isValid(x-1, y)){
					tempX--;
					temp = oneLine[x][y];
					oneLine[x][y] = oneLine[tempX][tempY];
					oneLine[tempX][tempY] = temp;
					x = tempX;
					moved = true;};
					break;
		case "r": 	if(isValid(x, y+1)){	
					tempY++;
					temp = oneLine[x][y];
					oneLine[x][y] = oneLine[tempX][tempY];
					oneLine[tempX][tempY] = temp;
					y = tempY;
					moved = true;};
					break;
		case "l": 	if(isValid(x, y-1)){	
			tempY--;
					temp = oneLine[x][y];
					oneLine[x][y] = oneLine[tempX][tempY];
					oneLine[tempX][tempY] = temp;
					y = tempY;
					moved = true;};
					break;
		case "d": 	if(isValid(x+1, y)){	
					tempX++;
						temp = oneLine[x][y];
						oneLine[x][y] = oneLine[tempX][tempY];
						oneLine[tempX][tempY] = temp;
						x = tempX;
						moved = true;};
						break;
		case "q":	break;
		default: 	System.out.println("Invalid");
		}
		map = toString();
		return moved;
	}
	
	/**
	 * Checks to see if an (x,y) value can exist in the current array
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isValid(int x, int y) {
		boolean ret = false;
		if((x >= 0 && x < oneLine.length) && (y >= 0 && y < oneLine[0].length)) {
			ret = true;
		}
		
		return ret;
	}
	
	/**
	 * A hueristic determined by comparing every character in the board array with a 
	 * previously created set of win-condition arrays
	 * @param winCond
	 * @return
	 */
	public int isWin(char[][] winCond) {
		int finish = 0;
		for(int i = 0; i < oneLine.length; i++) {
			for(int j = 0; j < oneLine[0].length; j++) {
				if(oneLine[i][j] != winCond[i][j])
					finish++;
			}
		}
		return finish;
	}
	
	/**
	 * Judges whether the first or second acceptable array ...DEF or ...DFE is the closer
	 * solution, picks one and returns the hueristic
	 * @return
	 */
	public int winCond() {
		int value = 0;
		if(isWin(win) < isWin(win2)) {
			value = isWin(win);
		} else {
			value = isWin(win2);
		}
		return value;
	}
	
	/**
	 * Judges whether the first or second acceptable array ...DEF or ...DFE is the closer
	 * solution, picks one and returns the hueristic
	 * @return
	 */
	public int huerCond(){
		int value = 0;
		if(isHuerWin(direct) < isHuerWin(direct2)) {
			value = isHuerWin(direct);
		}else {
			value = isHuerWin(direct2); 
		}
		return value;
	}
	
	/**
	 * A hueristic determined by judging how far each character is from it's goal state
	 * using manhatten distance
	 * @return
	 */
	public int isHuerWin(Map<Character, Coords> theDir) {
		char seeker = ' ';
		int finish = 0;
		int gX = 0;
		int gY = 0;
		for(int i = 0; i < oneLine.length; i++) {
			for(int j = 0; j < oneLine[0].length; j++) {
				seeker = oneLine[i][j];
				gX = theDir.get(seeker).getX();
				gY = theDir.get(seeker).getY();
				finish += Math.abs(i - gX) + Math.abs(j - gY);
			}
		}
		return finish;
	}
	
	/**
	 * Converts a char in the array into an integer (including the chars 'A' 'B' 'C' 'D'
	 * 'E' and 'F' for the purpose of determining inversions
	 * @param theC
	 * @return
	 */
	public int charToInt(char theC) {
		int val = 0;
		switch(theC) {
		case '1': 	val = 1;
					break;
		case '2': 	val = 2;
					break;
		case '3': 	val = 3;
					break;
		case '4': 	val = 4;
					break;
		case '5': 	val = 5;
					break;
		case '6': 	val = 6;
					break;
		case '7': 	val = 7;
					break;
		case '8': 	val = 8;
					break;
		case '9': 	val = 9;
					break;
		case 'A': 	val = 10;
					break;
		case 'B': 	val = 11;
					break;
		case 'C': 	val = 12;
					break;
		case 'D': 	val = 13;
					break;
		case 'E': 	val = 14;
					break;
		case 'F': 	val = 15;
					break;
		default:	val = 0;
		}
		return val;
	}
	
	/**
	 * Takes a character from the array and sends it to the charToInt method
	 * @param x
	 * @return
	 */
	public int toInt(int x) {
		int val = charToInt(map.charAt(x));		
		return val;
	}
	
	/**
	 * Returns the current blank-tiles position
	 * @return
	 */
	public String reportLoc() {
		return "x: " + x + " y: " + y;
	}
	
	/**
	 * Prints the board style state for the win condition
	 * @return
	 */
	public String printWin() {
		String toReturn = "";
		for(int j = 0; j < win[0].length ; j++) {
			for(int i = 0; i < win.length ; i++) {
				toReturn += win[j][i];
			}
				toReturn += "\n";
		}
		return toReturn;
	}
	/**
	 * Prints a String representation of the current board in a board like fashion
	 * @return
	 */
	public String printBoard() {
		String toReturn = "";
		for(int i = 0; i < oneLine.length ; i++) {
			for(int j = 0; j < oneLine[0].length ; j++) {
				toReturn += oneLine[i][j];
			}
				toReturn += "\n";
		}
		return toReturn;
	}
	
	public String toString() {
		String toReturn = "";
		for(int i = 0; i < oneLine.length ; i++) {
			for(int j = 0; j < oneLine[0].length ; j++) {
				toReturn += oneLine[i][j];
			}
				//toReturn += "\n";
		}
		return toReturn;
	}
	
	/**
	 * Private class to handle coordinates in order to find the correct goal position for a node
	 * @author Gantrock
	 *
	 */
	private class Coords{
		int x;
		int y;
		
		public Coords(int theX, int theY) {
			x = theX;
			y = theY;
		}
		
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
	}
}
