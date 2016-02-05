import couchbase.CouchbaseTestHarness;
import mysql.MySQLTestHarness;

/**
 * Created by msaidi on 2/5/16.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            System.exit(2);
        }

        if (args[0].equals("MySQL")) {
            MySQLTestHarness mySQLTestHarness = MySQLTestHarness.createMySQLTestHarness(args[1]);
            mySQLTestHarness.run();
        } else if (args[0].equals("Couchbase")) {
            CouchbaseTestHarness couchbaseTestHarness = CouchbaseTestHarness.createCouchbaseTestHarness(args[1]);
            couchbaseTestHarness.run();
        } else {
            printUsage();
        }
        System.exit(0);
    }

    private static void printUsage() {
        System.out.println("cmd : java -cp <classpath> JavaRunner type configFile");
        System.out.println("type : test type to run. must be MySql or Couchbase");
        System.out.println("configFile : Configuration file for the test");
    }
}
