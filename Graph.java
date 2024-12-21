import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Class that represents an undirected graph
public class Graph implements GraphADT {
	
	// Class representing a singly-linked-list node storing GraphEdges
	public class LinkedList {
		private LinkedList next;
		private GraphEdge element;
		
		// Constructor
		public LinkedList(GraphEdge element){
			this.element = element;
			this.next = null;
		}
		
		// Constructor if next node is known
		public LinkedList(GraphEdge element, LinkedList next){
			this.element = element;
			this.next = next;
		}
		
		// Return the GraphEdge stored by this node
		public GraphEdge getElement() {
			return element;
		}
		
		// Return the next node from this node
		public LinkedList getNext() {
			return next;
		}
		
		// Setter method for this node's GraphEdge
		public void setElement(GraphEdge element) {
			this.element = element;
		}
		
		// Setter method for this node's next node
		public void setNext(LinkedList next) {
			this.next = next;
		}
		
		// Returns whether this node has a next node
		public boolean hasNext () {
			if (next == null) return false;
			else return true;
		}
		
		// Adds a node storing this GraphEdge to the end of this entire list
		public void add(GraphEdge element) {
			this.getLast().setNext(new LinkedList(element));
		}
		
		// Returns whether this GraphEdge is already in the linked list for node with name 'name'
		public boolean edgeExists(int name, GraphEdge edge) {
			if (edgeList[name] == null) return false;
			else {
				LinkedList curr = edgeList[name];
				while (curr != null) {
					if (curr.isEqual(edge)) return true;
					curr = curr.getNext();
				}
			}
			
			return false;
		}
		
		// Returns whether the GraphEdge stored in this node is the same as the GraphEdge provided
		public boolean isEqual(GraphEdge element) {
			if (this.getElement().firstEndpoint().getName() == element.firstEndpoint().getName())
				if (this.getElement().secondEndpoint().getName() == element.secondEndpoint().getName())
					return true;
					
			if (this.element.firstEndpoint().getName() == element.secondEndpoint().getName())
				if (this.element.secondEndpoint().getName() == element.firstEndpoint().getName())
					return true; 
			
			return false;
		}
		
		// Return the last node in this list
		private LinkedList getLast() {
			LinkedList curr = next;
			if (curr == null) return this;
			
			LinkedList prev = curr;
			while (curr != null) {
				prev = curr;
				curr = curr.getNext();
			}
			
			return prev;
		}
		
		// Return how many elements are in the list starting from this node
		public int size() {
			int count = 0;
			LinkedList curr = this;
			
			while (curr != null) {
				count++;
				curr = curr.next;
			}
			
			return count;
		}
	}

	private LinkedList[] edgeList; // Adjacency list
	private GraphNode[] nodeList; // List of nodes corresponding to the adjacency list
	
	// Constructors empty graph with n nodes
	public Graph(int n) {
		edgeList = new LinkedList[n];
		nodeList = new GraphNode[n];
		for (int i = 0; i < n; i++) {
			nodeList[i] = new GraphNode(i);
		}
	}
	
	// Inserts a GraphEdge with given parameters into the graph
	public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {
		GraphEdge edge1 = new GraphEdge(u, v, edgeType, label);
		GraphEdge edge2 = new GraphEdge(v, u, edgeType, label);

		int uIndex = u.getName();
		int vIndex = v.getName();
		
		if (uIndex >= nodeList.length) throw new GraphException("Node does not exist.");
		if (vIndex >= nodeList.length) throw new GraphException("Node does not exist.");
		
		if (edgeList[uIndex] == null) edgeList[uIndex] = new LinkedList(edge1);
		else  {
			if (edgeList[uIndex].edgeExists(uIndex, edge1)) throw new GraphException("Edge already exists.");
			else edgeList[uIndex].add(edge1);
		}
		
		if (edgeList[vIndex] == null) edgeList[vIndex] = new LinkedList(edge2);
		else {
			if (edgeList[vIndex].edgeExists(vIndex, edge2))throw new GraphException("Edge already exists.");
			edgeList[vIndex].add(edge2);
		}
	}

	//	Return the node with name 'u'
	public GraphNode getNode(int u) throws GraphException {
		if (u >= nodeList.length) throw new GraphException ("Node does not exist.");
		return nodeList[u];
	}

	// Return an Iterator for all the edges incident on node 'u'
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		ArrayList<GraphEdge> list = new ArrayList<GraphEdge>();
		if (u.getName() >= nodeList.length) throw new GraphException("Node does not exist.");
		LinkedList curr = edgeList[u.getName()];
		
		while (curr != null) {
			list.add(curr.getElement());
			curr = curr.getNext();
		}
		
		if (list.size() == 0) return null;
		else return list.iterator();
	}

	// Returns the edge with endpoints 'u' and 'v' if it exists, otherwise throw a GraphException
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if (u.getName() >= nodeList.length) throw new GraphException("Node does not exist.");
		if (v.getName() >= nodeList.length) throw new GraphException("Node does not exist.");
		
		if (edgeList[u.getName()] == null) throw new GraphException("Node has no edges.");
		if (edgeList[v.getName()] == null) throw new GraphException("Node has no edges.");
		
		LinkedList choice;
		if (edgeList[u.getName()].size() > edgeList[v.getName()].size()) choice = edgeList[v.getName()];
		else choice = edgeList[u.getName()];
		
		GraphEdge target = null;
		while (choice != null) {
			if (choice.getElement().firstEndpoint().getName() == u.getName() && choice.getElement().secondEndpoint().getName() == v.getName())
				target = choice.getElement();
			else if (choice.getElement().firstEndpoint().getName() == v.getName() && choice.getElement().secondEndpoint().getName() == u.getName())
				target = choice.getElement();
			
			choice = choice.getNext();
		}
		
		return target;
	}

	// Returns whether or not these two nodes are adjacent
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		try {
			boolean answer = this.getEdge(u, v) != null;
			return answer;
		} catch (GraphException e) {
			if (e.toString().endsWith("Node does not exist.")) throw new GraphException("Node does not exist.");
			else return false;
		}
	}
}
