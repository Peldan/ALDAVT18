//Arvid Peldan - arpe3186@student.su.se

package alda.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList<E> implements ALDAList<E> {
	
	
	private static class Node<E> {
		private E data;
		private Node<E> next;
		
		public Node(E data){
			this.data = data;
		}
		
		public String toString(){
			return data.toString();
		}
		
	}

	private Node<E> first;
	private Node<E> last;
	private Node<E> curr;
	private int size;
	
	public MyALDAList(){
		this.first = new Node<E>(null);
		this.curr = first;
		this.last = first;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new MyALDAListIterator();
	}
	
	class MyALDAListIterator implements Iterator<E>{	
		boolean canRemove = false;
		int cnt = 0;
		E data;
		Node<E> curr;
		Node<E> prev = null;
			
		public MyALDAListIterator(){
			curr = first.next;
		}
		
		@Override
		public boolean hasNext() {
			return cnt < size;
		}
		
		@Override
	    public E next() {
			if(hasNext()){
				data = curr.data;
				prev = curr;
				curr = curr.next;
				cnt++;
				canRemove = true;
				return data;
			} else {
				throw new NoSuchElementException();
			}
	    }
		
		public void remove(){
			if(!canRemove){
				throw new IllegalStateException();
			}
			MyALDAList.this.remove(prev.data);
			canRemove = false;
			cnt--;
		}
	   		
	}
	
	@Override
	public void add(E element) {
		Node<E> newNode = new Node<E>(element);
		last.next = newNode;
		last = newNode;
		if(size == 0){
			first.next = newNode;
		}
		size++;
	}
	
	@Override
	public void add(int index, E element) {
		Node<E> prevNode = null;
		Node<E> newNode = new Node<E>(element);
		curr = first.next;
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		else if(index == size){
			add(element);
		}
		else if (index == 0) {
			newNode.next = first.next;
			size++;
			first.next = newNode;
		} else {
			for (int i = 0; i < index && curr.next != null; i++) {
				prevNode = curr;
				curr = curr.next;
			}
			prevNode.next = newNode;
			size++;
			newNode.next = curr;
		}
	}
	
	@Override
	public E remove(int index) {
		int count = 0;
		Node<E> prevNode = null;
		if(size == 0 || index > size - 1|| index < 0){
			throw new IndexOutOfBoundsException();
		}
		if(index != 0){
			for(curr = first.next; first.next != null; curr = curr.next){
				if(count == index){
					if(curr == last){
						last = prevNode;
					} else {
						prevNode.next = prevNode.next.next;
					}
					size--;
					return curr.data;
				}
				count++;
				prevNode = curr;
			}
		} else {
			E toRemove = first.next.data;
			first.next = first.next.next;
			size--;
			return toRemove;
		}
		return null;
	}
	
	@Override
	public boolean remove(E element) {
		int index = 0;
		Iterator<E> it = iterator();
		while(it.hasNext()){
			E currElement = it.next();
			if(currElement == element || currElement.equals(element)){
				remove(index);
				return true;
			}
			index++;
			
		}
		return false;
	}
	
	@Override
	public E get(int index) {
		int count = 0;
		if(size == 0 || index > size - 1|| index < 0){
			throw new IndexOutOfBoundsException();
		}
		for(curr = first.next; first.next != null; curr = curr.next){
			count++;
			if(count - 1 == index){
				return curr.data;
				
			}
		}
		return null;
	}
	
	@Override
	public boolean contains(E element) {
		Iterator<E> it = iterator();
		while(it.hasNext()){
			E currElement = it.next();
			if(currElement == element || currElement.equals(element)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int indexOf(E element) {
		Iterator<E> it = iterator();
		int index = 0;
		while(it.hasNext()){
			E curr = it.next();
			if(curr == element || curr.equals(element)){
				return index;
			}
			index++;
		}
		return -1;
	}
	
	@Override
	public void clear() {
		first.next = null;
		last = first;
		size = 0;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	public String toString(){
		String listContents = "[";
		E currElement;
		Iterator<E> it = iterator();
		while(it.hasNext()){
			currElement = it.next();
			if(!it.hasNext()){
				listContents += currElement;
			} else {
				listContents += currElement + ", ";
			}
		}
		listContents += "]";
		return listContents;
	}
}
