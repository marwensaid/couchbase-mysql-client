package couchbase.thread;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by msaidi on 2/5/16.
 */
public class CouchbaseReadRequestThread implements Runnable {

    private final CountDownLatch countDownLatch;
    private final Thread thread;
    private final ConcurrentHashMap<String, Object> cbConfigMap;
    private final ConcurrentHashMap<Integer, Long> map;
    private final int id;
    private Query query;
    private CouchbaseClient client;
    private View view;

    public CouchbaseReadRequestThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Object> cbConfigMap, ConcurrentHashMap<Integer, Long> map, int id) {
        this.countDownLatch = countDownLatch;
        thread = new Thread(this, "ReadRequestThread");
        this.cbConfigMap = cbConfigMap;
        this.map = map;
        this.id = id;
        thread.start();
    }

    public void run() {
        long startTime = 0;
        long endTime = 0;
        this.client = generateClient();
        this.query = generateQuery();
        this.view = generateView(client);

        try {
            startTime = System.currentTimeMillis();
            client.query(view, query);
            endTime = System.currentTimeMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        client.shutdown(1, TimeUnit.SECONDS);
        map.put(id, endTime - startTime);
        countDownLatch.countDown();
    }

    private View generateView(CouchbaseClient client) {
        return client.getView((String) cbConfigMap.get("documentName"), (String) cbConfigMap.get("viewName"));
    }

    private Query generateQuery() {
        Query query = new Query();
        query.setGroup((Boolean) cbConfigMap.get("setGroup"));
        query.setGroupLevel((Integer) cbConfigMap.get("groupLevel"));
        query.setReduce((Boolean) cbConfigMap.get("setReduce"));
        return query;
    }

    private CouchbaseClient generateClient() {
        CouchbaseClient client = null;

        try {
            CouchbaseConnectionFactory cf = new CouchbaseConnectionFactory((List<URI>) cbConfigMap.get("uris"), "default", "");
            client = new CouchbaseClient((CouchbaseConnectionFactory) cf);
        } catch (Exception e) {
            System.err.println("Error connecting to Couchbase: "
                    + e.getMessage());
            System.exit(0);
        }

        return client;
    }
}
