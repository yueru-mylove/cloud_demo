package com.miracle.cloud.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * MQ消息格式
 * Created by cheney on 2018/5/4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueMessage<T> implements Serializable{

    private static final long serialVersionUID=1L;

    private String type;

    private String level;
    private String title;
    private String message;
    // 业务数据，复杂格式
    private T data;
}

