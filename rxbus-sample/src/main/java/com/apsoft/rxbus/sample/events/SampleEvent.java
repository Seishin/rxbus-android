package com.apsoft.rxbus.sample.events;

public class SampleEvent {

    private String text;

    public SampleEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
