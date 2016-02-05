package mysql;

import common.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by msaidi on 2/5/16.
 */

public class MySQLConfiguration extends Configuration {
    private String userName = null;
    private String password = null;
    private String databaseName = null;
    private String serverName = null;
    private String port = null;
    private String readQuery = null;
    private String createTable = null;
    private String dropTable = null;

    private List<InsertEntry> insertEntries;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getReadQuery() {
        return readQuery;
    }

    public void setReadQuery(String readQuery) {
        this.readQuery = readQuery;
    }

    public String getCreateTable() {
        return createTable;
    }

    public void setCreateTable(String createTable) {
        this.createTable = createTable;
    }

    public String getDropTable() {
        return dropTable;
    }

    public void setDropTable(String dropTable) {
        this.dropTable = dropTable;
    }

    public List<InsertEntry> getInsertEntries() {
        return insertEntries;
    }

    public void setInsertEntries(List<InsertEntry> insertEntries) {
        this.insertEntries = insertEntries;
    }

    public ConcurrentHashMap<String, Object> generateConcurrentHashMap() {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>(super.generateConcurrentHashMap());
        concurrentHashMap.put("userName", userName);
        concurrentHashMap.put("password", password);
        concurrentHashMap.put("databaseName", databaseName);
        concurrentHashMap.put("serverName", serverName);
        concurrentHashMap.put("port", port);
        concurrentHashMap.put("readQuery", readQuery);
        concurrentHashMap.put("createTable", createTable);
        concurrentHashMap.put("dropTable", dropTable);
        return concurrentHashMap;
    }

    public static class InsertEntry {
        private String key;
        private String sessionID;
        private String response;
        private String student;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSessionID() {
            return sessionID;
        }

        public void setSessionID(String sessionID) {
            this.sessionID = sessionID;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }
    }

}
