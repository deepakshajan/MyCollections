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
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Deepak Shajan
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> implements MyMap<K, V> {

	int size;

	int bucketCount;

	int capacity = 1<<4; // initial capacity is 16

	float loadFactor = 0.75f; // same as 75%

	Entry<K, V>[] buckets; // Each entry in the buckets array holds a singly linked list

	@Override
	public void put(K key, V value) {
		if(null==buckets)
			init();
		if(size>loadFactor*capacity)
			reHashAndGrow();
		int hashedKey = hash(key);
		if(size>0)
			for(int i=0;i<size;i++)
				if(hashedKey==buckets[i].hash) {
					addToBucket(buckets[i], hashedKey, key, value);
					return;
				}
		addToBucket(null, hashedKey, key, value);
		size++;
	}

	@Override
	public V get(K key) {
		for(int i=0;i<bucketCount;i++)
			if(hash(key)==buckets[i].hash) {
				Entry<K, V> bucket = buckets[i];
				if(key.equals(bucket.key))
					return bucket.value;
				while(bucket.hasNext()) {
					bucket = bucket.next;
					if(key.equals(bucket.key))
						return bucket.value;
				}
			}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> getKeySet() { //TODO change return type to custom Set Implementation
		Set<K> keySet = (Set<K>) new HashSet<String>();
		for(int i=0;i<bucketCount;i++) {
			Entry<K, V> bucket = buckets[i];
			keySet.add(bucket.key);
			while(bucket.hasNext())
				keySet.add(bucket.key);
		}
		return keySet;
	}

	@Override
	public boolean contains(K key) {
		for(int i=0;i<bucketCount;i++)
			if(hash(key)==buckets[i].hash) {
				Entry<K, V> bucket = buckets[i];
				if(key.equals(bucket.key))
					return true;
				while(bucket.hasNext()) {
					bucket = bucket.next;
					if(key.equals(bucket.key))
						return true;
				}
			}
		return false;
	}

	/**
	 * Entry class to implement a singly linked list
	 * @author Deepak Shajan
	 *
	 * @param <K>
	 * @param <V>
	 */
	static class Entry<K, V> {

		int hash;
		K key;
		V value;
		Entry<K, V> next;

		Entry(int hash,K key,V value,Entry<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		boolean hasNext() {
			return null!=next;
		}
		
		final Entry<K, V> getNext() {
			return this.next;
		}
	}

	@SuppressWarnings("unchecked")
	private void init() {
		buckets = new Entry[capacity];
	}

	private void reHashAndGrow() {
		//The same hashCode generation algorithm is used even after rehash in this implementation
		capacity = capacity<<1; // double
		buckets = Arrays.copyOf(buckets, capacity);
	}

	static final int hash(Object key) {
		int h;   // uses the same mechanism used in HashMap
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	private void addToBucket(Entry<K, V> bucket, int hashedKey, K key, V value) {
		Entry<K, V> newEntry = new Entry<K, V>(hashedKey, key, value, null);
		if(null==bucket) {
			buckets[bucketCount++] = newEntry;
			return;
		}
		while(bucket.hasNext()) { //iterate to the end of the linked list
			bucket = bucket.next;
			if(value.equals(bucket.value)) {
				bucket.value = value;
				return;
			}
		}
		bucket.next=newEntry;
	}

	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
