package net.forestany.forestj.sandbox.util;

public class SortsTest {
	public static void testSorts() throws Exception {
    	testSortsList(true, 50_000, true, true, true, false, false, false);
    	testSortsList(true, 5_000_000, false, false, false, true, true, true);
    	testSortsList(false, 50_000, true, true, true, false, false, false);
    	testSortsList(false, 5_000_000, false, false, false, true, true, true);
		
		testSortsMap(true, 50_000, true, true, true, false, false, false, true);
		testSortsMap(true, 5_000_000, false, false, false, true, true, true, true);
		testSortsMap(true, 50_000, true, true, true, false, false, false, false);
		testSortsMap(true, 5_000_000, false, false, false, true, true, true, false);
		testSortsMap(false, 50_000, true, true, true, false, false, false, true);
		testSortsMap(false, 5_000_000, false, false, false, true, true, true, true);
		testSortsMap(false, 50_000, true, true, true, false, false, false, false);
		testSortsMap(false, 5_000_000, false, false, false, true, true, true, false);
    }
    
    private static void testSortsList(boolean p_b_unique, int p_i_amount, boolean p_b_selection, boolean p_b_insertion, boolean p_b_bubble, boolean p_b_heap, boolean p_b_merge, boolean p_b_quick) throws Exception {
		java.util.ArrayList<Integer> a_list = new java.util.ArrayList<Integer>();
		
		int i_showValuesAmount = 10;
		
		net.forestany.forestj.lib.Global.log("Generating '" + String.format("%,d", p_i_amount) + "' " + ((p_b_unique) ? "unique" : "random") + " values . . . ");
		
		if (p_b_unique) {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(i);
			}
		} else {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(net.forestany.forestj.lib.Helper.randomIntegerRange(1, p_i_amount));
			}
		}
		
		net.forestany.forestj.lib.ConsoleProgressBar o_progressBar = new net.forestany.forestj.lib.ConsoleProgressBar(4, 4);
		
		net.forestany.forestj.lib.Sorts.IDelegate itf_delegate = new net.forestany.forestj.lib.Sorts.IDelegate() { 
			@Override public void PostProgress(double p_d_progress) {
				o_progressBar.report(p_d_progress);
			}
		};
		
		// ######################################################################################################
		
		if (p_b_selection) {
			java.util.Collections.shuffle(a_list);
			
			o_progressBar.init("SelectionSort . . .", "Done.");
			net.forestany.forestj.lib.Sorts.selectionSort(a_list, itf_delegate);
			o_progressBar.close();

			int i = 1;
			
			for (int i_foo : a_list) {
				System.out.print(i_foo + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_insertion) {
			java.util.Collections.shuffle(a_list);
			
			o_progressBar.init("InsertionSort . . .", "Done.");
			net.forestany.forestj.lib.Sorts.insertionSort(a_list, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (int i_foo : a_list) {
				System.out.print(i_foo + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_bubble) {
			java.util.Collections.shuffle(a_list);
			
			o_progressBar.init("BubbleSort . . .", "Done.");
			net.forestany.forestj.lib.Sorts.bubbleSort(a_list, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (int i_foo : a_list) {
				System.out.print(i_foo + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_heap) {
			java.util.Collections.shuffle(a_list);
			
			o_progressBar.init("Sorting . . .", "Done.", "HeapSort");
			net.forestany.forestj.lib.Sorts.heapSort(a_list, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (int i_foo : a_list) {
				System.out.print(i_foo + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_merge) {
			java.util.Collections.shuffle(a_list);
			
			o_progressBar.init("Sorting . . .", "Done.", "MergeSort");
			net.forestany.forestj.lib.Sorts.mergeSort(a_list, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (int i_foo : a_list) {
				System.out.print(i_foo + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_quick) {
			java.util.Collections.shuffle(a_list);
			
			o_progressBar.init("Sorting . . .", "Done.", "QuickSort");
			net.forestany.forestj.lib.Sorts.quickSort(a_list, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (int i_foo : a_list) {
				System.out.print(i_foo + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
	}
	
	private static void testSortsMap(boolean p_b_unique, int p_i_amount, boolean p_b_selection, boolean p_b_insertion, boolean p_b_bubble, boolean p_b_heap, boolean p_b_merge, boolean p_b_quick, boolean p_b_sortByValue) throws Exception {
		java.util.ArrayList<Integer> a_list = new java.util.ArrayList<Integer>();
		java.util.Map<String, Integer> a_map = new java.util.HashMap<String, Integer>();
		java.util.List<java.util.Map.Entry<String, Integer>> a_return = null;
		
		int i_showValuesAmount = 10;
		
		net.forestany.forestj.lib.Global.log("Generating '" + String.format("%,d", p_i_amount) + "' " + ((p_b_unique) ? "unique" : "random") + " values - sort by value: " + p_b_sortByValue + " . . . ");
		
		if (p_b_unique) {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(i);
			}
		} else {
			for (int i = 1; i <= p_i_amount; i++) {
				a_list.add(net.forestany.forestj.lib.Helper.randomIntegerRange(1, p_i_amount));
			}
		}
		
		net.forestany.forestj.lib.ConsoleProgressBar o_progressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		
		net.forestany.forestj.lib.Sorts.IDelegate itf_delegate = new net.forestany.forestj.lib.Sorts.IDelegate() { 
			@Override public void PostProgress(double p_d_progress) {
				o_progressBar.report(p_d_progress);
			}
		};
		
		// ######################################################################################################
		
		if (p_b_selection) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			o_progressBar.init("SelectionSort . . .", "Done.");
			a_return = net.forestany.forestj.lib.Sorts.selectionSort(a_map, p_b_sortByValue, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (java.util.Map.Entry<String, Integer> o_entry : a_return) {
				System.out.print(o_entry.getKey() + ":" + o_entry.getValue() + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_insertion) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			o_progressBar.init("InsertionSort . . .", "Done.");
			a_return = net.forestany.forestj.lib.Sorts.insertionSort(a_map, p_b_sortByValue, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (java.util.Map.Entry<String, Integer> o_entry : a_return) {
				System.out.print(o_entry.getKey() + ":" + o_entry.getValue() + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_bubble) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			o_progressBar.init("BubbleSort . . .", "Done.");
			a_return = net.forestany.forestj.lib.Sorts.bubbleSort(a_map, p_b_sortByValue, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (java.util.Map.Entry<String, Integer> o_entry : a_return) {
				System.out.print(o_entry.getKey() + ":" + o_entry.getValue() + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_heap) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			o_progressBar.init("HeapSort . . .", "Done.");
			a_return = net.forestany.forestj.lib.Sorts.heapSort(a_map, p_b_sortByValue, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (java.util.Map.Entry<String, Integer> o_entry : a_return) {
				System.out.print(o_entry.getKey() + ":" + o_entry.getValue() + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_merge) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			o_progressBar.init("MergeSort . . .", "Done.");
			a_return = net.forestany.forestj.lib.Sorts.mergeSort(a_map, p_b_sortByValue, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (java.util.Map.Entry<String, Integer> o_entry : a_return) {
				System.out.print(o_entry.getKey() + ":" + o_entry.getValue() + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
		
		// ######################################################################################################
		
		if (p_b_quick) {
			java.util.Collections.shuffle(a_list);
			
			for (int i = 1; i <= p_i_amount; i++) {
				a_map.put(Integer.toHexString(i), a_list.get(i - 1));
			}
			
			o_progressBar.init("QuickSort . . .", "Done.");
			a_return = net.forestany.forestj.lib.Sorts.quickSort(a_map, p_b_sortByValue, itf_delegate);
			o_progressBar.close();
			
			int i = 1;
			
			for (java.util.Map.Entry<String, Integer> o_entry : a_return) {
				System.out.print(o_entry.getKey() + ":" + o_entry.getValue() + " ");
				
				if (i++ == i_showValuesAmount) {
					System.out.println("");
					break;
				}
			}
		}
	}
}
