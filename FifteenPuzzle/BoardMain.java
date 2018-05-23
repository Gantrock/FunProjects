import java.util.Scanner;

public class BoardMain {
	public static void main(String[] args) {
		//String isKey = " ";
		int space = 0;
		String defaultStr= "12345 689A7CDEBF";
		String testString = "123456789AB DEFC";
		String line = "";
		String initialState = "";
		String method = "";
		String option = "";
		Scanner key = new Scanner(System.in);
		initialState = args[0];
		method = args[1];
		if(args.length > 2) {
			option = args[2];
		}
		//manual testing
		/*
		line = key.nextLine();
		//space = line.indexOf("\"");
		line = line.substring(space+1);
		space = line.indexOf("\"");
		initialState = line.substring(0, space);
		line = line.substring(space+2);
		space = line.indexOf(" ");
		if(space > -1) {
		method = line.substring(0, space);
		line = line.substring(space+1);
		option = line;
		} else {
		method = line;
		option = " ";
		}*/
		Board testBoard = new Board(initialState);
		BoardTree theForest = new BoardTree(testBoard);
		//System.out.println(initialState +  " " + method + " " + option);
		if(testBoard.solvabilitty())
			System.out.println(theForest.search(method, option));
		System.out.println(theForest.toString());
		//gameplay testing
		/*do{
			System.out.print("How would you like to move? ");
			isKey = key.next();
			testBoard.swap(isKey.toLowerCase());
			System.out.println(testBoard.isWin());
			if(testBoard.isWin() == 0) {
				System.out.println("Winner!");
				isKey = "q";
			}
			System.out.println(testBoard);
		}while(!isKey.toLowerCase().equals("q"));*/
		key.close();
	}

}
