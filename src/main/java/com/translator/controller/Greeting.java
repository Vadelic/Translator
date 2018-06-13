package com.translator.controller;

public class Greeting {
    private static final String template = "Hello, %s!";

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = String.format(template, content);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}