package com.vengestudios.sutdstaffroommaps.generalhelpers;

import java.util.Collections;
import java.util.List;

/**
 * A helper class containing methods for faster sorting,
 * since the in built Java sorting method may not be that fast
 * for all occasions.
 */
public class CustomSorts {

	/**
	 * Partially quick sorts an array such that the k largest elements will be on the right.
	 * The k largest elements may not appear in sorted order.
	 * @param arr The array
	 * @param k   The value of k
	 */
	public static <T extends Comparable<T>> void quickPartialSortToKthAsec(List<T> arr, int k) {
		int from = 0, to = arr.size() - 1;
		while (from < to) {
			int r = from, w = to;
			T mid = arr.get((r + w)>>1);
			while (r < w) {
				if (arr.get(r).compareTo(mid) >= 0) {
					Collections.swap(arr, r, w);
					w--;
				} else {
					r++;
				}
			}
			if (arr.get(r).compareTo(mid) > 0)
				--r;
			if (k <= r) {
				to = r;
			} else {
				from = r+1;
			}
		}
	}

	/**
	 * The median of three method used in quick sort
	 * @param a
	 * @param b
	 * @param c
	 * @return The median of a, b, c
	 */
	public static <T extends Comparable<T>> T medianOfThree(T a, T b, T c) {
		int aToB = a.compareTo(b);
		int bToC = b.compareTo(c);
		int aToC = a.compareTo(c);
		if ((aToB >= 0 && aToC <= 0) || (aToC >= 0 && aToB <= 0)) return a;
		else if ((aToB <= 0 && bToC <= 0) || (bToC >= 0 && aToB >= 0)) return b;
		else return c;
	}

	/**
	 * Sorts a list in ascending order using insertion sort
	 * @param l The list
	 */
	public static <T extends Comparable<T>> void insertionSortAsec(List<T> l) {
		for (int sz=l.size(), i=1; i<sz; i++) {
			int j = i;
			while (j > 0) {
				T prev = l.get(j-1);
				T thisOne = l.get(j);
				if (prev.compareTo(thisOne) > 0) {
					l.set(j-1, thisOne);
					l.set(j, prev);
				} else {
					break;
				}
				j--;
			}
		}
	}

	/**
	 * Sorts a list in descending order using insertion sort
	 * @param l The list
	 */
	public static <T extends Comparable<T>> void insertionSortDesc(List<T> l) {
		for (int sz=l.size(), i=1; i<sz; i++) {
			int j = i;
			while (j > 0) {
				T prev = l.get(j-1);
				T thisOne = l.get(j);
				if (prev.compareTo(thisOne) < 0) {
					l.set(j-1, thisOne);
					l.set(j, prev);
				} else {
					break;
				}
				j--;
			}
		}
	}
}
