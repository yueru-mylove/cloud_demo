package com.voiceai.cloud.service;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月10日 21:55:00
 */
public interface HystrixStyleService {


    /**
     * Asynchronous Execution
     *
     * @return
     */
    String async();


    /**
     * Synchronous Execution
     *
     * @return
     */
    String sync();


    /**
     * Reactive Execution
     *
     * @return
     */
    String reactive();


    /**
     * fallback
     *
     * @return
     */
    String fallback();
}
