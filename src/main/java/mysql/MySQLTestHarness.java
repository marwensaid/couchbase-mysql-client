package mysql;

/**
 * Created by msaidi on 2/5/16.
 */
public class MySQLTestHarness {

    public MySQLTestHarness() {
    }

    public static MySQLTestHarness createMySQLTestHarness(String fileName) {
        MySQLTestHarness mySQLTestHarness = new MySQLTestHarness();
        mySQLTestHarness.init(fileName);
        return mySQLTestHarness;
    }

    private void init(String fileName) {

    }

    public void run() {

    }
}
