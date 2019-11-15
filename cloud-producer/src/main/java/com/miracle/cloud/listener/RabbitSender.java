package com.miracle.cloud.listener;

import com.miracle.cloud.bean.QueueMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        QueueMessage<Object> queueMessage = new QueueMessage<>();
        queueMessage.setData(message);
        queueMessage.setLevel("INFO");
        queueMessage.setTitle("trace_test");
        queueMessage.setType("trace");
        rabbitTemplate.convertAndSend("trace", queueMessage);
    }

}
