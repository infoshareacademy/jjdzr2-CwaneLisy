package com.infoshare.service;

public enum EventType {
    add ("ADD"),
    update ("UPDATE");

    public String eventType;

    EventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

}
