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

import java.util.ConcurrentModificationException;

/**
 * 
 * @author Deepak Shajan
 *
 * @param <E>
 */
public class MyLinkedList<E> extends MyAbstractList<E> {

	private static final long serialVersionUID = 1L;

	private MyNode start;

	private MyNode end;

	@Override
	public void add(E element) {
		if(null==start) {
			start = new MyNode(element, null, null);
			end = start;
		} else {
			MyNode newNode = new MyNode(element, end, null);
			end.next = newNode;
			end = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int position, E element) {
		if(position>size)
			throw new IndexOutOfBoundsException();
		else if(position==size)
			add(element);
		else {
			MyNode iterNode = start;
			for(int i=1;i<=position;i++)
				iterNode = iterNode.next;
			MyNode newNode = new MyNode(element, iterNode.prev, iterNode);
			if(null!=iterNode.prev)
				iterNode.prev.next = newNode;
			else //iterNode is same as start Node
				start = newNode;
			iterNode.next.prev = newNode;
			size++;
			modCount++;
		}
	}

	@Override
	public void remove(E element) {
		MyNode iterNode = start;
		while(!iterNode.value.equals(element))
			iterNode = iterNode.next;
		fastRemove(iterNode);
	}

	@Override
	public void remove(int position) {
		MyNode iterNode = start;
		for(int i=1;i<=position;i++)
			iterNode = iterNode.next;
		fastRemove(iterNode);
	}

	@Override
	public boolean contains(E element) {
		if(element.equals(start.value))
			return true;
		MyNode iterNode = start.next;
		while(null!=iterNode) {
			if(element.equals(iterNode.value))
				return true;
			iterNode = iterNode.next;
		}
		return false;
	}

	@Override
	public E get(int position) {
		if(position>=size)
			throw new IndexOutOfBoundsException();
		MyNode returnNode = start;
		for(int i=1;i<=position;i++)
			returnNode = returnNode.next;
		return null!=returnNode?returnNode.value:null;
	}

	@Override
	public void clear() {
		//ignoring the impact on generational garbage collection
		start = null;
		end = null;
		size = 0;
		modCount++;
	}

	@Override
	public MyIterator<E> iterator() {

		return new MyIterator<E>() {

			private int iterIndex;

			private MyNode currentNode = start; //Only ascending iterator is implemented

			private int expectedModCount = MyLinkedList.this.modCount;

			@Override
			public boolean hasNext() {
				return iterIndex!=size;
			}

			@Override
			public E next() {
				if(iterIndex!=0)
					currentNode = currentNode.next;
				iterIndex++;
				return currentNode.value;
			}

			@Override
			public void remove() {
				if(modCount!=expectedModCount)
					throw new ConcurrentModificationException();
				MyLinkedList.this.remove(iterIndex);
				expectedModCount++;
			}
		};
	}

	/**
	 * Custom implementation of a Doubly Linked List Node
	 * @author Deepak Shajan
	 *
	 * @param <E>
	 */
	private class MyNode {

		MyNode prev;

		MyNode next;

		E value;

		MyNode(E value,MyNode prev,MyNode next) {
			this.prev = prev;
			this.next = next;
			this.value = value;
		}
	}

	private void fastRemove(MyNode iterNode) {
		if(iterNode.equals(start))
			start=iterNode.next;
		else if(iterNode.equals(end))
			end=iterNode.prev;
		else {
			iterNode.prev.next = iterNode.next;
			iterNode.next.prev = iterNode.prev;
		}
		iterNode=null; // help GC
		size--;
		modCount++;
	}

}
