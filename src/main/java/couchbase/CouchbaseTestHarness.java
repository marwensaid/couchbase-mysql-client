package couchbase;

import com.google.gson.Gson;
import couchbase.thread.ReadRequestThread;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by msaidi on 2/5/16.
 */
public class CouchbaseTestHarness {

    private CouchbaseConfiguration config;
    private String outputContent;

    public CouchbaseTestHarness() {
    }

    public static CouchbaseTestHarness createCouchbaseTestHarness(String fileName) {
        CouchbaseTestHarness couchbaseTestHarness = new CouchbaseTestHarness();
        couchbaseTestHarness.init(fileName);
        return couchbaseTestHarness;
    }

    private void init(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            Gson gson = new Gson();
            config = gson.fromJson(fileReader, CouchbaseConfiguration.class);
            outputContent = "";

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        int messagesPerSecond = config.getReadsPerSecond() + config.getWritesPerSecond();

        outputContent += "Messages Per Second: " + messagesPerSecond + "\n";
        outputContent += "Number of Iterations: " + config.getIterations() + "\n";
    }

    public void run() {

        int iterations = config.getIterations();
        int messagesPerSecond = config.getReadsPerSecond() + config.getWritesPerSecond();
        CountDownLatch countDownLatch = new CountDownLatch(iterations * messagesPerSecond);

        Map<Integer, Long> callTimes = dispatchEvents(countDownLatch);

        // Wait for all request threads to finish.
        try {
            countDownLatch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        printResults(callTimes);
    }

    private Map<Integer, Long> dispatchEvents(CountDownLatch countDownLatch) {
        ConcurrentHashMap<Integer, Long> callTimes = new ConcurrentHashMap<Integer, Long>();
        int readsPerSecond = config.getReadsPerSecond();
        int writesPerSecond = config.getWritesPerSecond();
        int iterations = config.getIterations();
        int messagesPerSecond = readsPerSecond + writesPerSecond;
        ConcurrentHashMap<String, Object> config_map = config.generateConcurrentHashMap();

        try {
            for (int rt = 0; rt < iterations; rt++) {
                long start = System.currentTimeMillis();
                for (int i = 0; i < readsPerSecond; i++) {
                    //new ReadRequestThread(countDownLatch, config, callTimes, (rt * messagesPerSecond) + i);
                    new ReadRequestThread(countDownLatch, config_map, callTimes, (rt * messagesPerSecond) + i);
                }
                for (int i = readsPerSecond; i < messagesPerSecond; i++) {
                    System.out.println("Implement write request thread");
                }
                long finish = System.currentTimeMillis();
                long difference = finish - start;
                outputContent += "Time to dispatch " + rt + ": " + difference + "ms\n";
                if (difference > 1000) {
                    System.out.println("More than 1 second to dispatch request threads");
                } else {
                    Thread.sleep(1000 - difference);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return callTimes;
    }

    private void printResults(Map<Integer, Long> timePerCall) {
        int time = 0;

        for (Integer i : timePerCall.keySet()) {
            time += timePerCall.get(i);
            outputContent += timePerCall.get(i) + "\n";
        }

        outputContent += "Total Time: " + time;
        try {
            FileWriter fileWriter = new FileWriter(config.getOutputFileName());
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(outputContent);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
