package com.pyshankov.letters.counter;

import com.pyshankov.letters.counter.map.reduce.CharacterCounterConsumerProducer;
import com.pyshankov.letters.counter.map.reduce.ReduceConsumer;
import com.pyshankov.letters.counter.map.reduce.StringProducer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pyshankov on 25.10.2016.
 */
public class Main extends Application {

    MyBlockingQueue<String> queue = new MyBlockingQueue<>(1);

    public static void main(String[] args) {

        MyBlockingQueue<String> mapQueue = new MyBlockingQueue<>(10);
        MyBlockingQueue<Map<String,Integer>> reduceQueue = new MyBlockingQueue<>(10);
        Map<String,Integer> globalResultMap = new HashMap<>();

        CharacterCounterConsumerProducer characterCounterConsumerProducer
                = new CharacterCounterConsumerProducer(mapQueue,reduceQueue,10);
        ReduceConsumer consumer = new ReduceConsumer(reduceQueue,globalResultMap,10);
        StringProducer producer = new StringProducer(mapQueue);

        new Thread(producer).start();
        new Thread(characterCounterConsumerProducer).start();
        new Thread(consumer).start();

//        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        TextField text= new TextField();
        text.setMinHeight(100);
        Label result= new Label();
        result.setMinHeight(100);

        Consumer consumer = new Consumer(queue,(resultMap)->
            Platform.runLater(()->
                result.setText(resultMap.toString())
            )
        );
        Thread thread = new Thread(consumer);
        thread.setDaemon(true);
        thread.start();

        text.textProperty().addListener((observable, oldValue, newValue) ->
            queue.add(newValue.substring(oldValue.length()))
        );

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(text, 0, 0);
        grid.add(result, 0, 1);

        primaryStage.setScene(new Scene(grid, 300, 250));
        primaryStage.show();
    }


}
