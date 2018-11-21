package model;

import java.util.ArrayList;

public class LinkedPlayList<T> {

	private LinkedNode<T> root;
	private LinkedNode<T> last;

	public void add(T value) {
		if (root == null) {
			root = new LinkedNode<T>(value);
			last = root;
		} else {
			LinkedNode<T> node = new LinkedNode<T>(value);
			last.setNext(node);
			node.setLast(last);
			last = node;
		}
	}

	@SuppressWarnings("hiding")
	public class LinkedNode<T> {

		private T value;
		private LinkedNode<T> next;
		private LinkedNode<T> last;

		public LinkedNode(T value) {
			this.value = value;
		}

		public void setLast(LinkedNode<T> last) {
			this.last = last;

		}

		public void setNext(LinkedNode<T> next) {
			this.next = next;
		}

		public LinkedNode<T> getNext() {
			return next;
		}

		public LinkedNode<T> getLast() {
			return last;
		}

		public T getValue() {
			return value;
		}

	}

	public ArrayList<T> values() {
		ArrayList<T> res = new ArrayList<>();
		LinkedNode<T> node = root;
		while (node != null) {
			res.add(node.getValue());
			node = node.getNext();
		}
		return res;
	}
}
