package com.devizones.commons.event;

import org.springframework.context.ApplicationEventPublisher;


public abstract class Events {
    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void publish(Object event) {
        if(publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
