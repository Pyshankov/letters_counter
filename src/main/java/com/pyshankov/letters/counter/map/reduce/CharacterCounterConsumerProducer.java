package com.pyshankov.letters.counter.map.reduce;

import com.pyshankov.letters.counter.MyBlockingQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pyshankov on 26.10.2016.
 */
public class CharacterCounterConsumerProducer implements Runnable{

    private MyBlockingQueue<String> mapQueue;

    private MyBlockingQueue<Map<String,Integer>> reduceQueue;

    private Executor executor;

    public CharacterCounterConsumerProducer(
            MyBlockingQueue<String> mapQueue,
            MyBlockingQueue<Map<String,Integer>> reduceQueue,
            int nThread) {
        this.mapQueue = mapQueue;
        this.reduceQueue = reduceQueue;
        executor = Executors.newFixedThreadPool(nThread);
    }

    @Override
    public void run() {
        while (true){
            String result = mapQueue.poll();
            executor.execute(()->
                reduceQueue.add(getCount(result))
            );
        }
    }

    private static Map<String,Integer> getCount(String val){
        String[] res =  val.split("");
        Map<String,Integer> result = new HashMap<>();
        for(String var : res){
            if (!result.containsKey(var)) {
                result.put(var,countCharacter(res,var));
            }
         }
        return result;
    }

    private static Integer countCharacter(String[] res,String val){
        int result = 0;
        for(String var: res){
            if(var.equals(val)){
                result++;
            }
        }
        return result;
    }

}
