package com.ghtk.ws.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RabbitMqTestSetRunnerConsumerService implements CommandLineRunner {

    @Autowired
    private RabbitQueueService registry;

    @Override
    public void run(String... args) throws Exception {
        registry.addNewQueue("hungnv-queue11", "amq.topic", "hungnv-routingkey11");
    }

    @RabbitListener(id = "amq.topic", queues = {"hungnv-queue"}, concurrency = "2")
    public void receiver(Long testSetId) {
        log.info("received Message from rabbit : " + testSetId);
        try {
            log.info("completed " + testSetId + " task");
        } catch (Exception e) {
            log.error("Error on running test set", e);
        }
    }

//    @RabbitListener(id = "event", queues = {"custom-emp-queue-events"}) // create this queue in rabbitmq management, bound to amqp exchange
//    public void processQueueEvents(Message message) {
//        ((AbstractMessageListenerContainer) this.registry.getListenerContainer("process")).addQueueNames("hungnv");
//    }
}