package com.demo.mq.MessageBroker.controller;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

@Component
public class NotificationReader {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveNotification(String message) {
        System.out.println("Notification Listened: " + message );
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}