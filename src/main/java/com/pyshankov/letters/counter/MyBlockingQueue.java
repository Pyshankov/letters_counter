package com.pyshankov.letters.counter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pyshankov on 25.10.2016.
 */

public class MyBlockingQueue<E> {

    private Queue<E> queue;

    private int size;

    public MyBlockingQueue(int size) {
        this.size = size;
        this.queue = new LinkedList<>();
    }

    public synchronized E poll(){
        if(queue.size()==0){
            System.out.println("queue is empty");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        E elem = queue.poll();
        notifyAll();
       return elem;
    }

    public synchronized void add(E elem){
        if(queue.size()==size){
            System.out.println("queue full");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(elem);
        notifyAll();
    }


}
