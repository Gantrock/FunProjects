import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Trigram {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		Map<String, LinkedList> listgram = new HashMap<String, LinkedList>();
		
		File toRead = new File("alice-27.txt");
		Scanner fileRead = new Scanner(toRead);
		String line = "";
		while(fileRead.hasNextLine()) {
			line = fileRead.nextLine();
			reader(line, listgram);
		}
		fileRead.close();
		toRead = new File("london-call-27.txt");
		fileRead = new Scanner(toRead);
		line = fileRead.nextLine();
		while(fileRead.hasNextLine()) {
			line = fileRead.nextLine();
			reader(line, listgram);
		}
		fileRead.close();
		toRead = new File("melville-billy-27.txt");
		fileRead = new Scanner(toRead);
		line = "";
		while(fileRead.hasNextLine()) {
			line = fileRead.nextLine();
			reader(line, listgram);
		}
		fileRead.close();
		toRead = new File("twain-adventures-27.txt");
		fileRead = new Scanner(toRead);
		line = "";
		while(fileRead.hasNextLine()) {
			line = fileRead.nextLine();
			reader(line, listgram);
		}
		fileRead.close();
		toRead = new File("doyle-27.txt");
		fileRead = new Scanner(toRead);
		line = "";
		while(fileRead.hasNextLine()) {
			line = fileRead.nextLine();
			reader(line, listgram);
		}
		fileRead.close();
		toRead = new File("doyle-case-27.txt");
		fileRead = new Scanner(toRead);
		line = "";
		while(fileRead.hasNextLine()) {
			line = fileRead.nextLine();
			reader(line, listgram);
		}
		
		writer(listgram);
		fileRead.close();
		//lineRead.close();
	}
	
	public static void reader(String line, Map<String, LinkedList> listgram) {
		String x = "";
		String x1 = "";
		String x2 = "";
		Scanner lineRead = new Scanner(line);
		while(lineRead.hasNext()) {
			if(!x2.equals("")) {
				x2 = x1;
			} else if(lineRead.hasNext())
				x2 = lineRead.next().toLowerCase();
			if(!x1.equals("")) {
				x1 = x;
			}else if(lineRead.hasNext() && line.length() > 1) { 
				x1 = lineRead.next().toLowerCase();
			}
			if(lineRead.hasNext() && line.length() > 2)
				x = lineRead.next().toLowerCase();
			if(listgram.containsKey(x2)) {
				midLoop(listgram.get(x2), x1, x);
				} else {
					listgram.put(x2, new LinkedList());
					listgram.get(x2).add(x1);
					listgram.get(x2).add(null);
					midLoop(listgram.get(x2), x1, x);
				}
		}
		lineRead.close();
	}
	public static void bottomLoop(LinkedList theBot, String theItem) {
		//int test = 0;
		LinkedList boTemp = theBot;
		while(boTemp != null) {
			if(boTemp.get(0) != null) {
				if(((String)boTemp.getFirst()).equals(theItem)) {
					//System.out.println(boTemp.get(1));
					if(boTemp.size() == 2) {
						boTemp.add(1);
					}
					//test = (int) boTemp.get(2);
					//boTemp.set(2, test);
					boTemp.set(2, (((int) boTemp.get(2)) + 1));
					boTemp = (LinkedList) boTemp.get(1);
				} else if(boTemp.get(1) == null) {
					boTemp.set(1, new LinkedList());
					((LinkedList) boTemp.get(1)).add(theItem);
					((LinkedList) boTemp.get(1)).add(null);
					((LinkedList) boTemp.get(1)).add(1);
				} else {
					boTemp = (LinkedList) boTemp.get(1);
				}
			}
			//System.out.println(boTemp);
		}
	}
	
	public static void midLoop(LinkedList theMid, String theItem, String botString) {
		LinkedList temp = theMid;
		while(temp != null) {
			if(temp.get(1) == null) {
				temp.set(1,  new LinkedList());
				((LinkedList) temp.get(1)).add(botString);
				((LinkedList) temp.get(1)).add(null);
				((LinkedList) temp.get(1)).add(1);
				temp = (LinkedList) temp.get(1);
				//((LinkedList) temp.get(1)).set(0, theItem);
				bottomLoop(temp, botString);
			} 
			if(temp.get(0) != null) {
				if(((String) temp.getFirst()).equals(theItem)) {
					bottomLoop(temp, botString);
					temp = null;
					//temp = (LinkedList) temp.get(1);
				} else{
					temp = (LinkedList) temp.get(1);
				}
				//System.out.println(temp);
			}
		}
	}
	
	public static void writer(Map<String, LinkedList> theMap) throws FileNotFoundException, UnsupportedEncodingException {
		int wordCount = 1000;
		int written = 0;
		int searchDepth = 0;
		int total = 0;
		int i = 0;
		int j = 0;
		LinkedList start = new LinkedList();
		LinkedList deeper = new LinkedList();
		String first = "";
		String second = "";
		String third = "";
		
		PrintWriter author = new PrintWriter("resultStory.txt", "UTF-8");
		Random prob = new Random();
		while(written <= wordCount) {
			i = 0;
			total = 0;
			searchDepth = prob.nextInt(theMap.size());
			for(String key : theMap.keySet()) {
				if(searchDepth == i) {
					first = key;
				}
				i++;
			}
			author.write(" " + first + " ");
			written++;
			start = theMap.get(first);
			searchDepth = prob.nextInt(theMap.size());
			while(j <= searchDepth) {
				if(start.get(1) != null) {
					second = (String) start.get(0);
					start = (LinkedList) start.get(1);
				} else {
					second = (String) start.get(0);
				}
				j++;
				if(start.get(1) == null) {
					start = theMap.get(first);
				}
			}
			author.write(" " + second + " ");
			written++;

			deeper = (LinkedList) start.get(1);
			start = (LinkedList) start.get(1);
			j = 0;
			if(deeper.get(1) == null) {
				third = (String) deeper.get(0);
			} else {
				while(deeper.get(1) != null) {
					total += (int) deeper.get(2);
					deeper = (LinkedList) deeper.get(1);
				}
				searchDepth = prob.nextInt(total);
				while(searchDepth < j) {
					searchDepth -= (int) start.get(2);
					j++;
				}
				third = (String) start.get(0);
			}
			author.write(" " + third + " ");
			written++;
		}
		author.close();
	}
}
