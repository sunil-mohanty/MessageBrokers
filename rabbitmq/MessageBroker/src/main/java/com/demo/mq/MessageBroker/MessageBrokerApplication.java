package com.demo.mq.MessageBroker;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.mq.MessageBroker.controller.NotificationReader;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;


@SpringBootApplication
public class MessageBrokerApplication {
	
	
	public static final String DIRECT_EXCHANGE = "amq.direct";

    private static final String QUEUE_NAME = "notificationQueue";
    
    public static final String ROUTING_KEY = "notificationReader";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    /**
     * If the queue is already created and binded to a exchange already via admin console, don't bind it again 
     * via the code. Unless enable the below Binding configuartion
     */
    /*
    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("liv_credits");
    }*/

    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(NotificationReader reader) {
        return new MessageListenerAdapter(reader, "receiveNotification");
    }

	

	public static void main(String[] args) {
		SpringApplication.run(MessageBrokerApplication.class, args);
	}
}
