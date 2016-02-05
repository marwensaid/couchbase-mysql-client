package mysql.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by msaidi on 2/5/16.
 */
public class MySQLReadRequestThread implements Runnable {
    private final CountDownLatch countDownLatch;
    private final ConcurrentHashMap<String, Object> cbConfigMap;
    private final ConcurrentHashMap<Integer, Long> map;
    private final int id;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public MySQLReadRequestThread(CountDownLatch countDownLatch, ConcurrentHashMap<String, Object> cbConfigMap, ConcurrentHashMap<Integer, Long> map, int id) {
        this.countDownLatch = countDownLatch;
        Thread thread = new Thread(this, "ReadRequestThread");
        this.cbConfigMap = cbConfigMap;
        this.map = map;
        this.id = id;
        thread.start();
    }

    public void run() {
        long startTime = 0;
        long endTime = 0;

        try {
            this.connection = generateConnection(
                    (String) this.cbConfigMap.get("userName"),
                    (String) this.cbConfigMap.get("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.preparedStatement = generatePreparedStatement(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            startTime = System.currentTimeMillis();
            this.preparedStatement.executeQuery();
            endTime = System.currentTimeMillis();
            this.connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        map.put(id, endTime - startTime);
        countDownLatch.countDown();
    }

    private Connection generateConnection(String userName, String password) throws SQLException {
        Connection connection = null;
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", this.cbConfigMap.get("userName"));
        connectionProperties.put("password", this.cbConfigMap.get("password"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:mysql://" +
                this.cbConfigMap.get("serverName") + ":" +
                this.cbConfigMap.get("port") + "/" +
                this.cbConfigMap.get("databaseName"),
                connectionProperties);
        System.out.println("Connected to database");
        return connection;
    }

    private PreparedStatement generatePreparedStatement(Connection connection) throws SQLException {
        return connection.prepareStatement((String) this.cbConfigMap.get("readQuery"));
    }

}
