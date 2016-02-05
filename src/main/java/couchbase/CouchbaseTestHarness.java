package couchbase;

/**
 * Created by msaidi on 2/5/16.
 */
public class CouchbaseTestHarness {

    public CouchbaseTestHarness() {
    }

    public static CouchbaseTestHarness createCouchbaseTestHarness(String fileName) {
        CouchbaseTestHarness couchbaseTestHarness = new CouchbaseTestHarness();
        couchbaseTestHarness.init(fileName);
        return couchbaseTestHarness;
    }

    private void init(String fileName) {

    }

    public void run() {

    }
}
