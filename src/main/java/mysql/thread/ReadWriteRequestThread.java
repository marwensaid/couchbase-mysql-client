package mysql.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by msaidi on 2/5/16.
 */
public class ReadWriteRequestThread {
    public ReadWriteRequestThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Object> config_map, ConcurrentHashMap<Integer, Long> callTimes, int i, Object insertEntries) {
    }
}
