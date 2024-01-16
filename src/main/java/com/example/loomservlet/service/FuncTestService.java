package com.example.loomservlet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class FuncTestService {
    Logger log = LoggerFactory.getLogger(FuncTestService.class);

    public static final String SLEEP_1 = "sleep1";
    public static final String TAFRPC = "tafrpc";
    public static final String MYSQL_30_MS = "mysql30ms";

    public static final String SYNC_SLEEP = "syncSleep1";

    public void funcDispatch(String func) {
        long start = System.currentTimeMillis();

        log.info("func:{} vtCount:{}", func, 1);
        if(func.equals(SLEEP_1)) {
            threadSleep1();
        } else if(func.equals(TAFRPC)) {
            log.info("begin to call taf rpc.");
            tafrpc1s();
        } else if(func.equals(MYSQL_30_MS)) {
            mysql30msTest();
        } else if(func.equals(SYNC_SLEEP)) {
            syncSleep();
        }
        long end = System.currentTimeMillis();
        log.info("func:{} cost:{} ms", func, end-start);
    }

    private void mysql30msTest() {
    }

    private void tafrpc1s() {

    }

    private void threadSleep1() {

    }

    private synchronized void syncSleep() {
        System.out.println("test");
        log.info("syncSleep start");
        try {
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("syncSleep end");

    }
}
