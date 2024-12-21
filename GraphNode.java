// Class that represents a node of a graph
public class GraphNode {
	private int name;
	private boolean mark;
	
	// Constructor
	public GraphNode(int name) {
		this.name = name;
		mark = false;
	}
	
	// Setter method for mark
	public void mark(boolean mark) {
		this.mark = mark;
	}
	
	// Returns if this node is marked
	public boolean isMarked() {
		return mark;
	}
	
	// Getter method for the name
	public int getName() {
		return name;
	}
}
