package com.miracle.cloud.listener;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RabbitListenerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitListenerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Async
    @org.springframework.amqp.rabbit.annotation.RabbitListener(bindings = {
            @QueueBinding(value = @Queue("trace_queue"), exchange = @Exchange(value = "trace", type = ExchangeTypes.FANOUT, durable = "true"))})
    public void consumerTraceMessage(Object o) {
        System.out.println("received message ==> " + o);
    }
}
