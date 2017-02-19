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

import java.util.Arrays;
import java.util.ConcurrentModificationException;

/**
 * @author Deepak Shajan
 *
 * @param <E>
 */
public class MyArrayList<E> extends MyAbstractList<E> {

	private static final long serialVersionUID = 1L;

	private int arraySize = 15;

	private E[] elements;

	@SuppressWarnings("unchecked")
	@Override
	public void add(E element) {
		if(size==arraySize) {
			arraySize = arraySize<<1+2;
			elements = Arrays.copyOf(elements, arraySize);
		} else if(size==0)
			elements = (E[]) new Object[arraySize];
		elements[size]=element;
		size++;
		modCount++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(int position, E element) {
		while(position>=arraySize)
			arraySize = arraySize<<1+2;
		E[] tempArray = (E[]) new Object[arraySize];
		for(int i=0;i<position;i++)
			if(i<size)
				tempArray[i] = elements[i];
			else 
				tempArray[i]=null;
		tempArray[position]=element;
		for(int i=position+1;i<=arraySize;i++)
			if(i<size+1)
				tempArray[i]=elements[i-1];
		elements=tempArray;
		if(position<size)
			size++;
		else
			size=position+1;
		modCount++;
	}

	@Override
	public void remove(E element) {
		for(int i=0;i<size;i++)
			if(elements[i].equals(element)) {
				while(i<size-1) { //hand over to the while loop
					elements[i]=elements[i+1];
					i++;
				}
				size--;
				elements[size]=null;
				modCount++;
				break;
			}
	}

	@Override
	public void remove(int position) {
		for(int i=position;i<size-1;i++) {
			elements[i]=elements[i+1];
		}
		size--;
		elements[size]=null;
		modCount++;
	}

	@Override
	public boolean contains(E element) {
		for(int i=0;i<size;i++)
			if(elements[i].equals(element))
				return true;
		return false;
	}

	@Override
	public E get(int position) {
		return elements[position];
	}

	@Override
	public void clear() {
		elements=null; //Let GC destroy the object
		size=0;
	}

	public MyIterator<E> iterator() {
		
		return new MyIterator<E>() {

			private int iterIndex;
			
			private int expectedModCount=MyArrayList.this.modCount;
			
			@Override
			public boolean hasNext() {
				return iterIndex!=size;
			}

			@Override
			public E next() {
				return elements[iterIndex++];
			}

			@Override
			public void remove() throws ConcurrentModificationException {
				if(expectedModCount!=MyArrayList.this.modCount) //for fast fail
					throw new ConcurrentModificationException();
				MyArrayList.this.remove(iterIndex);
				expectedModCount++;
			}
		};
	}


}
