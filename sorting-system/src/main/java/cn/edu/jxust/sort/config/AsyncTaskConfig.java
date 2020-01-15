package cn.edu.jxust.sort.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author: ddh
 * @data: 2020/1/15 10:53
 * @description
 **/
@Configuration
@EnableAsync
public class AsyncTaskConfig implements AsyncConfigurer {
    @Override
    @Bean
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        // 核心线程数
        threadPool.setCorePoolSize(10);
        threadPool.setMaxPoolSize(15);
        threadPool.setQueueCapacity(5);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60);
        threadPool.setThreadNamePrefix("Derry-Async-");
        threadPool.initialize();
        return threadPool;
    }
}
