import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityGraphMap {
	private Graph graph;
	private Map<Misto, Node> mistaNodes;	
	private Map<Node,Misto> nodesMista;
	
	public EntityGraphMap(List<Sklad> sklady,List<Oaza> oazy, List<Cesta> cesty) {
		this.graph = new Graph();
		this.mistaNodes = new HashMap<Misto, Node>();
		this.nodesMista = new HashMap<Node,Misto>();
		for (Sklad sklad : sklady) {
			Node node = new Node("Sklad_" + sklad.INDEX);
			mistaNodes.put(sklad, node);
			nodesMista.put(node, sklad);
			graph.addNode(node);
		}
		for (Oaza oaza : oazy) {
			Node node = new Node("Oaza_" + oaza.INDEX);
			mistaNodes.put(oaza, node);
			nodesMista.put(node, oaza);
			graph.addNode(node);
		}
		for (Cesta cesta : cesty) {
			Node from = mistaNodes.get(cesta.misto1);
			Node to = mistaNodes.get(cesta.misto2);
			from.addDestination(to, cesta.vzdalenost);
			to.addDestination(from, cesta.vzdalenost); //doplneni i opracene cesty z nacitaneho souboru
		}
		//System.out.println(graph);
		//nodeA.addDestination(nodeB, 10);			
	}
	
	public List<Node> getSkladyNodes() {
		List<Node> skladyNodes = new LinkedList<Node>();
		
		mistaNodes.entrySet().forEach(entry -> {
			if (entry.getKey() instanceof Sklad) skladyNodes.add(entry.getValue());		    
		});
		
		return skladyNodes;
	}
	
	public List<Oaza> getOazy() {
		List<Oaza> oazy = new LinkedList<Oaza>();
		
		mistaNodes.entrySet().forEach(entry -> {
			if (entry.getKey() instanceof Oaza) oazy.add((Oaza) entry.getKey());		    
		});
		
		return oazy;
	}
	

	public Graph getGraph() {
		return graph;
	}



	public void setGraph(Graph graph) {
		this.graph = graph;
	}



	public Map<Misto, Node> getMistaNodes() {
		return mistaNodes;
	}



	public void setMistaNodes(Map<Misto, Node> mistaNodes) {
		this.mistaNodes = mistaNodes;
	}



	public Map<Node, Misto> getNodesMista() {
		return nodesMista;
	}



	public void setNodesMista(Map<Node, Misto> nodesMista) {
		this.nodesMista = nodesMista;
	}

	public Node getShortedPathTest() {
		Node shortest = null;
		graph.reset();
		List<Node> sources = getSkladyNodes();
		System.out.println(this);
		Oaza destination = getOazy().get(0);
		if (destination != null && mistaNodes.get(destination) != null) {
			shortest = Dijkstra.calculateShortestPathFromSourcesToDestination(graph, sources, mistaNodes.get(destination));
			System.out.println("To Oaza " + destination.ID + " shortest path: " + shortest.shortestPathInfo());			
		}
		return shortest;
	}
	
	public Node getShortedPathToOaza(Oaza destination) {
		Node shortest = null;
		graph.reset();
		List<Node> sources = getSkladyNodes();
		if (destination != null && mistaNodes.get(destination) != null) {
			shortest = Dijkstra.calculateShortestPathFromSourcesToDestination(graph, sources, mistaNodes.get(destination));
			//System.out.println(shortest);
		}
		return shortest;
	}
	
	public Sklad getSkladToOaza(Oaza destination) {
		Sklad sklad = null;
		Node shortest = getShortedPathToOaza(destination);	
		if (shortest != null && shortest.getShortestPath().size() > 0) {
			Node skladNode = shortest.getShortestPath().get(0);
			Misto misto = nodesMista.get(skladNode);
			if (misto instanceof Sklad) {
				sklad = (Sklad) misto;
			}
		}
		return sklad;
	}


	@Override
	public String toString() {
		return "EntityGraphMap [\ngraph {\n" + graph + "}\n]";
	}	
	
}
