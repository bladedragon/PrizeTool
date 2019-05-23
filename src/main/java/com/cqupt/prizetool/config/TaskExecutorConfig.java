package com.cqupt.prizetool.config;


import com.cqupt.prizetool.mapper.master.SpecifiedTypeMapper;
import com.cqupt.prizetool.service.TemplateMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
//@EnableAsync
public class TaskExecutorConfig {//实现AsyncConfigurer接口
    //
//    @Override
//    @Bean("processExecutor")
//    public Executor getAsyncExecutor() {//实现AsyncConfigurer接口并重写getAsyncExecutor方法，并返回一个ThreadPoolTaskExecutor，这样我们就获得了一个基于线程池TaskExecutor
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.setMaxPoolSize(20);
//        taskExecutor.setQueueCapacity(25);
//        taskExecutor.initialize();
//
//        return taskExecutor;
//    }
//
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return null;
//    }
//
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    public ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize, corePoolSize + 1,
            101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));


}
