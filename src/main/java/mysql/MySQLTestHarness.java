package mysql;

import com.google.gson.Gson;
import mysql.thread.*;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.currentTimeMillis;

/**
 * Created by msaidi on 2/5/16.
 */
public class MySQLTestHarness {

    private MySQLConfiguration config;
    private String outputContent;

    public MySQLTestHarness() {
    }

    public static MySQLTestHarness createMySQLTestHarness(String fileName) {
        MySQLTestHarness mySQLTestHarness = new MySQLTestHarness();
        mySQLTestHarness.init(fileName);
        return mySQLTestHarness;
    }

    private void init(String fileName) {
        try {
            FileReader f = new FileReader(fileName);
            Gson gson = new Gson();
            config = gson.fromJson(f, MySQLConfiguration.class);
            outputContent = "";
        } catch (Exception e) {
            e.printStackTrace();
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

        try {
            countDownLatch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        printResults(callTimes);
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


    private Map<Integer, Long> dispatchEvents(CountDownLatch countDownLatch) {
        ConcurrentHashMap<Integer, Long> callTimes = new ConcurrentHashMap<Integer, Long>();
        int readsPerSecond = config.getReadsPerSecond();
        int writesPerSecond = config.getWritesPerSecond();
        int iterations = config.getIterations();
        int messagesPerSecond = readsPerSecond + writesPerSecond;
        ConcurrentHashMap<String, Object> config_map = config.generateConcurrentHashMap();
        String workloadType = config.getWorkloadType();

        try {
            for (int rt = 0; rt < iterations; rt++) {
                long start = currentTimeMillis();
                if (workloadType.equals("read")) {
                    for (int i = 0; i < readsPerSecond; i++) {
                        new MySQLReadRequestThread(countDownLatch, config_map, callTimes, (rt * messagesPerSecond) + i);
                    }
                } else if (workloadType.equals("write")) {
                    for (int i = 0; i < readsPerSecond; i++) {
                        new WriteRequestThread(countDownLatch, config_map, callTimes, (rt * messagesPerSecond) + i, config.getInsertEntries());
                    }
                } else if (workloadType.equals("game_read")) {
                    new ReadThread(countDownLatch, config_map, callTimes, (rt * messagesPerSecond));
                } else if (workloadType.equals("game_write")) {
                    new WriteThread(countDownLatch, config_map, callTimes, (rt * messagesPerSecond), config.getInsertEntries());
                } else {
                    for (int i = 0; i < readsPerSecond; i++) {
                        new ReadWriteRequestThread(countDownLatch, config_map, callTimes, (rt * messagesPerSecond) + i, config.getInsertEntries());
                    }
                }


                for (int i = readsPerSecond; i < messagesPerSecond; i++) {
                    System.out.println("Implement write request thread");
                }
                long finish = currentTimeMillis();
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

    public static Connection generateConnection(String userName, String password, String serverName, String port, String databaseName) {

        return null;
    }
}
