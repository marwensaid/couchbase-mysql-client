package couchbase;

import common.Configuration;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by msaidi on 2/5/16.
 */
public class CouchbaseConfiguration extends Configuration {
    private String viewName;
    private boolean setGroup;
    private int groupLevel;
    private boolean setReduce;
    private String docName;
    private List<URI> uris;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public boolean isSetGroup() {
        return setGroup;
    }

    public void setSetGroup(boolean setGroup) {
        this.setGroup = setGroup;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }

    public boolean isSetReduce() {
        return setReduce;
    }

    public void setSetReduce(boolean setReduce) {
        this.setReduce = setReduce;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<URI> getUris() {
        return uris;
    }

    public void setUris(List<URI> uris) {
        this.uris = uris;
    }

    public ConcurrentHashMap<String, Object> generateConcurrentHashMap() {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>(super.generateConcurrentHashMap());
        concurrentHashMap.put("viewName", viewName);
        concurrentHashMap.put("setGroup", setGroup);
        concurrentHashMap.put("groupLevel", groupLevel);
        concurrentHashMap.put("setReduce", setReduce);
        concurrentHashMap.put("docName", docName);
        concurrentHashMap.put("uris", uris);

        return concurrentHashMap;
    }
}
