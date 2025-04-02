package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DijkstraTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testDijkstra() {
		/* Test graph */
		
		net.forestany.forestj.lib.Dijkstra<Integer> o_graphInteger = new net.forestany.forestj.lib.Dijkstra<Integer>();
			o_graphInteger.add(1, 6, 3);
			o_graphInteger.add(2, 1, 1);
			o_graphInteger.add(2, 3, 2);
			o_graphInteger.add(2, 6, 4);
			o_graphInteger.add(3, 4, 6);
			o_graphInteger.add(3, 5, 3);
			o_graphInteger.add(5, 3, 2);
			o_graphInteger.add(5, 4, 2);
			o_graphInteger.add(6, 2, 1);
			o_graphInteger.add(6, 5, 7);
			o_graphInteger.add(6, 7, 8);
			o_graphInteger.add(7, 2, 1);
			o_graphInteger.add(7, 3, 9);
			o_graphInteger.add(7, 5, 2);
			o_graphInteger.add(7, 8, 1);
			o_graphInteger.add(8, 6, 10);
			o_graphInteger.add(8, 1, 1);
			o_graphInteger.add(4, 7, 11);
		
		java.util.List<Integer> a_shortestPathsIntegerResult = java.util.Arrays.asList(1, 0, 2, 7, 5, 4, 12, 13);
		
		java.util.List<java.util.List<Integer>> a_shortestPathsToIntegerResult = java.util.Arrays.asList(
			java.util.Arrays.asList(2, 1),
			java.util.Arrays.asList(2),
			java.util.Arrays.asList(2, 3),
			java.util.Arrays.asList(2, 3, 5, 4),
			java.util.Arrays.asList(2, 3, 5),
			java.util.Arrays.asList(2, 6),
			java.util.Arrays.asList(2, 6, 7),
			java.util.Arrays.asList(2, 6, 7, 8)
		);
		
		Integer i_startNode = 2;
		o_graphInteger.executeDijkstra(i_startNode);
		
		for (int i = 0; i < o_graphInteger.getMapping().size(); i++) {
			assertTrue(
					o_graphInteger.getShortestPathSumTo((i + 1)) == a_shortestPathsIntegerResult.get(i),
					"shortest path sum[" + o_graphInteger.getShortestPathSumTo((i + 1)) + "] from '" + i_startNode + "' to '" + (i + 1) + "' is not '" + a_shortestPathsIntegerResult.get(i) + "'"
			);
		}
		
		for (int i = 0; i < o_graphInteger.getMapping().size(); i++) {
			assertEquals(
					o_graphInteger.getShortestPathTo((i + 1)),
					a_shortestPathsToIntegerResult.get(i),
					"shortest path " + o_graphInteger.getShortestPathTo((i + 1)) + " from '" + i_startNode + "' to '" + (i + 1) + "' is not '" + a_shortestPathsToIntegerResult.get(i) + "'"
			);
		}
		
		/* Test graph with string values */
		
		java.util.ArrayList<String> a_locations = new java.util.ArrayList<String>();
			a_locations.add("London");
			a_locations.add("Birmingham");
			a_locations.add("Oxford");
			a_locations.add("Cambridge");
			a_locations.add("Southampton");
			a_locations.add("Bristol");
			a_locations.add("Liverpool");
			a_locations.add("Manchester");
		
		net.forestany.forestj.lib.Dijkstra<String> o_graph = new net.forestany.forestj.lib.Dijkstra<String>();
			o_graph.add(a_locations.get(0), a_locations.get(5), 3);
			o_graph.add(a_locations.get(1), a_locations.get(0), 1);
			o_graph.add(a_locations.get(1), a_locations.get(2), 2);
			o_graph.add(a_locations.get(1), a_locations.get(5), 4);
			o_graph.add(a_locations.get(2), a_locations.get(3), 6);
			o_graph.add(a_locations.get(2), a_locations.get(4), 3);
			o_graph.add(a_locations.get(4), a_locations.get(2), 2);
			o_graph.add(a_locations.get(4), a_locations.get(3), 2);
			o_graph.add(a_locations.get(5), a_locations.get(1), 1);
			o_graph.add(a_locations.get(5), a_locations.get(4), 7);
			o_graph.add(a_locations.get(5), a_locations.get(6), 8);
			o_graph.add(a_locations.get(6), a_locations.get(1), 1);
			o_graph.add(a_locations.get(6), a_locations.get(2), 9);
			o_graph.add(a_locations.get(6), a_locations.get(4), 2);
			o_graph.add(a_locations.get(6), a_locations.get(7), 1);
			o_graph.add(a_locations.get(7), a_locations.get(5), 10);
			o_graph.add(a_locations.get(7), a_locations.get(0), 1);
			o_graph.add(a_locations.get(3), a_locations.get(6), 11);
		
		java.util.List<Integer> a_shortestPathsResult = java.util.Arrays.asList(13, 12, 14, 0, 13, 16, 11, 12);
		
		java.util.List<java.util.List<String>> a_shortestPathsToResult = java.util.Arrays.asList(
			java.util.Arrays.asList("Cambridge", "Liverpool", "Birmingham", "London"),
			java.util.Arrays.asList("Cambridge", "Liverpool", "Birmingham"),
			java.util.Arrays.asList("Cambridge", "Liverpool", "Birmingham", "Oxford"),
			java.util.Arrays.asList("Cambridge"),
			java.util.Arrays.asList("Cambridge", "Liverpool", "Southampton"),
			java.util.Arrays.asList("Cambridge", "Liverpool", "Birmingham", "Bristol"),
			java.util.Arrays.asList("Cambridge", "Liverpool"),
			java.util.Arrays.asList("Cambridge", "Liverpool", "Manchester")
		);
		
		String s_startNode = a_locations.get(3); /* Cambridge */
		o_graph.executeDijkstra(s_startNode);
		
		for (int i = 0; i < a_locations.size(); i++) {
			assertTrue(
					o_graph.getShortestPathSumTo(a_locations.get(i)) == a_shortestPathsResult.get(i),
					"shortest path sum[" + o_graph.getShortestPathSumTo(a_locations.get(i)) + "] from '" + s_startNode + "' to '" + a_locations.get(i) + "' is not '" + a_shortestPathsResult.get(i) + "'"
			);
		}
		
		for (int i = 0; i < a_locations.size(); i++) {
			assertEquals(
					o_graph.getShortestPathTo(a_locations.get(i)),
					a_shortestPathsToResult.get(i),
					"shortest path " + o_graph.getShortestPathTo(a_locations.get(i)) + " from '" + s_startNode + "' to '" + a_locations.get(i) + "' is not '" + a_shortestPathsToResult.get(i) + "'"
			);
		}
	}
}
