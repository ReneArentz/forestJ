package net.forestany.forestj.lib;

/**
 * Collection of static methods to sort dynamic lists and dynamics key-value maps.
 * Available sort algorithms:
 *  - selection
 *  - insertion
 *  - bubble
 *  - heap
 *  - merge
 *  - quick
 * Also possibility to get sort progress with delegate implementation.
 */
public class Sorts {
	
	/* Delegates */
	
	/**
	 * interface which can be implemented as a parameter with the sort methods to post sort progress
	 */
	public interface IDelegate {
		/**
		 * method to post progress while sort is executed
		 * 
		 * @param p_d_progress		percent progress as a double
		 */
		void PostProgress(double p_d_progress);
	}
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public Sorts() {
		
	}
	
	/**
	 * Sort a dynamic list with selection sort
	 * 
	 * @param <T>			type of elements in list
	 * @param p_ol_list		dynamic list which will be sorted
	 */
	public static <T extends Comparable<? super T>> void selectionSort(java.util.List<T> p_ol_list) {
		Sorts.selectionSort(p_ol_list, null);
	}
	
	/**
	 * Sort a dynamic list with selection sort
	 * 
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	public static <T extends Comparable<? super T>> void selectionSort(java.util.List<T> p_ol_list, Sorts.IDelegate p_itf_delegate) {
		long l_overallCount = 0;
		int i_left = 0;
		int i_min = 0;
		
		do {
			i_min = i_left;
	
			/* find minimum */
			for (int i = i_left + 1; i < p_ol_list.size(); i++) {
				/* compare */
				if (p_ol_list.get(i).compareTo(p_ol_list.get(i_min)) < 0) {
					i_min = i;
				}
			}
			
			/* swap */
			T o_temp = p_ol_list.get(i_min);
			p_ol_list.set(i_min, p_ol_list.get(i_left));
			p_ol_list.set(i_left, o_temp);
			
			i_left++;
			l_overallCount++;
			
			if (p_itf_delegate != null) {
				p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
			}
		} while (i_left < p_ol_list.size());
		
		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
		}
	}
	
	/**
	 * Sort a dynamic list with insertion sort
	 * 
	 * @param <T>			type of elements in list
	 * @param p_ol_list		dynamic list which will be sorted
	 */
	public static <T extends Comparable<? super T>> void insertionSort(java.util.List<T> p_ol_list) {
		Sorts.insertionSort(p_ol_list, null);
	}
	
	/**
	 * Sort a dynamic list with insertion sort
	 * 
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	public static <T extends Comparable<? super T>> void insertionSort(java.util.List<T> p_ol_list, Sorts.IDelegate p_itf_delegate) {
		long l_overallCount = 0;
		
		for (int i = 1; i < p_ol_list.size(); i++) {
			T o_temp = p_ol_list.get(i);
			int j = i;

			/* compare */
			while ((j > 0) && (p_ol_list.get(j - 1).compareTo(o_temp) > 0)) {
				/* swap */
				p_ol_list.set(j, p_ol_list.get(j - 1));
				j--;
			}

			p_ol_list.set(j, o_temp);
			
			l_overallCount++;
			
			if (p_itf_delegate != null) {
				p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
			}
		}

		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
		}
	}
	
	
	/**
	 * Sort a dynamic list with bubble sort
	 * 
	 * @param <T>			type of elements in list
	 * @param p_ol_list		dynamic list which will be sorted
	 */
	public static <T extends Comparable<? super T>> void bubbleSort(java.util.List<T> p_ol_list) {
		Sorts.bubbleSort(p_ol_list, null);
	}
	
	/**
	 * Sort a dynamic list with bubble sort
	 * 
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	public static <T extends Comparable<? super T>> void bubbleSort(java.util.List<T> p_ol_list, Sorts.IDelegate p_itf_delegate) {
		long l_overallCount = 0;
		
		for (int j = p_ol_list.size() - 1; j >= 0; j--) {
			for (int i = 0; i <= j - 1; i++) {
				/* compare */
				if (p_ol_list.get(i).compareTo(p_ol_list.get(i + 1)) > 0) {
					/* swap */
					T o_temp = p_ol_list.get(i);
					p_ol_list.set(i, p_ol_list.get(i + 1));
					p_ol_list.set(i + 1, o_temp);
				}
			}
			
			l_overallCount++;
			
			if (p_itf_delegate != null) {
				p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
			}
		}
		
		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
		}
	}
	
	
	/**
	 * Sort a dynamic list with heap sort
	 * 
	 * @param <T>			type of elements in list
	 * @param p_ol_list		dynamic list which will be sorted
	 */
	public static <T extends Comparable<? super T>> void heapSort(java.util.List<T> p_ol_list) {
		Sorts.heapSort(p_ol_list, null);
	}
	
	/**
	 * Sort a dynamic list with heap sort
	 * 
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	public static <T extends Comparable<? super T>> void heapSort(java.util.List<T> p_ol_list, Sorts.IDelegate p_itf_delegate) {
		long l_overallCount = 0;
		
		/* create help list */
		java.util.ArrayList<T> a_heap = new java.util.ArrayList<T>();
		
		/* initialize help list with null */
		for (int i = 0; i < p_ol_list.size() + 1; i++) {
			a_heap.add(null);
		}

		for (int i = 0; i < p_ol_list.size(); i++) {
			/* fill help list */
			a_heap.set(i + 1,  p_ol_list.get(i));
			int j = i + 1;

			while (j > 1) {
				/* compare */
				if (a_heap.get(j / 2).compareTo(a_heap.get(j)) < 0) {
					/* swap */
					T o_temp = a_heap.get(j);
					a_heap.set(j, a_heap.get(j / 2));
					a_heap.set(j / 2, o_temp);
					j = (j / 2);
				} else {
					break;
				}
			}
		}

		for (int i = 0; i < p_ol_list.size(); i++) {
			/* set max element from help list in list */
			p_ol_list.set(p_ol_list.size() - 1 - i, a_heap.get(1));
			
			/* delete max element in help list */
			a_heap.set(1, a_heap.get(p_ol_list.size() - i));
			a_heap.set(p_ol_list.size() - i, null);
			int j = 1;
			
			while ((2 * j) <= (p_ol_list.size() - i)) {
				int i_maxChild;
				
				/* compare */
				if ((2 * j + 1) <= (p_ol_list.size() - i)) {
					/* compare */
					if ( (a_heap.get(2 * j + 1) == null) || (a_heap.get(2 * j).compareTo(a_heap.get(2 * j + 1)) > 0) ) {
						i_maxChild = 2 * j;
					} else {
						i_maxChild = 2 * j + 1;
					}
				} else {
					i_maxChild = 2 * j;
				}

				/* compare */
				if ( (a_heap.get(i_maxChild) != null) && (a_heap.get(j).compareTo(a_heap.get(i_maxChild)) < 0) ) {
					/* swap */
					T o_temp = a_heap.get(j);
					a_heap.set(j, a_heap.get(i_maxChild));
					a_heap.set(i_maxChild, o_temp);

					j = i_maxChild;
				} else {
					break;
				}
			}
			
			l_overallCount++;
			
			if (p_itf_delegate != null) {
				p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
			}
		}
		
		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress(l_overallCount / (double)p_ol_list.size());
		}
	}
	
	
	/**
	 * Sort a dynamic list with merge sort
	 * 
	 * @param <T>			type of elements in list
	 * @param p_ol_list		dynamic list which will be sorted
	 */
	public static <T extends Comparable<? super T>> void mergeSort(java.util.List<T> p_ol_list) {
		Sorts.mergeSort(p_ol_list, null);
	}
	
	/**
	 * Sort a dynamic list with merge sort
	 * 
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	public static <T extends Comparable<? super T>> void mergeSort(java.util.List<T> p_ol_list, Sorts.IDelegate p_itf_delegate) {
		java.util.concurrent.atomic.AtomicLong l_overallCount = new java.util.concurrent.atomic.AtomicLong(0);
		
		mergeSortRecursive(0, p_ol_list.size() - 1, p_ol_list, l_overallCount, p_itf_delegate);
		
		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress(l_overallCount.get() / (double)p_ol_list.size());
		}
	}
	
	/**
	 * Recursive call of the merge sort, only private access within sorts collection
	 * 
	 * @param p_i_left			left index(start) of merge part within recursion
	 * @param p_i_right			right index(end) of merge part within recursion
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_l_overallCount	overall count for sort algorithm for progress
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	private static <T extends Comparable<? super T>> void mergeSortRecursive(int p_i_left, int p_i_right, java.util.List<T> p_ol_list, java.util.concurrent.atomic.AtomicLong p_l_overallCount, Sorts.IDelegate p_itf_delegate) {
		/* cancel condition */
		if (p_i_left < p_i_right) {
			int i_center = (p_i_left + p_i_right) / 2;
			mergeSortRecursive(p_i_left, i_center, p_ol_list, p_l_overallCount, p_itf_delegate);
			mergeSortRecursive(i_center + 1, p_i_right, p_ol_list, p_l_overallCount, p_itf_delegate);
			Merge(p_i_left, i_center, p_i_right, p_ol_list, p_l_overallCount, p_itf_delegate);
		}
	}
	
	/**
	 * Implementation of merge sort which can be used recursively, only private access within sorts collection
	 * 
	 * @param p_i_left			left index(start) of merge part within recursion
	 * @param p_i_center		center index(middle) of merge part within recursion
	 * @param p_i_right			right index(end) of merge part within recursion
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_l_overallCount	overall count for sort algorithm for progress
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	private static <T extends Comparable<? super T>> void Merge(int p_i_left, int p_i_center, int p_i_right, java.util.List<T> p_ol_list, java.util.concurrent.atomic.AtomicLong p_l_overallCount, Sorts.IDelegate p_itf_delegate) {
		/* create help list */
		java.util.ArrayList<T> a_help = new java.util.ArrayList<T>();
		
		/* initialize help list with null */
		for (int i = 0; i < (p_i_right - p_i_left + 1); i++) {
			a_help.add(null);
		}
		
		int i = 0;
		int l = p_i_left;
		int r = p_i_center + 1;

		while((l <= p_i_center) && (r <= p_i_right)) {
			/* compare */
			if (p_ol_list.get(l).compareTo(p_ol_list.get(r)) <= 0) {
				/* swap */
				a_help.set(i, p_ol_list.get(l));
				l++;
			} else {
				/* swap */
				a_help.set(i, p_ol_list.get(r));
				r++;
			}
			
			i++;
		}

		if (l > p_i_center) {
			for (int j = r; j <= p_i_right; j++) {
				/* swap */
				a_help.set(i, p_ol_list.get(j));
				i++;
			}
		} else {
			for (int j = l; j <= p_i_center; j++) {
				/* swap */
				a_help.set(i, p_ol_list.get(j));
				i++;
			}
		}

		for (i = 0; i <= (p_i_right - p_i_left); i++) {
			p_ol_list.set(i + p_i_left, a_help.get(i));
		}
		
		p_l_overallCount.set(p_l_overallCount.get() + 1);
		
		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress(p_l_overallCount.get() / (double)p_ol_list.size());
		}
	}

	
	/**
	 * Sort a dynamic list with quick sort
	 * 
	 * @param <T>			type of elements in list
	 * @param p_ol_list		dynamic list which will be sorted
	 */
	public static <T extends Comparable<? super T>> void quickSort(java.util.List<T> p_ol_list) {
		Sorts.quickSort(p_ol_list, null);
	}
	
	/**
	 * Sort a dynamic list with quick sort
	 * 
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	public static <T extends Comparable<? super T>> void quickSort(java.util.List<T> p_ol_list, Sorts.IDelegate p_itf_delegate) {
		java.util.concurrent.atomic.AtomicLong l_overallCount = new java.util.concurrent.atomic.AtomicLong(0);
		
		boolean b_sortNeeded = false;

		/* check if we have a list which is already sorted, otherwise the recursion will kill our stack */
        for (int i = 0; i < p_ol_list.size() - 1; i++)
        {
            if (p_ol_list.get(i).compareTo(p_ol_list.get(i+1)) > 0)
            {
                b_sortNeeded = true;
                break;
            }
        }

        if (b_sortNeeded)
        {
			quickSortRecursive(0, p_ol_list.size() - 1, p_ol_list, l_overallCount, p_itf_delegate);
			
			if (p_itf_delegate != null) {
				p_itf_delegate.PostProgress((l_overallCount.get() / 2) / (double)p_ol_list.size());
			}
        }
	}
	
	/**
	 * Implementation of quick sort which can be used recursively, only private access within sorts collection
	 * 
	 * @param p_i_left			left index(start) of quicksort part within recursion
	 * @param p_i_right			right index(end) of quicksort part within recursion
	 * @param <T>				type of elements in list
	 * @param p_ol_list			dynamic list which will be sorted
	 * @param p_l_overallCount	overall count for sort algorithm for progress
	 * @param p_itf_delegate	delegate object with implemented method to post sort progress
	 */
	private static <T extends Comparable<? super T>> void quickSortRecursive(int p_i_left, int p_i_right, java.util.List<T> p_ol_list, java.util.concurrent.atomic.AtomicLong p_l_overallCount, Sorts.IDelegate p_itf_delegate) {
		if (p_i_left < p_i_right)
		{
			int i = p_i_left;
			int j = p_i_right - 1;
			T o_pivot = p_ol_list.get(p_i_right);
			
			do {
				/* compare */
				while ((p_ol_list.get(i).compareTo(o_pivot) <= 0) && (i < p_i_right)) {
					i++;
				}
				
				/* compare */
				while ((p_ol_list.get(j).compareTo(o_pivot) >= 0) && (j > p_i_left)) {
					j--;
				}

				if (i < j) {
					/* swap */
					T o_temp = p_ol_list.get(i);
					p_ol_list.set(i, p_ol_list.get(j));
					p_ol_list.set(j, o_temp);
				}
			}
			while (i < j);

			if (p_ol_list.get(i).compareTo(o_pivot) > 0) {
				/* swap */
				T o_temp = p_ol_list.get(i);
				p_ol_list.set(i, p_ol_list.get(p_i_right));
				p_ol_list.set(p_i_right, o_temp);
			}
			
			quickSortRecursive(p_i_left, i - 1, p_ol_list, p_l_overallCount, p_itf_delegate);
			quickSortRecursive(i + 1, p_i_right, p_ol_list, p_l_overallCount, p_itf_delegate);
			
			p_l_overallCount.set(p_l_overallCount.get() + 1);
			
			if (p_itf_delegate != null) {
				p_itf_delegate.PostProgress((p_l_overallCount.get() / 2) / (double)p_ol_list.size());
			}
		}
		
		p_l_overallCount.set(p_l_overallCount.get() + 1);
		
		if (p_itf_delegate != null) {
			p_itf_delegate.PostProgress((p_l_overallCount.get() / 2) / (double)p_ol_list.size());
		}
	}
	
	/* ############################################################################################################ */
	
	/**
	 * Sort a dynamic key-value list with selection sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the parameter key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> selectionSort(java.util.Map<T, U> p_m_list) throws IllegalStateException {
		return Sorts.selectionSort(p_m_list, true);
	}
	
	/**
	 * Sort a dynamic key-value list with selection sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> selectionSort(java.util.Map<T, U> p_m_list, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.selectionSort(p_m_list, true, p_itf_delegate);
	}
	
	/**
	 * Sort a dynamic key-value list with selection sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> selectionSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue) throws IllegalStateException {
		return Sorts.selectionSort(p_m_list, p_b_sortByValue, null);
	}
	
	/**
	 * Sort a dynamic key-value list with selection sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> selectionSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.genericMapSort(p_m_list, p_b_sortByValue, p_itf_delegate, 0);
	}
	
	
	/**
	 * Sort a dynamic key-value list with insertion sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the parameter key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> insertionSort(java.util.Map<T, U> p_m_list) throws IllegalStateException {
		return Sorts.insertionSort(p_m_list, true);
	}
	
	/**
	 * Sort a dynamic key-value list with insertion sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> insertionSort(java.util.Map<T, U> p_m_list, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.insertionSort(p_m_list, true, p_itf_delegate);
	}
	
	/**
	 * Sort a dynamic key-value list with insertion sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> insertionSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue) throws IllegalStateException {
		return Sorts.insertionSort(p_m_list, p_b_sortByValue, null);
	}
	
	/**
	 * Sort a dynamic key-value list with insertion sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> insertionSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.genericMapSort(p_m_list, p_b_sortByValue, p_itf_delegate, 1);
	}
	
	
	/**
	 * Sort a dynamic key-value list with bubble sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the parameter key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> bubbleSort(java.util.Map<T, U> p_m_list) throws IllegalStateException {
		return Sorts.bubbleSort(p_m_list, true);
	}
	
	/**
	 * Sort a dynamic key-value list with bubble sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> bubbleSort(java.util.Map<T, U> p_m_list, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.bubbleSort(p_m_list, true, p_itf_delegate);
	}
	
	/**
	 * Sort a dynamic key-value list with bubble sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> bubbleSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue) throws IllegalStateException {
		return Sorts.bubbleSort(p_m_list, p_b_sortByValue, null);
	}
	
	/**
	 * Sort a dynamic key-value list with bubble sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> bubbleSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.genericMapSort(p_m_list, p_b_sortByValue, p_itf_delegate, 2);
	}
	
	
	/**
	 * Sort a dynamic key-value list with heap sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the parameter key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> heapSort(java.util.Map<T, U> p_m_list) throws IllegalStateException {
		return Sorts.heapSort(p_m_list, true);
	}
	
	/**
	 * Sort a dynamic key-value list with heap sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> heapSort(java.util.Map<T, U> p_m_list, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.heapSort(p_m_list, true, p_itf_delegate);
	}
	
	/**
	 * Sort a dynamic key-value list with heap sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> heapSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue) throws IllegalStateException {
		return Sorts.heapSort(p_m_list, p_b_sortByValue, null);
	}
	
	/**
	 * Sort a dynamic key-value list with heap sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> heapSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.genericMapSort(p_m_list, p_b_sortByValue, p_itf_delegate, 3);
	}
	
	
	/**
	 * Sort a dynamic key-value list with merge sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the parameter key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> mergeSort(java.util.Map<T, U> p_m_list) throws IllegalStateException {
		return Sorts.mergeSort(p_m_list, true);
	}
	
	/**
	 * Sort a dynamic key-value list with merge sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> mergeSort(java.util.Map<T, U> p_m_list, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.mergeSort(p_m_list, true, p_itf_delegate);
	}
	
	/**
	 * Sort a dynamic key-value list with merge sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> mergeSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue) throws IllegalStateException {
		return Sorts.mergeSort(p_m_list, p_b_sortByValue, null);
	}
	
	/**
	 * Sort a dynamic key-value list with merge sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> mergeSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.genericMapSort(p_m_list, p_b_sortByValue, p_itf_delegate, 4);
	}
	
	
	/**
	 * Sort a dynamic key-value list with quick sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the parameter key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> quickSort(java.util.Map<T, U> p_m_list) throws IllegalStateException {
		return Sorts.quickSort(p_m_list, true);
	}
	
	/**
	 * Sort a dynamic key-value list with quick sort, sort by value
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> quickSort(java.util.Map<T, U> p_m_list, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.quickSort(p_m_list, true, p_itf_delegate);
	}
	
	/**
	 * Sort a dynamic key-value list with quick sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> quickSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue) throws IllegalStateException {
		return Sorts.quickSort(p_m_list, p_b_sortByValue, null);
	}
	
	/**
	 * Sort a dynamic key-value list with quick sort
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	public static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> quickSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate) throws IllegalStateException {
		return Sorts.genericMapSort(p_m_list, p_b_sortByValue, p_itf_delegate, 5);
	}
	
	
	/**
	 * Implementation of all sort methods for dynamic key-value lists, only private access within sorts collection
	 * 
	 * @param <T>						type of key elements in key-value list
	 * @param <U>						type of value elements in key-value list
	 * @param p_m_list					dynamic key-value list which will be sorted
	 * @param p_b_sortByValue			true - sort key-value list by value, false - sort key-value list by key
	 * @param p_itf_delegate			delegate object with implemented method to post sort progress
	 * @param p_i_sortAlgorithm			0 - selection, 1 - insertion, 2 - bubble, 3 - heap, 4 - merge, 5 - quick
	 * @return							list of map entries - java.util.List&lt; java.util.Map.Entry&lt;T, U&gt;&gt;
	 * @throws IllegalStateException	thrown if a temporary help list has not the same amount of elements,
	 * 									a key-value entry could not be found or the sorted result has not
	 * 									the same amount of elements as the given unsorted key-value list
	 * @see java.util.List
	 * @see java.util.Map.Entry
	 */
	private static <T extends Comparable<? super T>, U extends Comparable<? super U>> java.util.List<java.util.Map.Entry<T, U>> genericMapSort(java.util.Map<T, U> p_m_list, boolean p_b_sortByValue, Sorts.IDelegate p_itf_delegate, int p_i_sortAlgorithm) throws IllegalStateException {
		/* check for valid sort algorithm parameter */
		if ( (p_i_sortAlgorithm < 0) || (p_i_sortAlgorithm > 5) ) {
			throw new IllegalStateException("Parameter for sort algorithm[" + p_i_sortAlgorithm + "] is not between[0..5]");
		}
		
		/* return value */
		java.util.List<java.util.Map.Entry<T, U>> m_return = new java.util.ArrayList<java.util.Map.Entry<T, U>>();
		
		if (p_b_sortByValue) { /* sort by value */
			/* put values is hash set to determine duplicates */
			java.util.Set<U> a_valuesSet = new java.util.HashSet<U>(p_m_list.values());
			boolean b_duplicates = false;
			
			/* check if dynamics key-value list as parameter contains duplicates */
			if (a_valuesSet.size() != p_m_list.values().size()) {
				b_duplicates = true;
			}
			
			if (b_duplicates) { /* handle duplicates */
				java.util.List<U> a_tempList = new java.util.ArrayList<U>();
				java.util.Map<U, Integer> m_duplicateMap = new java.util.HashMap<U, Integer>();
				java.util.Map<String, T> m_tempMap = new java.util.HashMap<String, T>();
				
				for (java.util.Map.Entry<T, U> o_entry : p_m_list.entrySet()) {
					/* add value to temporary list which will be sorted later */
					a_tempList.add(o_entry.getValue());
					
					if (m_duplicateMap.containsKey(o_entry.getValue())) {
						/* duplicate found, increment amount value in duplicate hash map */
						m_duplicateMap.put( o_entry.getValue(), (m_duplicateMap.get(o_entry.getValue()) + 1) );
					} else {
						/* new value, put value into duplicate hash map with amount 1 */
						m_duplicateMap.put( o_entry.getValue(), 1 );
					}
					
					/* put value with amount and key in temporary map - is used for reconstruction of sorted values and return key-value list */
					m_tempMap.put(o_entry.getValue().toString() + "__sep__" + m_duplicateMap.get(o_entry.getValue()), o_entry.getKey());
				}
				
				/* check if temporary map has same amount of elements as the given unsorted key-value list */
				if (m_tempMap.size() != p_m_list.size()) {
					throw new IllegalStateException("Temp map size[" + m_tempMap.size() + "] is not equal parameter map size[" + p_m_list.size() + "]");
				}
				
				/* execution sort algorithm with temporary list */
				if (p_i_sortAlgorithm == 0) {
					Sorts.selectionSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 1) {
					Sorts.insertionSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 2) {
					Sorts.bubbleSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 3) {
					Sorts.heapSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 4) {
					Sorts.mergeSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 5) {
					Sorts.quickSort(a_tempList, p_itf_delegate);
				}
				
				long l_overallCount = 0;
				
				/* reconstruction of sorted values and return key-value list */
				for (int i = 0; i < a_tempList.size(); i++) {
					for (int j = 1; j <= p_m_list.size(); j++) {
						/* look for key in temporary map with sorted temporary list values */
						if (m_tempMap.containsKey(a_tempList.get(i) + "__sep__" + j)) {
							/* get key */
							T foo = m_tempMap.get(a_tempList.get(i) + "__sep__" + j);
							/* get value */
							U bar = p_m_list.get(foo);
							
							/* add key value entry to return list */
							m_return.add( java.util.Map.entry( foo, bar ) );
							/* m_return.add(new java.util.AbstractMap.SimpleEntry<T, U>(foo, bar)); // <= 1.8 */
							
							/* remove found key, so an exception will occur if it will be searched again */
							m_tempMap.remove(a_tempList.get(i) + "__sep__" + j);
							break;
						}
						
						/* could not find key in temporary map */
						if (j == p_m_list.size()) {
							throw new IllegalStateException("Could not find temp map value[" + a_tempList.get(i) + "__sep__integer" + j + "]");
						}
					}
					
					l_overallCount++;
					
					/* post reconstruction progress */
					if (p_itf_delegate != null) {
						p_itf_delegate.PostProgress(l_overallCount / (double)p_m_list.size());
					}
				}
			} else { /* no duplicates */
				java.util.List<U> a_tempList = new java.util.ArrayList<U>();
				java.util.Map<U, T> m_tempMap = new java.util.HashMap<U, T>();
				
				/* fill temporary list with values and temporary map with values and keys */
				for (java.util.Map.Entry<T, U> o_entry : p_m_list.entrySet()) {
					a_tempList.add(o_entry.getValue());
					m_tempMap.put(o_entry.getValue(), o_entry.getKey());
				}
				
				/* check if temporary map has same amount of elements as the given unsorted key-value list */
				if (m_tempMap.size() != p_m_list.size()) {
					throw new IllegalStateException("Temp map size[" + m_tempMap.size() + "] is not equal parameter map size[" + p_m_list.size() + "]");
				}
				
				/* execution sort algorithm with temporary list */
				if (p_i_sortAlgorithm == 0) {
					Sorts.selectionSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 1) {
					Sorts.insertionSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 2) {
					Sorts.bubbleSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 3) {
					Sorts.heapSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 4) {
					Sorts.mergeSort(a_tempList, p_itf_delegate);
				} else if (p_i_sortAlgorithm == 5) {
					Sorts.quickSort(a_tempList, p_itf_delegate);
				}
				
				long l_overallCount = 0;
				
				for (int i = 0; i < a_tempList.size(); i++) {
					/* check if key exists in temporary map */
					if (!m_tempMap.containsKey(a_tempList.get(i))) {
						throw new IllegalStateException("Could not find key value[" + a_tempList.get(i) + "] in temporary map");
					}
					
					/* get key from temporary map by value of sorted temporary list */
					T foo = m_tempMap.get(a_tempList.get(i));
					
					/* add key value entry to return list */
					m_return.add( java.util.Map.entry( foo, a_tempList.get(i) ) );
					/* m_return.add(new java.util.AbstractMap.SimpleEntry<T, U>(foo, a_tempList.get(i))); // <= 1.8 */
					
					/* remove key from temporary map */
					m_tempMap.remove(a_tempList.get(i));
					
					l_overallCount++;
					
					/* post reconstruction progress */
					if (p_itf_delegate != null) {
						p_itf_delegate.PostProgress(l_overallCount / (double)p_m_list.size());
					}
				}
			}
		} else {  /* sort by key */
			java.util.List<T> a_tempList = new java.util.ArrayList<T>();
			java.util.Map<T, U> m_tempMap = new java.util.HashMap<T, U>();
			
			/* fill temporary list with values and temporary map with values and keys */
			for (java.util.Map.Entry<T, U> o_entry : p_m_list.entrySet()) {
				a_tempList.add(o_entry.getKey());
				m_tempMap.put(o_entry.getKey(), o_entry.getValue());
			}
			
			/* check if temporary map has same amount of elements as the given unsorted key-value list */
			if (m_tempMap.size() != p_m_list.size()) {
				throw new IllegalStateException("Temp map size[" + m_tempMap.size() + "] is not equal parameter map size[" + p_m_list.size() + "]");
			}
			
			/* execution sort algorithm with temporary list */
			if (p_i_sortAlgorithm == 0) {
				Sorts.selectionSort(a_tempList, p_itf_delegate);
			} else if (p_i_sortAlgorithm == 1) {
				Sorts.insertionSort(a_tempList, p_itf_delegate);
			} else if (p_i_sortAlgorithm == 2) {
				Sorts.bubbleSort(a_tempList, p_itf_delegate);
			} else if (p_i_sortAlgorithm == 3) {
				Sorts.heapSort(a_tempList, p_itf_delegate);
			} else if (p_i_sortAlgorithm == 4) {
				Sorts.mergeSort(a_tempList, p_itf_delegate);
			} else if (p_i_sortAlgorithm == 5) {
				Sorts.quickSort(a_tempList, p_itf_delegate);
			}
			
			long l_overallCount = 0;
			
			for (int i = 0; i < a_tempList.size(); i++) {
				/* check if key exists in temporary map */
				if (!m_tempMap.containsKey(a_tempList.get(i))) {
					throw new IllegalStateException("Could not find key value[" + a_tempList.get(i) + "] in temporary map");
				}
				
				/* get value from temporary map by key of sorted temporary list */
				U foo = m_tempMap.get(a_tempList.get(i));
				
				/* add key value entry to return list */
				m_return.add( java.util.Map.entry( a_tempList.get(i), foo ) );
				/* m_return.add(new java.util.AbstractMap.SimpleEntry<T, U>(a_tempList.get(i), foo)); // <= 1.8 */
				
				/* remove key from temporary map */
				m_tempMap.remove(a_tempList.get(i));
				
				l_overallCount++;
				
				/* post reconstruction progress */
				if (p_itf_delegate != null) {
					p_itf_delegate.PostProgress(l_overallCount / (double)p_m_list.size());
				}
			}
		}
		
		/* last check if sorted result list has not the same amount of elements as the given unsorted key-value list */
		if (m_return.size() != p_m_list.size()) {
			throw new IllegalStateException("Result list size[" + m_return.size() + "] is not equal parameter map size[" + p_m_list.size() + "]");
		}
		
		return m_return;
	}
}
