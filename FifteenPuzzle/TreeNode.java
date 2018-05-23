public class TreeNode implements Comparable<TreeNode> {
	int depth;
	int value;
	Board theBoard;
	/*TreeNode rightNode;
	TreeNode downNode;
	TreeNode leftNode;
	TreeNode upNode;*/
	
	/**
	 * A two parameter constructor, meant for a root node.
	 * @param newBoard
	 * @param theDepth
	 */
	public TreeNode(Board newBoard, int theDepth) {
		depth = theDepth + 1;
		theBoard = new Board(newBoard.toString());
		value = theBoard.winCond();
	}
	
	/**
	 * A four parameter constructor, meant for any number of nodes that aren't the root.
	 * @param newBoard
	 * @param theDepth
	 * @param choice
	 * @param manHat
	 */
	public TreeNode(Board newBoard, int theDepth, String choice, boolean manHat) {
		depth = theDepth + 1;
		theBoard = new Board(newBoard.toString());
		theBoard.swap(choice);
		if(manHat) {
			value = theBoard.huerCond();
		} else {
			value = theBoard.winCond();
		}
	}

	@Override
	public int compareTo(TreeNode theOther) {
		return (value - theOther.value);
	}
}
