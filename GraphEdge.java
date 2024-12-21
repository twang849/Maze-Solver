// Class that represents an edge of a graph
public class GraphEdge {
	private GraphNode origin;
	private GraphNode destination;
	int type;
	String label;
	
	// Constructs an edge between points u and v, with given type and label
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		origin = u;
		destination = v;
		this.type = type;
		this.label = label;
	}
	
	// Returns the first endpoint of this edge
	public GraphNode firstEndpoint() {
		return origin;
	}
	
	// Returns the second endpoint of this edge
	public GraphNode secondEndpoint() {
		return destination;
	}
	
	// Setter method for edge type
	public void setType(int newType) {
		type = newType;
	}
	
	// Getter method for edge type
	public int getType() {
		return type;
	}
 	
	// Getter method for edge label
	public String getLabel() {
		return label;
	}
	
	// Setter method for edge label
	public void setLabel(String newLabel) {
		label = newLabel;
	}
	
}
