import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>();
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

    public void reset() {
    	for (Node node : nodes) {
    		node.resetShortestPath();
    		node.resetDistance();
    	}    	
    }

	@Override
	public String toString() {
		String result = "";
		String nodesString = "";
		String pathString = "";
		String pathStringAll = "";
		for (Node node : nodes) {
			if (!nodesString.equals("")) nodesString = nodesString + ", "; 
			nodesString = nodesString + node.getName();
			Set<Node> adjacentNodes = node.getAdjacentNodes().keySet();
			pathString = "";
			for (Node adjacentNode : adjacentNodes) {
				if (!pathString.equals("")) pathString = pathString + ", ";
				pathString = pathString + adjacentNode.getName() + "[" + node.getAdjacentNodes().get(adjacentNode) + "]";
			}
			pathStringAll = pathStringAll + "from: " + node.getName() + " to: " + pathString + "\n"; 
		}
		return "Nodes [" + nodesString + "]\nPath: \n" + pathStringAll;
	}
    
}