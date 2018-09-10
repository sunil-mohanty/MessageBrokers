package com.demo.mq.MessageBroker.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mq.MessageBroker.MessageBrokerApplication;

@RestController
@RequestMapping("/publish")
public class PublishController {

	@Autowired
    private RabbitTemplate rabbitTemplate;
    
	
	@GetMapping("/notification")
	public String putNotification(@RequestParam("notificationMsg") String notificationMsg) throws InterruptedException {
		rabbitTemplate.convertAndSend(MessageBrokerApplication.DIRECT_EXCHANGE, MessageBrokerApplication.ROUTING_KEY, notificationMsg);
        return notificationMsg;
	}

}
