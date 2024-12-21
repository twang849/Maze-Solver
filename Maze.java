import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

// Class representing a maze
public class Maze {
	private Graph graph;
	private char map[][];
	private int startId;
	private int endId;
	private int coinCount;
	private Stack<GraphNode> stack;

	// Constructor for maze, using input file 'inputFile'
	public Maze(String inputFile) throws MazeException {
		coinCount = 0;
		
		try {
			this.readInput(new BufferedReader(new FileReader(inputFile)));
		} catch (IOException | GraphException e) {
			e.printStackTrace();
		}
		
		stack = new Stack<GraphNode>();
	}

	// Getter method for the graph
	public Graph getGraph() {
		return graph;
	}

	// Returns an iterator for nodes of the solution to the maze
	public Iterator<GraphNode> solve() throws GraphException {
		if (!this.DFS(coinCount, new GraphNode(startId))) return null;
		else return stack.iterator();
	}

	// Performs a depth first search traversal to find a path to the exit
	private boolean DFS(int k, GraphNode go) throws GraphException {
		stack.push(go);
		if (go.getName() == endId) return true;
		graph.getNode(go.getName()).mark(true);
		Iterator<GraphEdge> iterator = graph.incidentEdges(go);
		while (iterator.hasNext()) {
			GraphEdge curr = iterator.next();
			if (!graph.getNode(curr.secondEndpoint().getName()).isMarked()) {
				if (k - curr.getType() >= 0)
					if (DFS(k - curr.getType(), curr.secondEndpoint())) return true;
			}
		}
		graph.getNode(stack.pop().getName()).mark(false);
		return false;
	}

	// Helper method that reads the input file to build the maze with a Graph object
	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		int S = Integer.parseInt(inputReader.readLine());
		int A = Integer.parseInt(inputReader.readLine());
		int L = Integer.parseInt(inputReader.readLine());
		coinCount = Integer.parseInt(inputReader.readLine());
		
		map = new char[2 * L - 1][2 * A - 1];
		graph = new Graph(A * L);
		
		// Create a 2D array with the characters of the input file maze
		for (int i = 0; i < map.length; i++) {
			char line[] = inputReader.readLine().toCharArray();
			
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = line[j];
			}
		}
		
		// Process the information in the 2D array map
		int nodeCount = 0;
		for (int i = 0; i < map.length; i += 2) {
			for (int j = 0; j < map[0].length; j += 2) {
				if (map[i][j] == 's') startId = nodeCount;
				if (map[i][j] == 'x') endId = nodeCount;
				
				// Adds edge with the node currently at, and the node directly to the right 
				if (j + 2 < map[0].length) {
					if (Character.isDigit(map[i][j + 1])) 
						this.insertEdge(nodeCount, nodeCount + 1, Integer.parseInt(String.valueOf(map[i][j + 1])), "door");
					else if (map[i][j + 1] == 'w');
					else if (map[i][j + 1] == 'c') 
						this.insertEdge(nodeCount, nodeCount + 1, 0, "corridor");
					else System.out.println("Error.");
				}
				
				// Adds edge with the node currently at, and the node directly below
				if (i + 2 < map.length) {
					if (Character.isDigit(map[i + 1][j])) 
						this.insertEdge(nodeCount, nodeCount + A, Integer.parseInt(String.valueOf(map[i + 1][j])), "door");
				else if (map[i + 1][j] == 'w');
				else if (map[i + 1][j] == 'c') 
					this.insertEdge(nodeCount, nodeCount + A, 0, "corridor");
				else System.out.println("Error.");
				}
				nodeCount++;
			}
		}
	}

	// Helper method to insert an edge into the graph
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {
		graph.insertEdge(new GraphNode(node1), new GraphNode(node2), linkType, label);
	}
}
