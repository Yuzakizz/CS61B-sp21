package deque;

import org.junit.validator.PublicClassValidator;

public class LinkedListDeque<T> {
    //private TNode first;
    //private TNode last;
    private TNode sentinel;
    private int size;
    public LinkedListDeque() {
            sentinel =new  TNode(null,null,null);
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            size = 0;
    }
    public class TNode {
        private TNode next;
        private TNode prev;
        private T item;
        public TNode(TNode next, TNode prev, T item) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }

    }
    public void addFirst(T item){
        TNode newNode = new TNode(sentinel.next,sentinel,item);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    public void addLast(T item){
        TNode newNode = new TNode(sentinel,sentinel.prev,item);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){

    }

    public T removeFirst(){
        if (size == 0){
            return null;
        }
        TNode deleted = sentinel.next;
        T returnItem = deleted.item;
        deleted.next.prev = sentinel;
        sentinel.next = deleted.next;
        deleted.prev = null;
        deleted.next = null;
        deleted.item = null;
        size--;
        return  returnItem;
    }

    public T removeLast(){
        if (size == 0){
            return null;
        }
        TNode deleted = sentinel.prev;
        T returnItem = deleted.item;
        deleted.prev.next = sentinel;
        sentinel.prev = deleted.prev;
        deleted.prev = null;
        deleted.next = null;
        deleted.item = null;
        size--;
        return  returnItem;
    }

    public T get(int index){
        if(index < 0 || index >= size){
            return null;
        }
        TNode get = sentinel.next;
        for (int i = 0; i < index; i++) {
            get=get.next;
        }
        return get.item;
    }

    public T getRecursive(int index){
        if (index < 0 || index >= size){
            return null;
        }
           return getRecursive(sentinel.next,index);
    }
    private T getRecursive(TNode node,int times){
        if(times==0){
            return node.item;
        }
        return getRecursive(node.next,times-1);
    }
}

/// ** This is a fill in the blanks version of the SLList class
// *  in case you want to try to figure out how to write it yourself.
// */
//public class SLList {
//    public class IntNode {
//        public int item;
//        public IntNode next;
//        public IntNode(int i, IntNode n) {
//            item = i;
//            next = n;
//        }
//    }
//
//    private IntNode first;
//
//    public SLList(int x) {
//        first = new IntNode(x, null);
//    }
//
//    /** Adds an item to the front of the list. */
//    public void addFirst(int x) {
//        first = new IntNode(x, first);
//    }
//
//    /** Retrieves the front item from the list. */
//    public int getFirst() {
//        return first.item;
//    }
//
//    /** Adds an item to the end of the list. */
//    public void addLast(int x) {
//        /* Your Code Here! */
//    }
//
//    /** Returns the number of items in the list using recursion. */
//    public int size() {
//        /* Your Code Here! */
//        return 0;
//    }
//}