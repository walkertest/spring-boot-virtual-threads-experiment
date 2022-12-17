springmvc虚拟线程测试项目.
======================================================================

This repository contains an experiment that uses a Spring Boot application with [Virtual Threads](https://wiki.openjdk.java.net/display/loom/Main).

包版本依赖：

* Spring Framework 6.0 M5
* Spring Boot 3.0 M4
* Apache Tomcat 10.1.0 M17
* HikariCP 5.0.1 (Loom issue: https://github.com/brettwooldridge/HikariCP/issues/1463)
* PGJDBC 42.4.0 (PR that turns `synchronized` into Loom-friendly Locks: https://github.com/pgjdbc/pgjdbc/issues/1951)
 
This experiment evolves incrementally, find the previous state at https://github.com/mp911de/spring-boot-virtual-threads-experiment/tree/boot-2.4. 

You need Java 19 (EAP) with `--enable-preview` to run the example. 


tomcat 开启虚拟线程模式，线程池替换为虚拟线程即可. 
```java
@Bean
AsyncTaskExecutor applicationTaskExecutor() {
    // enable async servlet support
    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    return new TaskExecutorAdapter(executorService::execute);
}

@Bean
TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {

    return protocolHandler -> {
        protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    };
}
```

启动线程之后，判断自己是否处于虚拟线程模式：
```aidl
$ curl "127.0.0.1:8080/where-am-i"
VirtualThread[#73]/runnable@ForkJoinPool-1-worker-17
```

# 性能测试
## sleep模拟耗时情况下的qps
### 虚拟线程模式下的数据
```aidl
./ab  -n 1000 -c 10 "127.0.0.1:8080/sleepTest"
Concurrency Level:      10
Time taken for tests:   102.438 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      134000 bytes
HTML transferred:       2000 bytes
Requests per second:    9.76 [#/sec] (mean)
Time per request:       1024.385 [ms] (mean)
Time per request:       102.438 [ms] (mean, across all concurrent requests)
Transfer rate:          1.28 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.6      0      10
Processing:  1001 1014   5.1   1013    1054
Waiting:     1001 1013   5.0   1013    1054
Total:       1001 1014   5.1   1014    1054

Percentage of the requests served within a certain time (ms)
  50%   1014
  66%   1015
  75%   1016
  80%   1017
  90%   1019
  95%   1022
  98%   1027
  99%   1030
 100%   1054 (longest request)
```

### 网络请求下的耗时


### 数据库/缓存下的耗时.
