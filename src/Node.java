import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
    
    private String name;
    
    private List<Node> shortestPath = new LinkedList<>();
    
    private Double distance = Double.MAX_VALUE;
    
    Map<Node, Double> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }
 
    public Node(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}
	
	public void resetShortestPath() {
		this.shortestPath = new LinkedList<>();
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public void resetDistance() {
		this.distance = Double.MAX_VALUE;
	}

	public Map<Node, Double> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}
	
	public String shortestPathInfo() {
		String result = "";
		for (Node node : shortestPath) {
			if (!result.equals("")) result = result + " > ";
			result = result + node.getName();
		}
		result = result + " > " + name + " : [" + distance  + "]";
		
		return result;
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", shortestPath=" + shortestPath + ", distance=" + distance + "]";
	}

    
    
}