/**
 * MIT License

Copyright (c) 2017 deepakshajan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.deepak.util;

import java.io.Serializable;

/**
 * Custom implementation of collections in java.
 * Has an iterator inner interface as well
 * 
 * @author Deepak Shajan
 *
 */
public interface MyCollection<E> extends Serializable,Cloneable {

	/**
	 * Add a single element to the Collection
	 */
	void add(E element);
	
	/**
	 * Add the element to the position.All elements after this position will be shifted to the right by one index
	 * @param position
	 * @param element
	 */
	void add(int position,E element);
	
	/**
	 * Remove the element from the collection
	 * If multiple elements are present then only the first element is removed
	 * @param element
	 */
	void remove(E element);
	
	/**
	 * Remove the element in the given position
	 * @param position
	 * @param element
	 */
	void remove(int position);
	
	/**
	 * @return size of the collection
	 */
	int size();
	
	/**
	 * Check if the collection is empty
	 * @return true if collection is empty, else return false
	 */
	boolean isEmpty();
	
	/**
	 * Check if the collection contains the element 
	 * @return true if collection contains the element, else return false
	 */
	boolean contains(E element);
	
	/**
	 * Get the element at the position
	 * @param position
	 * @return E at position
	 */
	E get(int position);
	
	/**
	 * Clear the List
	 */
	void clear();
	
	/**
	 * 
	 * @return a custom implemented iterator for the collection
	 */
	MyIterator<E> iterator();
	
	/**
	 * Inner interface defining the iterator for the collection class
	 * @author Deepak Shajan
	 *
	 * @param <E>
	 */
	static interface MyIterator<E> {

		/**
		 * @return true if there is another element next, else return false.
		 */
		boolean hasNext();
		
		/**
		 * 
		 * @return the next element
		 */
		E next();
		
		/**
		 * Remove the current iterated element
		 * @param element
		 */
		void remove();
	}
}
