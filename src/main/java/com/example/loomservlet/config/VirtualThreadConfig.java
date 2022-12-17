package com.example.loomservlet.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadConfig {

    @Bean
    AsyncTaskExecutor applicationTaskExecutor() {
        // enable async servlet support
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        return new TaskExecutorAdapter(executorService::execute);
    }

//    @Bean
//    TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
//
//        //todo--增加一个虚拟线程的开关.
//
//        return protocolHandler -> {
//            ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
//            //todo--add 线程名字
////            executorService.
//            protocolHandler.setExecutor(executorService);
//        };
//    }
}
