package deque;

public class ArrayDeque<T> {
    private T[] array;
    private int size;
    private int front;
    private int back;

    public ArrayDeque(){
        //T[] array=(T[])new Object[8];//0123 4567
        array=(T[])new Object[8];
         size=0;
         front = 7;
         back = 0;
    }
    public void addFirst(T item){ //扩容
        if(size== array.length){
            resize(size*2);
        }
        size++;
        array[front]=item;
        front=(front-1+array.length)%array.length;
    }

    public void addLast(T item){
        if(size== array.length){
            resize(size*2);
        }
        size++;
        array[back]=item;
        back=(back+1+ array.length)%array.length;
    }

    public boolean isEmpty(){
        return  size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int i = 0; i <size; i++) {
            if(i==size-1){
            System.out.print(get(i)+"\n");
            continue;
        }
            System.out.print(get(i)+" ");
        }
    }
    public T removeFirst(){ //缩容
        if(isEmpty()){
            return  null;
        }
        else {
            T retuneItem=array[(front+1+ array.length)%array.length];
            array[(front+1+ array.length)%array.length]=null;
            front=(front+1+ array.length)%array.length;
            size--;
            if (array.length >= 16 && size * 4 < array.length) {
                resize(array.length / 2);
            }

            return retuneItem;
        }
    }
    public T removeLast(){    //缩容
        if(isEmpty()){
            return  null;
        }
        else{
            T retuneItem=array[(back-1+ array.length)%array.length];
            array[(back-1+ array.length)%array.length]=null;
            back=(back-1+ array.length)%array.length;
            size--;
            if (array.length >= 16 && size * 4 < array.length) {
                resize(array.length / 2);
            }

            return retuneItem;
        }
    }
    public T get(int index){
        if(index<0 || index>=size){
            return  null;
        }
        else{
            return array[(front+1+index)%array.length];
        }
    }
    private void resize(int capacity){
        T[] a = (T[])new Object[capacity];
        if (back<front){
            for(int i=0;i<back;i++){//back 不变
                a[i]=array[i];
            }
            front=capacity-(size-front);
            for (int i = capacity-1; i >front; i--) {
                a[i]=array[i];
            }
        }
        else if(back>front){
            for(int i=front+1,j=0;i<back;i++,j++){
                a[j]=array[i];
            }
            front=capacity-1;
            back=size;
        }
        array=a;
    }
}
