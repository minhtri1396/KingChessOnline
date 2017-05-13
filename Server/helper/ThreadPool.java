package helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    
    public static ThreadPool BUILDER = new ThreadPool();
    
    private ExecutorService threadExcutor;
    
    private ThreadPool() {}
    
    public void start() {
        threadExcutor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() - 1); // one for main thread
    }
    
    public int getValidNumberThreads() {
        return Runtime.getRuntime().availableProcessors() - 1;
    }
    
    public void shutdown() {
        threadExcutor.shutdown();
        try {
            if (!threadExcutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                threadExcutor.shutdownNow();
            } 
        } catch (InterruptedException e) {
            threadExcutor.shutdownNow();
        }
    }
    
    public synchronized void execute(Runnable runnable) {
        threadExcutor.execute(runnable);
    }
    
}
