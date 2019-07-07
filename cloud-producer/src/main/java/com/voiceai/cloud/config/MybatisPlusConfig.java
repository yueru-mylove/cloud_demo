package com.voiceai.cloud.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 01:30:00
 */
@Configuration
@MapperScan("com.voiceai.cloud.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * sql注入器  逻辑删除插件
     * @return
     */
    @Bean
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }

    /**
     * sql性能分析插件，输出sql语句及所需时间
     * @return
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
    /**
     * 乐观锁插件
     * @return
     */
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }
}
