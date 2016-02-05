package couchbase;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by msaidi on 2/5/16.
 */
public class ReadRequestThread implements Runnable {

    private final CountDownLatch countDownLatch;
    private final Thread thread;
    private final ConcurrentHashMap<String, Object> cbConfigMap;
    private final ConcurrentHashMap<Integer, Long> map;
    private final int id;

    public ReadRequestThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Object> cbConfigMap, ConcurrentHashMap<Integer, Long> map, int id) {
        this.countDownLatch = countDownLatch;
        thread = new Thread(this, "ReadRequestThread");
        this.cbConfigMap = cbConfigMap;
        this.map = map;
        this.id = id;
        thread.start();
    }

    public void run() {

    }
}
