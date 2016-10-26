package com.pyshankov.letters.counter.map.reduce;

import com.pyshankov.letters.counter.MyBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pyshankov on 26.10.2016.
 */
public class StringProducer implements Runnable {

    MyBlockingQueue<String> mapQueue;

    RandomStingGenerator stingGenerator;

    public StringProducer(MyBlockingQueue<String> mapQueue) {
        this.mapQueue = mapQueue;
        stingGenerator = new RandomStingGenerator();
    }

    @Override
    public void run() {
        while (true){
            String res = stingGenerator.generateNext();
            mapQueue.add(res);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class RandomStingGenerator{

        private Random random;

        private List<String> letters;

        public RandomStingGenerator() {
            random = new Random();
            letters = new ArrayList<>();
            letters.add("A");
            letters.add("B");
            letters.add("C");
            letters.add("D");
            letters.add("E");
            letters.add("F");
            letters.add("G");
            letters.add("H");
            letters.add("I");
            letters.add("J");
        }

        public String generateNext(){
            StringBuilder builder = new StringBuilder();
            for (int i = 0 ; i < 10; i++){
                builder.append(letters.get((random.nextInt() % 10 + 10) % 10));
            }
            return builder.toString();
        }
    }

}
