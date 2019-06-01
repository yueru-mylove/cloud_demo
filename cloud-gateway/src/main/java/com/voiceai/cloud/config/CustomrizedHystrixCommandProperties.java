package com.voiceai.cloud.config;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;


/**
 * 1. 概述
 * 本文对Hystrix的配置参数的覆盖优先级，可配置参数的种类、配置项进行详细的介绍。
 * Hystrix可以配置属性的有以下类型：
 * 1. Command Properties
 *
 * Execution：控制HystrixCommand.run() 的如何执行
 * Fallback: 控制HystrixCommand.getFallback() 如何执行
 * Circuit Breaker： 控制断路器的行为
 * Metrics: 捕获和HystrixCommand 和 HystrixObservableCommand 执行信息相关的配置属性
 * Request Context：设置请求上下文的属性
 *
 * 2. Collapser Properties：设置请求合并的属性
 * 3. Thread Pool Properties：设置线程池的属性
 * 2. Hystrix参数的覆盖优先级
 * 每个Hystrix参数都有4个地方可以配置，优先级从低到高如下，如果每个地方都配置相同的属性，则优先级高的值会覆盖优先级低的值
 *
 * 1 内置全局默认值：写死在代码里的值
 * 2 动态全局默认属性：通过属性文件配置全局的值
 * 3 内置实例默认值：写死在代码里的实例的值
 * 4 动态配置实例属性：通过属性文件配置特定实例的值
 *
 * 3 Hystrix配置属性详解
 * Hystrix可以配置属性的有以下类型：
 * 1. Command Properties
 *
 * Execution：控制HystrixCommand.run() 的如何执行
 * Fallback: 控制HystrixCommand.getFallback() 如何执行
 * Circuit Breaker： 控制断路器的行为
 * Metrics: 捕获和HystrixCommand 和 HystrixObservableCommand 执行信息相关的配置属性
 * Request Context：设置请求上下文的属性
 *
 * 2. Collapser Properties：设置请求合并的属性
 * 3. Thread Pool Properties：设置线程池的属性
 * 3.1. Execution
 * 以下属性控制HystrixCommand.run() 的如何执行
 * 1. execution.isolation.strategy
 * 表示HystrixCommand.run()的执行时的隔离策略，有以下两种策略
 *
 * 1 THREAD: 在单独的线程上执行，并发请求受线程池中的线程数限制
 * 2 SEMAPHORE: 在调用线程上执行，并发请求量受信号量计数限制
 *
 * 在默认情况下，推荐HystrixCommands 使用 thread 隔离策略，HystrixObservableCommand 使用 semaphore 隔离策略。
 * 只有在高并发(单个实例每秒达到几百个调用)的调用时，才需要修改HystrixCommands 的隔离策略为semaphore 。semaphore 隔离策略通常只用于非网络调用
 * 默认值：THREAD,
 * // 设置所有实例的默认值
 * hystrix.command.default.execution.isolation.strategy=..
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.execution.isolation.strategy=...
 *
 *
 * 2. execution.isolation.thread.timeoutInMilliseconds
 * 设置调用者执行的超时时间（单位毫秒）
 * 默认值：1000
 * // 设置所有实例的默认值
 * hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.execution.isolation.thread.timeoutInMilliseconds=...
 *
 *
 * 3. execution.timeout.enabled
 * 表示是否开启超时设置。
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.command.default.execution.timeout.enabled=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.execution.timeout.enabled=...
 *
 * 4. execution.isolation.thread.interruptOnTimeout
 * 表示设置是否在执行超时时，中断HystrixCommand.run() 的执行
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.command.default.execution.isolation.thread.interruptOnTimeout=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.execution.isolation.thread.interruptOnTimeout=...
 *
 * 5. execution.isolation.thread.interruptOnCancel
 * 表示设置是否在取消任务执行时，中断HystrixCommand.run() 的执行
 * 默认值：false
 * // 设置所有实例的默认值
 * hystrix.command.default.execution.isolation.thread.interruptOnCancel=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.execution.isolation.thread.interruptOnCancel
 *
 * 6. execution.isolation.semaphore.maxConcurrentRequests
 * 当HystrixCommand.run()使用SEMAPHORE的隔离策略时，设置最大的并发量
 * 默认值：10
 * // 设置所有实例的默认值
 * hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests=...
 *
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.execution.isolation.semaphore.maxConcurrentRequests=...
 *
 * 3.2. Fallback
 * 以下属性控制HystrixCommand.getFallback() 如何执行。这些属性对隔离策略THREAD 和SEMAPHORE都起作用.
 * 1. fallback.isolation.semaphore.maxConcurrentRequests
 * 此属性设置从调用线程允许HystrixCommand.getFallback（）方法允许的最大并发请求数
 * 如果达到最大的并发量，则接下来的请求会被拒绝并且抛出异常.
 * 默认值：10
 * // 设置所有实例的默认值
 * hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.fallback.isolation.semaphore.maxConcurrentRequests=...
 *
 * 2. fallback.enabled
 * 是否开启fallback功能
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.command.default.fallback.enabled=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.fallback.enabled=...
 *
 * 3.3. Circuit Breaker
 * 控制断路器的行为
 * 1. circuitBreaker.enabled
 * 是否开启断路器功能
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.command.default.circuitBreaker.enabled=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.circuitBreaker.enabled=...
 *
 * 2. circuitBreaker.requestVolumeThreshold
 * 该属性设置滚动窗口中将使断路器跳闸的最小请求数量
 * 如果此属性值为20，则在窗口时间内（如10s内），如果只收到19个请求且都失败了，则断路器也不会开启。
 * 默认值：20
 * // 设置所有实例的默认值
 * hystrix.command.default.circuitBreaker.requestVolumeThreshold=...
 *
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.circuitBreaker.requestVolumeThreshold=...
 *
 * 3. circuitBreaker.sleepWindowInMilliseconds
 * 断路器跳闸后，在此值的时间的内，hystrix会拒绝新的请求，只有过了这个时间断路器才会打开闸门
 * 默认值：5000
 * // 设置所有实例的默认值
 * hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.circuitBreaker.sleepWindowInMilliseconds=...
 *
 * 4. circuitBreaker.errorThresholdPercentage
 * 设置失败百分比的阈值。如果失败比率超过这个值，则断路器跳闸并且进入fallback逻辑
 * 默认值：50
 * // 设置所有实例的默认值
 * hystrix.command.default.circuitBreaker=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.circuitBreaker.errorThresholdPercentage=...
 *
 * 5. circuitBreaker.forceOpen
 * 如果设置true，则强制使断路器跳闸，则会拒绝所有的请求.此值会覆盖circuitBreaker.forceClosed的值
 * 默认值：false
 * // 设置所有实例的默认值
 * hystrix.command.default.circuitBreaker.forceOpen=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.circuitBreaker.forceOpen=...
 *
 *
 * 6. circuitBreaker.forceClosed
 * 如果设置true，则强制使断路器进行关闭状态，此时会允许执行所有请求，无论是否失败的次数达到circuitBreaker.errorThresholdPercentage值
 * 默认值：false
 * // 设置所有实例的默认值
 * hystrix.command.default.circuitBreaker.forceClosed=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.circuitBreaker.forceClosed=...
 *
 * 3.4. Metrics
 * 捕获和HystrixCommand 和 HystrixObservableCommand 执行信息相关的配置属性
 * 1. metrics.rollingStats.timeInMilliseconds
 * 设置统计滚动窗口的时间长度
 * 如果此值为10s，将窗口分成10个桶，每个桶表示1s时间，则统计信息如下图：
 *
 *
 *
 *
 *
 *
 * image.png
 *
 * 默认值： 10000
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.rollingStats.timeInMilliseconds=....
 *
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.rollingStats.timeInMilliseconds=...
 *
 * 2. metrics.rollingStats.numBuckets
 * 设置统计滚动窗口的桶数量，
 * 注意：以下配置必须成立，否则会抛出异常。
 * metrics.rollingStats.timeInMilliseconds % metrics.rollingStats.numBuckets == 0
 *
 * 如：10000/10、10000/20是正确的配置,但是10000/7错误的
 * 在高并发的环境里，每个桶的时间长度建议大于100ms
 * 默认值：10
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.rollingStats.numBuckets=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.rollingStats.numBuckets=...
 *
 * 3. metrics.rollingPercentile.enabled
 * 设置执行延迟是否被跟踪，并且被计算在失败百分比中。如果设置为false,则所有的统计数据返回-1
 * 默认值： true
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.rollingPercentile.enabled=...
 *
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.rollingPercentile.enabled=...
 *
 * 4. metrics.rollingPercentile.timeInMilliseconds
 * 此属性设置统计滚动百分比窗口的持续时间
 * 默认值：60000
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.rollingPercentile.timeInMilliseconds=...
 *
 * 5. metrics.rollingPercentile.numBuckets
 * 设置统计滚动百分比窗口的桶数量
 * 注意：以下配置必须成立，否则会抛出异常。
 * metrics.rollingPercentile.timeInMilliseconds % metrics.rollingPercentile.numBuckets == 0
 *
 * 如： 60000/6、60000/60是正确的配置,但是10000/7错误的
 * 在高并发的环境里，每个桶的时间长度建议大于1000ms
 * 默认值：6
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.rollingPercentile.numBuckets=...
 *
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.rollingPercentile.numBuckets=...
 *
 *
 * 6. metrics.rollingPercentile.bucketSize
 * 此属性设置每个桶保存的执行时间的最大值。如果桶数量是100，统计窗口为10s，如果这10s里有500次执行，只有最后100次执行会被统计到bucket里去
 * 默认值：100
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.rollingPercentile.bucketSize=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.rollingPercentile.bucketSize=...
 *
 * 7. metrics.healthSnapshot.intervalInMilliseconds
 * 采样时间间隔
 * 默认值：500
 * // 设置所有实例的默认值
 * hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.metrics.healthSnapshot.intervalInMilliseconds=...
 *
 * 3.5. Request Context
 * 此属性控制HystrixCommand使用到的Hystrix的上下文
 * 1. requestCache.enabled
 * 是否开启请求缓存功能
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.command.default.requestCache.enabled=...
 *
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.requestCache.enabled=...
 *
 *
 * 2. requestLog.enabled
 * 表示是否开启日志，打印执行HystrixCommand的情况和事件
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.command.default.requestLog.enabled=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.command.HystrixCommandKey.requestLog.enabled=...
 *
 *
 * 3.6. Collapser Properties
 * 设置请求合并的属性
 * 1. maxRequestsInBatch
 * 设置同时批量执行的请求的最大数量
 * 默认值：Integer.MAX_VALUE
 * // 设置所有实例的默认值
 * hystrix.collapser.default.maxRequestsInBatch=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.collapser.HystrixCollapserKey.maxRequestsInBatch=...
 *
 * 2. timerDelayInMilliseconds
 * 批量执行创建多久之后，再触发真正的请求
 * 默认值：10
 * // 设置所有实例的默认值
 * hystrix.collapser.default.timerDelayInMilliseconds=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.collapser.HystrixCollapserKey.timerDelayInMilliseconds=...
 *
 * 3. requestCache.enabled
 * 是否对HystrixCollapser.execute() 和 HystrixCollapser.queue()开启请求缓存
 * 默认值：true
 * // 设置所有实例的默认值
 * hystrix.collapser.default.requestCache.enabled=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.collapser.HystrixCollapserKey.requestCache.enabled=...
 *
 * 3.7. Thread Pool Properties
 * 设置Hystrix Commands的线程池行为，大部分情况线程数量是10。
 * 线程池数量的计算公式如下：
 * 最高峰时每秒的请求数量 × 99%命令执行时间 + 喘息空间
 *
 * 设置线程池数量的主要原则是保持线程池越小越好，因为它是减轻负载并防止资源在延迟发生时被阻塞的主要工具
 * 1. coreSize
 * 设置线程池的core的大小
 * 默认值：10
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.coreSize=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.coreSize=...
 *
 * 2. maximumSize
 * 设置最大的线程池的大小，只有设置allowMaximumSizeToDivergeFromCoreSize时，此值才起作用
 * 默认值：10
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.maximumSize=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.maximumSize=...
 *
 * 3. maxQueueSize
 * 设置最大的BlockingQueue队列的值。如果设置-1，则使用SynchronousQueue队列，如果设置正数，则使用LinkedBlockingQueue队列
 * 默认值：-1
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.maxQueueSize=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.maxQueueSize=...
 *
 * 4. queueSizeRejectionThreshold
 * 因为maxQueueSize值不能被动态修改，所有通过设置此值可以实现动态修改等待队列长度。即等待的队列的数量大于queueSizeRejectionThreshold时（但是没有达到maxQueueSize值），则开始拒绝后续的请求进入队列。
 * 如果设置-1，则属性不启作用
 * 默认值：5
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.queueSizeRejectionThreshold=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.queueSizeRejectionThreshold=...
 *
 * 5. keepAliveTimeMinutes
 * 设置线程多久没有服务后，需要释放（maximumSize-coreSize ）个线程
 * 默认值：1
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.keepAliveTimeMinutes=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.keepAliveTimeMinutes=...
 *
 *
 * 6. allowMaximumSizeToDivergeFromCoreSize
 * 设置allowMaximumSizeToDivergeFromCoreSize值为true时，maximumSize才有作用
 * 默认值：false
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize=....
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.allowMaximumSizeToDivergeFromCoreSize=...
 *
 * 7. metrics.rollingStats.timeInMilliseconds
 * 设置滚动窗口的时间
 * 默认值：10000
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.metrics.rollingStats.timeInMilliseconds=true
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolKey.metrics.rollingStats.timeInMilliseconds=true
 *
 * 8. metrics.rollingStats.numBuckets
 * 设置滚动静态窗口分成的桶的数量
 * 配置的值必须满足如下条件：
 * metrics.rollingStats.timeInMilliseconds % metrics.rollingStats.numBuckets == 0
 *
 * 默认值：10
 * 建议每个桶的时间长度大于100ms
 * // 设置所有实例的默认值
 * hystrix.threadpool.default.metrics.rollingStats.numBuckets=...
 * // 设置实例HystrixCommandKey的此属性值
 * hystrix.threadpool.HystrixThreadPoolProperties.metrics.rollingStats.numBuckets=...
 *
 */





/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月10日 20:49:00
 */
public class CustomrizedHystrixCommandProperties extends HystrixCommandProperties{


    protected CustomrizedHystrixCommandProperties(HystrixCommandKey key) {
        super(key);
    }

    protected CustomrizedHystrixCommandProperties(HystrixCommandKey key, Setter builder) {
        super(key, builder);
    }

    protected CustomrizedHystrixCommandProperties(HystrixCommandKey key, Setter builder, String propertyPrefix) {
        super(key, builder, propertyPrefix);
    }
}
