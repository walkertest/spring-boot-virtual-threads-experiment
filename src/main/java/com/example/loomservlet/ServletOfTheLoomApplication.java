package com.example.loomservlet;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind
		.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServletOfTheLoomApplication {

	public static void main(String[] args) {
		System.setProperty("jdk.tracePinnedThreads","full");
		SpringApplication.run(ServletOfTheLoomApplication.class, args);
	}

}
