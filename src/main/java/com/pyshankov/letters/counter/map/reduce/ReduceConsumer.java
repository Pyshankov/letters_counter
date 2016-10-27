package com.pyshankov.letters.counter.map.reduce;

import com.pyshankov.letters.counter.MyBlockingQueue;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pyshankov on 26.10.2016.
 */
public class ReduceConsumer implements Runnable{

    private MyBlockingQueue<Map<String,Integer>> reduceQueue;

    private Map<String,Integer> globalResultMap;

    private Executor executor;

    public ReduceConsumer(
            MyBlockingQueue<Map<String, Integer>> reduceQueue,
            Map<String, Integer> globalResultMap,
            int nThread) {
        this.reduceQueue = reduceQueue;
        this.globalResultMap = globalResultMap;
        executor = Executors.newFixedThreadPool(nThread);
    }

    @Override
    public void run() {
        while (true){
            Map<String,Integer> result = reduceQueue.poll();
            executor.execute(()->
                merge(result)
            );
        }
    }

    private  void merge(Map<String,Integer> map){
        System.out.println(globalResultMap);
        synchronized (globalResultMap){
        for (String character: map.keySet()){
                globalResultMap.put(character,globalResultMap.getOrDefault(character,0)+map.get(character));
            }
        }
        System.out.println();
        System.out.println(map);
        System.out.println(globalResultMap);
    }
}
