package com.voiceai.cloud.config;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月10日 21:51:00
 */
@Configuration
public class HystrixConfiguration {

    /**
     * 1. Command Properties
     * <p>
     * Execution：控制HystrixCommand.run() 的如何执行
     * Fallback: 控制HystrixCommand.getFallback() 如何执行
     * Circuit Breaker： 控制断路器的行为
     * Metrics: 捕获和HystrixCommand 和 HystrixObservableCommand 执行信息相关的配置属性
     * Request Context：设置请求上下文的属性
     * <p>
     * 2. Collapser Properties：设置请求合并的属性
     * 3. Thread Pool Properties：设置线程池的属性
     *
     * @return  HystrixCommandProperties
     */
    @Bean
    public HystrixCommandProperties hystrixCommandProperties() {

        HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true)
                /**
                 * circuit 相关的属性，控制HystrixCommand.getFallback()如何执行
                 */
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withCircuitBreakerForceClosed(false)
                .withCircuitBreakerForceOpen(false)
                // 该属性设置滚动窗口中将使断路器跳闸的最小请求数量 ==> 10s 使断路器跳闸的最小请求数量
                .withCircuitBreakerRequestVolumeThreshold(20)
                // 该值的时间内，断路器会中断所有的请求，只有当过了这个时间之后，断路器才会开启接收新的请求
                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                .withCircuitBreakerEnabled(true)

                /**
                 * execution 相关的属性，控制Hystrix.run() 相关的功能
                 *
                 */
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
                // 并发请求时的隔离策略，默认的隔离策略是线程隔离，通常不会使用Semphore隔离，Semphore通常只会用在非网络调用的时候。
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                // 是否开启超时设置
                .withExecutionTimeoutEnabled(true)
                // 调用者执行的超时时间
                .withExecutionTimeoutInMilliseconds(1000)
                // 超时中断Hystrix.run()的执行
                .withExecutionIsolationThreadInterruptOnTimeout(true)
                // 取消任务执行时，中断Hystrix.run()的执行
                .withExecutionIsolationThreadInterruptOnFutureCancel(true)

                /**
                 * fallback
                 *
                 */
                // 是否开启fallback功能
                .withFallbackEnabled(true)
                /**
                 * 此属性设置从调用线程允许HystrixCommand.getFallback（）方法允许的最大并发请求数
                 * 如果达到最大的并发量，则接下来的请求会被拒绝并且抛出异常.
                 */
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(10)

                // 统计滚动窗口的时间长度
                .withMetricsRollingStatisticalWindowInMilliseconds(60000)
                // 统计滚动窗口的桶数量
                .withMetricsRollingStatisticalWindowBuckets(20)
                // 设置执行延迟是否被跟踪，并且被计算在失败百分比中。如果设置为false,则所有的统计数据返回-1
                .withMetricsRollingPercentileEnabled(true)
                // 设置统计滚动百分比窗口的桶数量
                .withMetricsRollingPercentileBucketSize(10)
                // 此属性设置统计滚动百分比窗口的持续时间
                .withMetricsRollingPercentileWindowInMilliseconds(60000)
                // 采样时间间隔
                .withMetricsHealthSnapshotIntervalInMilliseconds(500);

        HystrixCommandKey key = HystrixCommandKey.Factory.asKey("default");
        return new CustomrizedHystrixCommandProperties(key, setter);
    }

}
