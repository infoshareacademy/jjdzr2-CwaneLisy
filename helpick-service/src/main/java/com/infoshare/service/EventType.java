package com.infoshare.service;

public enum EventType {
    add ("ADD"),
    update ("UPDATE");

    public String type;

    EventType(String type) {
        this.type = type;
    }

    public String getEventType() {
        return type;
    }
}
