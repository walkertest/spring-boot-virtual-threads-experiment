import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;

public class VtPinnedDebugDemo {

    static Logger logger = LoggerFactory.getLogger(VtPinnedDebugDemo.class);

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("jdk.tracePinnedThreads", "full");

        testPinned(1);

        Thread.sleep(Duration.ofSeconds(10));
    }

    private static void testPinned(int syncType) {
        Task task = new Task();
        task.setSyncType(syncType);
        Thread.ofVirtual().name("testvt").start(task);
    }


    static class Task implements Runnable {
        int syncType = 0;   //0: no sync; 1: sync; 2: reflect invoke

        public void setSyncType(int syncType) {
            this.syncType = syncType;
        }

        @Override
        public void run() {
            if(syncType == 0 ) {
                taskMethod();
            } else if (syncType == 1) {
                synchronized (this) {
                    taskMethod();
                }
            }
        }

        private  void taskMethod() {
            System.out.println("hello world begin");
            logger.info("start");

            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("end");
            logger.info("end test");

        }
    }

}
