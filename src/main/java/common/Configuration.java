package common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by msaidi on 2/5/16.
 */
public class Configuration {
    private int iterations;
    private int readsPerSecond;
    private int writesPerSecond;
    private String outputFileName;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getReadsPerSecond() {
        return readsPerSecond;
    }

    public void setReadsPerSecond(int readsPerSecond) {
        this.readsPerSecond = readsPerSecond;
    }

    public int getWritesPerSecond() {
        return writesPerSecond;
    }

    public void setWritesPerSecond(int writesPerSecond) {
        this.writesPerSecond = writesPerSecond;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public ConcurrentHashMap<String, Object> generateConcurrentHashMap() {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>();
        concurrentHashMap.put("iterations", iterations);
        concurrentHashMap.put("readsPerSecond", readsPerSecond);
        concurrentHashMap.put("writesPerSecond", writesPerSecond);
        concurrentHashMap.put("outputFileName", outputFileName);

        return concurrentHashMap;
    }
}
