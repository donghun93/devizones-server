package com.devizones.commons.event;

import lombok.Getter;

@Getter
public abstract class DomainEvent {
    private final long timestamp;

    public DomainEvent() {
        this.timestamp = System.currentTimeMillis();
    }
}
