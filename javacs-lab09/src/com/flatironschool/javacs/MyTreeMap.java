/**
 * 
 */
package com.flatironschool.javacs;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a binary search tree.
 * 
 * @param <K>
 * @param <V>
 *
 */
public class MyTreeMap<K, V> implements Map<K, V> {

	private int size = 0;
	private Node root = null;

	/**
	 * Represents a node in the tree.
	 *
	 */
	protected class Node {
		public K key;
		public V value;
		public Node left = null;
		public Node right = null;
		
		/**
		 * @param key
		 * @param value
		 * @param left
		 * @param right
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
		
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	@Override
	public boolean containsKey(Object target) {
		return findNode(target) != null;
	}

	/**
	 * Returns the entry that contains the target key, or null if there is none. 
	 * 
	 * @param target
	 */
	private Node findNode(Object target) {
		// some implementations can handle null as a key, but not this one
		if (target == null) {
            throw new NullPointerException();
	    }
		
		// something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) target;
		
		// the actual search
        // TODO: Fill this in.
        //if find node that contains TARGET as key, return node

        //search the tree
        Node currentNode = root;
        while(currentNode != null){
        	if(k.compareTo(currentNode.key) == 0){
        		return currentNode;
        	}
        	else{
	        	if(k.compareTo(currentNode.key) > 0){
	        		currentNode = currentNode.right;
	        	}
	        	else if(k.compareTo(currentNode.key) < 0){
	        		currentNode = currentNode.left;
	        	}
        	}
        }
        return null;
	}

	/**
	 * Compares two keys or two values, handling null correctly.
	 * 
	 * @param target
	 * @param obj
	 * @return
	 */
	private boolean equals(Object target, Object obj) {
		if (target == null) {
			return obj == null;
		}
		return target.equals(obj);
	}

	@Override
	public boolean containsValue(Object target) {
		//tree traversal
		ArrayList<V> values = new ArrayList<V>();
		Stack<Node> stack = new Stack<Node>();
		Node p = root;

		//tree traversal code source: http://www.programcreek.com/2012/12/leetcode-solution-of-binary-tree-inorder-traversal-in-java/
		while(stack.empty() == false || p != null){
			if(p!= null){
				stack.push(p);
				p = p.left;
			}

			else{
				Node t = stack.pop();
				values.add(t.value);
				p = t.right;
			}
		}

		for(V value: values){
			if(equals(target, value)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		Node node = findNode(key);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new LinkedHashSet<K>();
        // TODO: Fill this in.
		if(root != null){
			inOrderHelper(root, set);
		}

		return set;
	}


	private void inOrderHelper(Node pointer, Set set){
		//inOrder tree traversal source code: http://www.programcreek.com/2012/12/leetcode-solution-of-binary-tree-inorder-traversal-in-java/
		if(pointer.left != null){
			inOrderHelper(pointer.left, set);
		}
		set.add(pointer.key);
		if(pointer.right!=null){
			inOrderHelper(pointer.right, set);
		}
	}

	@Override
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			root = new Node(key, value);
			size++;
			return null;
		}
		return putHelper(root, key, value);
	}

	private V putHelper(Node node, K key, V value) {
        // TODO: Fill this in.
        //if key in tree, replaces old Value, returns old Value
        //if not in tree, create new node, finds right place to add, returns null
		if(containsKey(key) == true){
			Node current = findNode(key);
			V oldValue = current.value;
			current.value = value;
			return oldValue;
		}
		else{

			Node nodeNew = new Node(key, value);
			size+=1;
			Node currentNode = root;
        	while(true){
	
		        	if(((Comparable<? super K>)(nodeNew.key)).compareTo(currentNode.key) < 0){
		        		if(currentNode.left == null){
		        			currentNode.left = nodeNew;
		        			return null;
		        		}
		        		else{
		        			currentNode = currentNode.left;
		        		}

		        	}
		        	else if(((Comparable<? super K>)(nodeNew.key)).compareTo(currentNode.key) > 0){
		        		if(currentNode.right == null){
		        			currentNode.right = nodeNew;
		        			return null;
		        		}
		        		else{
		        			currentNode = currentNode.right;
		        		}
		        	
		        	}
        	}

		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		Set<V> set = new HashSet<V>();
		Deque<Node> stack = new LinkedList<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			if (node == null) continue;
			set.add(node.value);
			stack.push(node.left);
			stack.push(node.right);
		}
		return set;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyTreeMap<String, Integer>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);
		
		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
	}

	/**
	 * Makes a node.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public MyTreeMap<K, V>.Node makeNode(K key, V value) {
		return new Node(key, value);
	}

	/**
	 * Sets the instance variables.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @param node
	 * @param size
	 */
	public void setTree(Node node, int size ) {
		this.root = node;
		this.size = size;
	}

	/**
	 * Returns the height of the tree.
	 * 
	 * This is only here for testing purposes.  Should not be used otherwise.
	 * 
	 * @return
	 */
	public int height() {
		return heightHelper(root);
	}

	private int heightHelper(Node node) {
		if (node == null) {
			return 0;
		}
		int left = heightHelper(node.left);
		int right = heightHelper(node.right);
		return Math.max(left, right) + 1;
	}
}
