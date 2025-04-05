package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SortsTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testSorts() {
		executeSortsList(true, 5000, true, true, true, false, false, false);
		executeSortsList(false, 5000, true, true, true, false, false, false);
		executeSortsList(true, 5000, false, false, false, true, true, true);
        executeSortsList(false, 5000, false, false, false, true, true, true);
        
		executeSortsMap(true, 5000, true, true, true, false, false, false, true);
		executeSortsMap(true, 5000, true, true, true, false, false, false, false);
		executeSortsMap(false, 5000, true, true, true, false, false, false, true);
		executeSortsMap(false, 5000, true, true, true, false, false, false, false);
		
		executeSortsMap(true, 5000, false, false, false, true, true, true, true);
		executeSortsMap(true, 5000, false, false, false, true, true, true, false);
		executeSortsMap(false, 5000, false, false, false, true, true, true, true);
		executeSortsMap(false, 5000, false, false, false, true, true, true, false);
	}
	
	private static void executeSortsList(boolean p_b_unique, int p_i_amount, boolean p_b_selection, boolean p_b_insertion, boolean p_b_bubble, boolean p_b_heap, boolean p_b_merge, boolean p_b_quick) {
		java.util.ArrayList<Integer> a_list = new java.util.ArrayList<Integer>();
		
		if (p_b_unique) {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(i);
			}
		} else {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(net.forestany.forestj.lib.Helper.randomIntegerRange(1, p_i_amount));
			}
		}
		
		/* ###################################################################################################### */
		
		if (p_b_selection) {
			java.util.Collections.shuffle(a_list);
			net.forestany.forestj.lib.Sorts.selectionSort(a_list);
			executeSortsListValidate(p_b_unique, a_list);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_insertion) {
			java.util.Collections.shuffle(a_list);
			net.forestany.forestj.lib.Sorts.insertionSort(a_list);
			executeSortsListValidate(p_b_unique, a_list);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_bubble) {
			java.util.Collections.shuffle(a_list);
			net.forestany.forestj.lib.Sorts.bubbleSort(a_list);
			executeSortsListValidate(p_b_unique, a_list);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_heap) {
			java.util.Collections.shuffle(a_list);
			net.forestany.forestj.lib.Sorts.heapSort(a_list);
			executeSortsListValidate(p_b_unique, a_list);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_merge) {
			java.util.Collections.shuffle(a_list);
			net.forestany.forestj.lib.Sorts.mergeSort(a_list);
			executeSortsListValidate(p_b_unique, a_list);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_quick) {
			java.util.Collections.shuffle(a_list);
			net.forestany.forestj.lib.Sorts.quickSort(a_list);
			executeSortsListValidate(p_b_unique, a_list);
		}
	}
	
	private static void executeSortsListValidate(boolean p_b_unique, java.util.List<Integer> p_a_list) {
		if (p_b_unique) {
			int i = 1;
			
			for (int i_foo : p_a_list) {
				assertTrue( i_foo == i++, "ExecuteSortsListValidate error: " + i_foo + " != " + (i + 1) );
			}
		} else {
			int i = -1;
			
			for (int i_foo : p_a_list) {
				if (i > 0) {
					assertTrue( i <= i_foo, "ExecuteSortsListValidate error: " + i + " > " + i_foo );
				}
				
				i = i_foo;
			}
		}
	}
	
	private static void executeSortsMap(boolean p_b_unique, int p_i_amount, boolean p_b_selection, boolean p_b_insertion, boolean p_b_bubble, boolean p_b_heap, boolean p_b_merge, boolean p_b_quick, boolean p_b_sortByValue) {
		java.util.ArrayList<Integer> a_list = new java.util.ArrayList<Integer>();
		java.util.Map<String, Integer> a_map = new java.util.HashMap<String, Integer>();
		java.util.List<java.util.Map.Entry<String, Integer>> a_return = null;
		
		if (p_b_unique) {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(i);
			}
		} else {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(net.forestany.forestj.lib.Helper.randomIntegerRange(1, p_i_amount));
			}
		}
		
		/* ###################################################################################################### */
		
		if (p_b_selection) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			a_return = net.forestany.forestj.lib.Sorts.selectionSort(a_map, p_b_sortByValue);
			executeSortsMapValidate(p_b_unique, p_b_sortByValue, a_return);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_insertion) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			a_return = net.forestany.forestj.lib.Sorts.insertionSort(a_map, p_b_sortByValue);
			executeSortsMapValidate(p_b_unique, p_b_sortByValue, a_return);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_bubble) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			a_return = net.forestany.forestj.lib.Sorts.bubbleSort(a_map, p_b_sortByValue);
			executeSortsMapValidate(p_b_unique, p_b_sortByValue, a_return);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_heap) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			a_return = net.forestany.forestj.lib.Sorts.heapSort(a_map, p_b_sortByValue);
			executeSortsMapValidate(p_b_unique, p_b_sortByValue, a_return);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_merge) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			a_return = net.forestany.forestj.lib.Sorts.mergeSort(a_map, p_b_sortByValue);
			executeSortsMapValidate(p_b_unique, p_b_sortByValue, a_return);
		}
		
		/* ###################################################################################################### */
		
		if (p_b_quick) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			a_return = net.forestany.forestj.lib.Sorts.quickSort(a_map, p_b_sortByValue);
			executeSortsMapValidate(p_b_unique, p_b_sortByValue, a_return);
		}
	}
	
	private static void executeSortsMapValidate(boolean p_b_unique, boolean p_b_sortByValue, java.util.List<java.util.Map.Entry<String, Integer>> p_a_map) {
		if (p_b_unique) {
			int i = 1;
			String s_foo = null;
			
			for (java.util.Map.Entry<String, Integer> o_entry : p_a_map) {
				if (p_b_sortByValue) {
					assertTrue( o_entry.getValue() == i++, "ExecuteSortsMapValidate error: " + o_entry.getValue() + " != " + (i + 1) );
				} else {
					if (s_foo != null) {
						assertTrue( s_foo.compareTo(o_entry.getKey()) <= 0, "ExecuteSortsMapValidate error: " + s_foo + " > " + o_entry.getKey() );
					}
					
					s_foo = o_entry.getKey();
				}
			}
		} else {
			int i = -1;
			String s_foo = null;
			
			for (java.util.Map.Entry<String, Integer> o_entry : p_a_map) {
				if (!p_b_sortByValue) {
					if (p_b_sortByValue) {
						assertTrue( o_entry.getValue() == i++, "ExecuteSortsMapValidate error: " + o_entry.getValue() + " != " + (i + 1) );
					} else {
						if (s_foo != null) {
							assertTrue( s_foo.compareTo(o_entry.getKey()) <= 0, "ExecuteSortsMapValidate error: " + s_foo + " > " + o_entry.getKey() );
						}
						
						s_foo = o_entry.getKey();
					}
				} else {
					if (i > 0) {
						assertTrue( i <= o_entry.getValue(), "ExecuteSortsMapValidate error: " + i + " > " + o_entry.getValue() );
					}
					
					i = o_entry.getValue();
				}
			}
		}
	}
}
