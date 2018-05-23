import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;


public class BoardTree {
	String[] direction = {"r", "d", "l", "u"};
	int numCreated;
	int numExpanded;
	int maxFringe;
	int fringe;
	TreeNode current;
	
	public BoardTree(Board theBoard) {
		numCreated = 0;
		numExpanded = 0;
		maxFringe = 0;
		fringe = 1;
		current = new TreeNode(theBoard, -1); 
	}
	
	/**
	 * Determines search type with a switch
	 * @param type
	 * @param option
	 * @return
	 */
	public String search(String type, String option) {
		String result = "";
		switch(type) {
		case "BFS":
			result = bFS();
			break;
		case "DFS":
			result = dFS();
			break;
		case "DLS":
			result = dLS(option);
			break;
		case "GBFS":
			result = gBFS(option);
			break;
		case "AStar":
			result = aStar(option);
			break;
		}
		return result;
	}

	/**
	 * Breadth-First Search, Checks each node in a generation, if the goal state is found
	 * stop checking
	 * @return
	 */
	public String bFS() {
		TreeNode tempNode;
		Deque<TreeNode> breadth = new LinkedList<TreeNode>();
		Map<String, Integer> visited = new HashMap<String, Integer>();
		breadth.add(current);
		while(!breadth.isEmpty() && (current.value != 0)) {
			numExpanded++;
			fringe--;
			current = breadth.poll();
			for(int i = 0; i < direction.length; i++) {
				if(current.theBoard.testSwap(direction[i])) {
					tempNode = new TreeNode(current.theBoard, current.depth, direction[i], false);
					fringe++;
					numCreated++;
					if(!visited.containsKey(tempNode.theBoard.toString()))
						breadth.addLast(tempNode);
				}
			}
			if(!visited.containsKey(current.theBoard.map))
				visited.put(current.theBoard.map, current.value);
			newMax(fringe);
		}
		return this.toString();
	}
	
	/**
	 * Depth first search, checks a path as long as it can go and if it finds the goal state
	 * stops checking
	 * @return
	 */
	public String dFS() {
		TreeNode tempNode;
		Deque<TreeNode> depth = new LinkedList<TreeNode>();
		Map<String, Integer> visited = new HashMap<String, Integer>();
		depth.addFirst(current);
		while(!depth.isEmpty() && current.value != 0) {
			numExpanded++;
			fringe--;
			current = depth.removeFirst();
			for(int i = direction.length-1; i >= 0; i--) {
				if(current.theBoard.testSwap(direction[i])) {
					tempNode = new TreeNode(current.theBoard, current.depth, direction[i], false);
					fringe++;
					numCreated++;
					if(!visited.containsKey(tempNode.theBoard.toString()))
						depth.addFirst(tempNode);
				}
			}
			if(!visited.containsKey(current.theBoard.toString()))
				visited.put(current.theBoard.toString(), current.value);
			newMax(fringe);
		}
		return this.toString();
	}
	
	/**
	 * Depth limited search, employs a depth-first search method but stops if it reaches
	 * the limited depth
	 * @param option
	 * @return
	 */
	public String dLS(String option) {
		TreeNode tempNode;
		int limit = Integer.parseInt(option);
		Deque<TreeNode> depth = new LinkedList<TreeNode>();
		Map<String, Integer> visited = new HashMap<String, Integer>();
		depth.addFirst(current);
		while(!depth.isEmpty() && current.value != 0 && current.depth < limit) {
			numExpanded++;
			fringe--;
			current = depth.removeFirst();
			for(int i = direction.length-1; i >= 0; i--) {
				if(current.theBoard.testSwap(direction[i])) {
					tempNode = new TreeNode(current.theBoard, current.depth, direction[i], false);
					fringe++;
					numCreated++;
					if(!visited.containsKey(tempNode.theBoard.toString()))
						depth.addFirst(tempNode);
				}
			}
			if(!visited.containsKey(current.theBoard.toString()))
				visited.put(current.theBoard.toString(), current.value);
			newMax(fringe);
		}
		if(current.value != 0) {
			System.out.println("Answer not found, please widen limit and search again");
		}
		return this.toString();
	}
	
	/**
	 * Greedy-best-first. Takes the lowest value path (closest to completion) and adds
	 * more paths from seemingly successful paths
	 * @param option
	 * @return
	 */
	public String gBFS(String option) {
		TreeNode tempNode;
		boolean isManhatten = false;
		PriorityQueue<TreeNode> theQ = new PriorityQueue<TreeNode>();
		Map<String, Integer> visited = new HashMap<String, Integer>();
		if(option.equals("h2")) {
			isManhatten = true;
		}
		theQ.add(current);
		while(!theQ.isEmpty() && current.value != 0) {
			numExpanded++;
			fringe--;
			current = theQ.poll();
			for(int i = 0; i < direction.length; i++) {
				if(current.theBoard.testSwap(direction[i])) {
					tempNode = new TreeNode(current.theBoard, current.depth, direction[i], isManhatten);
					fringe++;
					numCreated++;
					if(!visited.containsKey(tempNode.theBoard.toString()))
						theQ.add(tempNode);
				}
			}
			if(!visited.containsKey(current.theBoard.map))
				visited.put(current.theBoard.map, current.value);
			newMax(fringe);
		}
		return this.toString();
	}
	
	/**
	 * Uses aStar search to find a path. Tracks the paths by value from start to finish.
	 * @param option
	 * @return
	 */
	public String aStar(String option) {
		TreeNode tempNode;
		int pathSoFar = 0;
		int atGoal = -1;
		boolean isManhatten = false;
		PriorityQueue<TreeNode> theQ = new PriorityQueue<TreeNode>();
		Map<String, Integer> visited = new HashMap<String, Integer>();
		if(option.equals("h2")) {
			isManhatten = true;
		}
		theQ.add(current);
		while(!theQ.isEmpty() && atGoal != 0) {
			numExpanded++;
			fringe--;
			current = theQ.poll();
			atGoal = current.value;
			//aStarStep(pathSoFar, isManhatten, theQ);
			for(int i = 0; i < direction.length; i++) {
				if(current.theBoard.testSwap(direction[i])) {
					tempNode = (new TreeNode(current.theBoard, current.depth, direction[i], isManhatten));
					if(isManhatten) {
						tempNode.value = tempNode.theBoard.huerCond();
					} else {
						tempNode.value = tempNode.theBoard.winCond();
					}
					if(!visited.containsKey(tempNode.theBoard.toString())) {
						theQ.add(tempNode);
					}
					fringe++;
					numCreated++;
				}
			}
			newMax(fringe);
			System.out.println(current.theBoard.printBoard());
			if(!visited.containsKey(current.theBoard.map))
				visited.put(current.theBoard.map, current.value);
		}
		System.out.println(current.theBoard.printBoard());
		return this.toString();
	}
	
	/**
	 * Checks to see if the local fringe is greater than the Tree's maxFringe
	 * @param theMax
	 */
	public void newMax(int theMax) {
		if(theMax > maxFringe) {
			maxFringe = theMax;
		}
	}
	/**
	 * Returns a String representation of the final nodes depth, how many nodes were created
	 * how many were expanded, and what the max fringe was
	 */
	public String toString() {
		return current.depth + ", " + numCreated + ", " + numExpanded + ", " + maxFringe;
	}
}
