import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class Dijkstra {

	public static Node calculateShortestPathFromSourcesToDestination(Graph graph, List<Node> sources, Node destination) {
		Node result = null;
		for (int i = 0; i<sources.size(); i++) {
			Node node = calculateShortestPathFromSourceToDestination(graph,sources.get(i), destination);
			if (result != null && node != null && node.getDistance()<result.getDistance()) {
				result = node;
			} else if (node !=null && result == null) {
				result = node;
			}
		}
		return result;
	}
	
	private static Node calculateShortestPathFromSourceToDestination(Graph graph, Node source, Node destination) {
		Node result = null;
		calculateShortestPathFromSource(graph,source);
		for (Node node : graph.getNodes()) {
			if (destination.getName().equals(node.getName()) && node.getShortestPath().size()>0 && source.getName().equals(node.getShortestPath().get(0).getName())) {
				result = node;
				break;
			}
		}
		return result;
	}
	
	
	public static void calculateShortestPathFromSource(Graph graph, Node source) {
	    source.setDistance(0.0);

	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();

	    unsettledNodes.add(source);

	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry < Node, Double> adjacencyPair: 
	          currentNode.getAdjacentNodes().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            Double edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    //System.out.println(graph);
	    //System.out.println();
	}
	
	private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
	    Node lowestDistanceNode = null;
	    double lowestDistance = Double.MAX_VALUE;
	    for (Node node: unsettledNodes) {
	        double nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	
	private static void calculateMinimumDistance(Node evaluationNode,
			Double edgeWeigh, Node sourceNode) {
		Double sourceDistance = sourceNode.getDistance();
			    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			        evaluationNode.setDistance(sourceDistance + edgeWeigh);
			        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			        shortestPath.add(sourceNode);
			        evaluationNode.setShortestPath(shortestPath);
			    }
			}
	
	public static void test() {
		List<Node> sources = new LinkedList<Node>();
		
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D"); 
		Node nodeE = new Node("E");
		Node nodeF = new Node("F");
		Node nodeG = new Node("G");

		nodeA.addDestination(nodeB, 10);
		nodeA.addDestination(nodeC, 15);

		nodeB.addDestination(nodeD, 12);
		nodeB.addDestination(nodeF, 15);

		nodeC.addDestination(nodeE, 10);

		nodeD.addDestination(nodeE, 2);
		nodeD.addDestination(nodeF, 1);

		nodeF.addDestination(nodeE, 5);
		
		nodeG.addDestination(nodeB, 8);
		nodeG.addDestination(nodeC, 10);

		Graph graph = new Graph();

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		System.out.println(graph);

		calculateShortestPathFromSource(graph, nodeA);
		System.out.println(graph);
		graph.reset();
		calculateShortestPathFromSource(graph, nodeG);
		graph.reset();
		
		sources.add(nodeG);	
		sources.add(nodeA);		
		Node shortest = calculateShortestPathFromSourcesToDestination(graph, sources, nodeE);
		System.out.println("From G or A to E shortest path: " + shortest.shortestPathInfo());
		graph.reset();
		
		sources.remove(nodeG);		
		shortest = calculateShortestPathFromSourcesToDestination(graph, sources, nodeE);
		System.out.println("From A to E shortest path: " + shortest.shortestPathInfo());
		graph.reset();
	}
}
