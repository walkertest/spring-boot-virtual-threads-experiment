package com.example.loomservlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class TestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * curl "127.0.0.1:8080/where-am-i"
     * @return
     */
    @GetMapping("/where-am-i")
    String getThreadName() {
        return Thread.currentThread().toString();
    }


    /**
     * curl "127.0.0.1:8080/sleepTest?sleepTime=1000"
     * sleep测试
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/sleepTest")
    String getValue(@RequestParam(required = false) Long sleepTime) throws InterruptedException {
        log.info("sleepTime:{}", sleepTime);
        if(sleepTime == null || sleepTime ==0 ) {
            sleepTime = 1000L;
        }
        // Simulate a blocking call for one second. The thread should be put aside for about a second.
        Thread.sleep(sleepTime);
        log.info("return.");
        return "OK";
    }



    @GetMapping("/where-am-i-async")
    Callable<String> getAsyncThreadName() {
        return () -> Thread.currentThread().toString();
    }

    @GetMapping("/sql")
    String getFromDatabase() {

        // Simulate blocking I/O where the server side controls the timeout. The thread should be put aside for about a second.
        return null;
//			return jdbcTemplate.queryForList("select pg_sleep(1);").toString();
    }
}

