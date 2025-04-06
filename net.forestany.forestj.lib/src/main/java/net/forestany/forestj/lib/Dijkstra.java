package net.forestany.forestj.lib;

/**
 * 
 *  Class to use dijkstra algorithm on a graph.
 *  @param <T>			type of elements in list for dijkstra algorithm
 *  
 */
public class Dijkstra<T> {
	
	/* Fields */
	
	private java.util.ArrayList<Node> a_graph;
	private java.util.ArrayList<T> a_mapping;
	private java.util.Map<T, Integer> a_shortestPathsSums;
	private java.util.Map<T, java.util.ArrayList<T>> a_shortestPaths;
	
	/* Properties */
	
	/**
	 * get graph list
	 * 
	 * @return java.util.ArrayList&lt;Node&gt;
	 */
	public java.util.ArrayList<Node> getGraph() {
		return this.a_graph;
	}
	
	/**
	 * get mapping
	 * 
	 * @return java.util.ArrayList&lt;T&gt;
	 */
	public java.util.ArrayList<T> getMapping() {
		return this.a_mapping;
	}
	
	/* Methods */
	
	/**
	 * Constructor, initialize graph and shortest paths list
	 */
	public Dijkstra() {
		this.a_graph = new java.util.ArrayList<Node>();
		this.a_mapping = new java.util.ArrayList<T>();
		this.a_shortestPathsSums = null;
		this.a_shortestPaths = null;
	}
	
	/**
	 * Get weight sum of path to parameter node
	 * 
	 * @param p_o_to	destination node
	 * @return integer weight sum value
	 */
	public int getShortestPathSumTo(T p_o_to) {
		if (this.a_shortestPathsSums == null) {
			return Integer.MAX_VALUE / 2;
		} else {
			return this.a_shortestPathsSums.get(p_o_to);
		}
	}
	
	/**
	 * Get list of nodes which show you the shortest path to destination node
	 * 
	 * @param p_o_to	destination node
	 * @return generic node list as shortest path to destination node
	 */
	public java.util.ArrayList<T> getShortestPathTo(T p_o_to) {
		if (this.a_shortestPaths == null) {
			return null;
		} else {
			return this.a_shortestPaths.get(p_o_to);
		}
	}
	
	/**
	 * Add node with a path to another node as destination, give this path a weight
	 * 
	 * @param p_o_node		source node
	 * @param p_o_to		destination node
	 * @param p_i_weight	weight for path from source to destination
	 */
	public void add(T p_o_node, T p_o_to, int p_i_weight) {
		/* add node to mapping */
		if (!this.a_mapping.contains(p_o_node)) {
			this.a_mapping.add(p_o_node);
		}
		
		/* add to node value to mapping */
		if (!this.a_mapping.contains(p_o_to)) {
			this.a_mapping.add(p_o_to);
		}
		
		/* add new node and use map indexes as values */
		this.a_graph.add(new Node(this.a_mapping.indexOf(p_o_node), this.a_mapping.indexOf(p_o_to), p_i_weight));
												net.forestany.forestj.lib.Global.ilogConfig("added node with index '" + this.a_mapping.indexOf(p_o_node) + "', to node '" + this.a_mapping.indexOf(p_o_to) + "' and weight '" + p_i_weight + "'");
	}
	
	/**
	 * Execute dijkstra algorithm on graph to calculate the shortest paths from a start node to all other reachable nodes
	 * 
	 * @param p_o_startNode		define a start node to calculate shortest paths to other nodes
	 */
	public void executeDijkstra(T p_o_startNode) {
		int i_size = this.a_graph.size();
		int i_amount = this.a_mapping.size();
		int i_minDistance = -1;
		int[][] a_distances = new int[i_size][i_size];
		int[] a_shortestPaths = new int[i_size];
		int[] a_predecessors = new int[i_size];
		boolean[] a_visited = new boolean[i_size];
		
		/* set all distances to 0 */
		for (int i = 0; i < i_size; i++) {
			for (int j = 0; j < i_size; j++) {
				a_distances[i][j] = 0;
			}
		}
		
												net.forestany.forestj.lib.Global.ilogMass("set all distances to 0");
		
		for (int i = 0; i < i_size; i++) {
			/* set shortest paths to max value */
			a_shortestPaths[i] = Integer.MAX_VALUE / 2;
			/* set all predecessors to -1 */
			a_predecessors[i] = -1;
			/* no node has been visited yet */
			a_visited[i] = false;
			/* get first distances from node information */
			a_distances[this.a_graph.get(i).getNode()][this.a_graph.get(i).getTo()] = this.a_graph.get(i).getWeight();
													net.forestany.forestj.lib.Global.ilogMass("set node '" + i + "' to standard values: max weights, predecessors -1, visited false and first distances");
		}
		
		/* set path to own node to 0 */
		if (this.a_mapping.contains(p_o_startNode)) {
			a_shortestPaths[this.a_mapping.indexOf(p_o_startNode)] = 0;
													net.forestany.forestj.lib.Global.ilogMass("start node '" + p_o_startNode + "' shortest path is '0'");
		}
		
		/* dijkstra algorithm */
		for (int i = 0; i < i_amount; i++) {
			i_minDistance = -1;
			
			for (int j = 0; j < i_amount; j++) {
				if (!a_visited[j] && ((i_minDistance == -1) || (a_shortestPaths[j] < a_shortestPaths[i_minDistance]))) {
															net.forestany.forestj.lib.Global.ilogMass("set min distance '" + j + "', because '" + j + "' was not visited and min distance is '-1', or weight sum to '" + j+ "(" + a_shortestPaths[j] + ")' is lower than " + ((i_minDistance >= 0) ? a_shortestPaths[i_minDistance] : "infinity"));
					i_minDistance = j;
				}
			}

			a_visited[i_minDistance] = true;
													net.forestany.forestj.lib.Global.ilogMass(i_minDistance + " was visited just now");
			
			for (int j = 0; j < i_amount; j++) {
														net.forestany.forestj.lib.Global.ilogMass("check distance from '" + i + "' to '" + j + "'");
				if (a_distances[i_minDistance][j] != 0) {
					if (a_shortestPaths[i_minDistance] + a_distances[i_minDistance][j] < a_shortestPaths[j]) {
																net.forestany.forestj.lib.Global.ilogMass("distance from '" + i_minDistance + "' to '" + j + "': " + a_distances[i_minDistance][j] + " != 0");
																net.forestany.forestj.lib.Global.ilogMass("weight sum(" + a_shortestPaths[i_minDistance] + ") from '" + i_minDistance + "' plus distance from '" + i_minDistance + "' to '" + j + "' [" + a_distances[i_minDistance][j] + "] is lower than weight sum(" + a_shortestPaths[j] + ") from '" + j + "'");
																net.forestany.forestj.lib.Global.ilogMass("update weight sum(" + a_shortestPaths[j] + ") from '" + j + "' to (" + (int)(a_shortestPaths[i_minDistance] + a_distances[i_minDistance][j]) + ")");
																net.forestany.forestj.lib.Global.ilogMass("update predecessors(" + a_predecessors[j] + ") from '" + j + "' to (" + (int)(i_minDistance + 1) + ")");
						a_shortestPaths[j] = a_shortestPaths[i_minDistance] + a_distances[i_minDistance][j];
						a_predecessors[j] = i_minDistance + 1;
					}
				}
			}
		}
		
		this.a_shortestPathsSums = new java.util.HashMap<T, Integer>();
		this.a_shortestPaths = new java.util.HashMap<T, java.util.ArrayList<T>>();
		
		/* fill shortest path mapping with sum weights with list of shortest paths of dijkstra algorithm */
		for (int i = 0; i < i_amount; i++) {
			this.a_shortestPathsSums.put(this.a_mapping.get(i), a_shortestPaths[i]);
													net.forestany.forestj.lib.Global.ilogFinest("added weight sum of shortest path '" + a_shortestPaths[i] + "' from start node '" + this.a_mapping.indexOf(p_o_startNode) + " to destination node '" + i + "'");
		}
		
		/* generate map of shortest paths to each node */
		for (int i = 0; i < i_amount; i++) {
			int i_to = i;
			java.util.ArrayList<T> a_path = new java.util.ArrayList<T>();
			/* start with destination node */
			a_path.add(this.a_mapping.get(i_to));
													net.forestany.forestj.lib.Global.ilogFinest("added destination node '" + this.a_mapping.get(i_to) + "(" + i_to + ")' to shortest path");
			/* go shortest path back until reaching start node */
			while (a_predecessors[i_to] != -1) {
				i_to = a_predecessors[i_to] - 1;
				a_path.add(this.a_mapping.get(i_to));
														net.forestany.forestj.lib.Global.ilogFinest("added predecessors node '" + this.a_mapping.get(i_to) + "(" + i_to + ")' to shortest path");
			}
			
			/* reverse shortest path list */
			java.util.Collections.reverse(a_path);
													net.forestany.forestj.lib.Global.ilogMass("reverse shortest path for right order from start node '" + this.a_mapping.indexOf(p_o_startNode) + "' to destination node '" + i + "'");
			
			/* add shortest path list to property list with destination node as key */
			this.a_shortestPaths.put(this.a_mapping.get(i), a_path);
													net.forestany.forestj.lib.Global.ilogFinest("added shortest path " + a_path + " to class property");
		}
	}
	
	/* Internal Classes */
	
	/**
	 * 
	 * Internal class to store node information like to-node and weight-value
	 *
	 */
	public class Node {
		
		/* Fields */
		
		private int i_node;
		private int i_to;
		private int i_weight;
		
		/* Properties */
		
		/**
		 * get node
		 * 
		 * @return int
		 */
		public int getNode() {
			return this.i_node;
		}
		
		/**
		 * get to
		 * 
		 * @return int
		 */
		public int getTo() {
			return this.i_to;
		}
		
		/**
		 * get weight
		 * 
		 * @return int
		 */
		public int getWeight() {
			return this.i_weight;
		}
		
		/* Methods */
		
		/**
		 * Constructor
		 * 
		 * @param p_i_node		give node a unique integer value
		 * @param p_i_to		give node a to node
		 * @param p_i_weight	give node a weight
		 */
		public Node (int p_i_node, int p_i_to, int p_i_weight) {
			this.i_node = p_i_node;
			this.i_to = p_i_to;
			this.i_weight = p_i_weight;
		}
	}
}
