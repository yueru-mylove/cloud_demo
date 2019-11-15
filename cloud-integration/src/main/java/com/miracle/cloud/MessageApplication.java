package com.miracle.cloud;

import com.miracle.cloud.bean.QueueMessage;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableIntegration
@SpringBootApplication
@EnableAsync
@EnableRabbit
@EnableScheduling
public class MessageApplication implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("str");
        rabbitTemplate.convertAndSend("trace", getMsg());
    }

    @Scheduled(fixedRate = 1000L)
    public void sendMsg() {
        rabbitTemplate.convertAndSend("trace", null, getMsg());
    }


    private QueueMessage<String> getMsg() {
        QueueMessage<String> queueMessage = new QueueMessage<>();
        queueMessage.setData("str");
        queueMessage.setLevel("INFO");
        queueMessage.setType("TRACE");
        queueMessage.setMessage("str");
        queueMessage.setTitle("TRACE_TITLE");
        return queueMessage;
    }


    @GetMapping("/trace/{word}")
    public String linked(@PathVariable("word") String word) {
        System.out.println(String.format("trace ==> [{%s}]", word));
        return word;
    }
}
