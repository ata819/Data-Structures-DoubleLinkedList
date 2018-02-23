/* Alan Ta cssc 0941 */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E> {
   private int currentSize, modCounter;
   private Node<E> head, tail;

   class Node<T> {
       T data;
       Node<T> next, prev;

       public Node(T obj) {
           data = obj;
           next = prev = null;
       }
   }

   public LinearList() {
       head = tail = null;
       currentSize = 0;
       modCounter = 0;
   }

   public boolean addFirst(E obj) {
       Node<E> newNode = new Node<E>(obj);
       if (head == null)
           head = tail = newNode;		/* If new Node, sets H&T pointers */
       else {
           newNode.next = head;		/* Shifts the head pointer to newNode */
           head.prev = newNode;
           head = newNode;
       }
       currentSize++;			/* Increase size and modCounter */
       modCounter++;
       return true;
   }

   public boolean addLast(E obj) {
       Node<E> newNode = new Node<E>(obj);
       if (tail == null)
           head = tail = newNode;		/* If new Node, sets H&T pointers */
       else {
           tail.next = newNode;		/* creates new tail node and sets pointer */
           newNode.prev = tail;
           tail = newNode;
       }
       currentSize++;
       modCounter++;
       return true;

   }

   public E removeFirst() {
       if (isEmpty())
           return null;			/* Checks list if empty */
       E tmp = head.data;			/* Stores head data to return */
       if (head == tail)			/* For single element, leaves list empty */
           head = tail = null;
       else {					
           head = head.next;		/* Moves head pointer */
           head.prev = null;		/* Cuts of connection with prev */
       }
       currentSize--;			/* Decrease size, increase modCounter */
       modCounter++;
       return tmp;				/* returns head data */
   }

   public E removeLast() {
       if (isEmpty())
           return null;
       E tmp = tail.data;			/* Saves tail data to return */
       if (head == tail)
           return removeFirst();
       else {
           tail.prev.next = null;	
           tail = tail.prev;		/* Cuts off connection and set new tail Ptr */
       }
       modCounter++;
       currentSize--;
       return tmp;
   }

   public E remove(E obj) {
       Node<E> prev = null, current = head;	/* sets current = head */
       while (current != null && 
((Comparable<E>) obj).compareTo(current.data) != 0)
           current = current.next;	/* Goes through the list to find obj */
       if (current == null)
           return null;
       if (current == head)
           return removeFirst();
       if (current == tail)
           return removeLast();		/* Checks cases if empty,rmFirst,and rmLast */
       else {
           current.prev.next = current.next;
           current.next.prev = current.prev;
       }				/* Removes obj by setting new pointer */
       currentSize--;
       modCounter++;
       return current.data;

   }

   public E peekFirst() {
       if (isEmpty())
           return null;
       return head.data;		/* returns head data */
   }

   public E peekLast() {
       if (isEmpty())
           return null;
       return tail.data;		/* returns tail data */
   }

   public boolean contains(E obj) {return find(obj) != null;}

   public E find(E obj) {
       Node<E> tmp = head;
       while (tmp != null) {
           if (tmp.data == obj)
               return tmp.data;
           tmp = tmp.next;		/* finds obj by going through the list */
       }
       return null;
   }

   public void clear() {
       head = tail = null;	
       modCounter++;
       currentSize = 0;
   }

   public boolean isEmpty() {return currentSize == 0;}

   public boolean isFull() {return false;}

   public int size() {return currentSize;}

   public Iterator<E> iterator() {return new IteratorHelper();}

   class IteratorHelper implements Iterator<E> {
       private Node<E> iterPtr;
       private long modCheck;

       public IteratorHelper() {
           modCheck = modCounter;
           iterPtr = head;
       }

       public boolean hasNext() {
           if (modCheck != modCounter)
               throw new ConcurrentModificationException();
           return iterPtr != null;
       }

       public E next() {
           if (!hasNext())
               throw new NoSuchElementException();
           E tmp = iterPtr.data;
           iterPtr = iterPtr.next;
           return tmp;
       }

       public void remove() {
           throw new UnsupportedOperationException();
       }
   }

}
