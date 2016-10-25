package com.pyshankov.letters.counter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pyshankov on 25.10.2016.
 */
public class Consumer implements Runnable {

    private MyBlockingQueue<String> blockingQueue;

    private Map<String,Integer> resultMap;

    private java.util.function.Consumer<Map<String,Integer>> callback;

    public Consumer(MyBlockingQueue<String> blockingQueue,java.util.function.Consumer<Map<String,Integer>> callback) {
        this.blockingQueue = blockingQueue;
        resultMap = new HashMap<>();
        this.callback = callback;
    }

    @Override
    public void run() {
        while(true) {
            String letter = blockingQueue.poll();
            if (resultMap.containsKey(letter)) {
                resultMap.put(letter, resultMap.get(letter) + 1);
            } else {
                resultMap.put(letter, 1);
            }
            callback.accept(resultMap);
        }
    }
}
