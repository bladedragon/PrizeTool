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

@EnableAsync
public class TaskExecutorConfig implements AsyncConfigurer{



    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    @Bean("asyncExecutor")
    public ExecutorService asyncExecutor(){
         ExecutorService executor = new ThreadPoolExecutor(
                corePoolSize, corePoolSize + 10 ,
                101, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1000));

         return executor;
    }




}
