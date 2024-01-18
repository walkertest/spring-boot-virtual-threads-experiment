import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;

public class VtPinned {

    static Logger logger = LoggerFactory.getLogger(VtPinned.class);

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("jdk.tracePinnedThreads", "full");
//        testPinned(0);
        testPinned(1);
//        testPinned(2);

//        testPinnedReflect(2);

        Thread.sleep(Duration.ofSeconds(10));
    }

    private static void testPinned(int syncType) {
        Task task = new Task();
        task.setSyncType(syncType);
        Thread.ofVirtual().name("testvt").start(task);
    }

    private static void testPinnedReflect(int syncType) {
        Task task = new Task();
        task.setSyncType(syncType);

        try {
            Class c1 = Class.forName("java.lang.Thread$Builder");

            Method m1 = c1.getMethod("name", String.class, long.class);
            Method m2 = c1.getMethod("start", Runnable.class);
            Method m3 = Thread.class.getMethod("ofVirtual", (Class<?>[]) null);

            Object threadBuilder = m3.invoke(null, (Object[]) null);
            m1.invoke(threadBuilder, "test", Long.valueOf(0));
            m2.invoke(threadBuilder, task);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

//        Thread.ofVirtual().name("testvt").start(task);
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
            } else {
                methodReflectInvokeCall();
            }
        }

        private void methodReflectInvokeCall()  {
            Class clazz = null;
            clazz = ServantTest.class;

            ServantTest servantTest = new ServantTest();
            try {
                Method method = clazz.getMethod("test");
                method.invoke(servantTest);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
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


    static class ServantTest {
        public synchronized void test() {
            System.out.println("ServantTest hello world");
            try {
                Thread.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ServantTest hello world end");
        }
    }
}
