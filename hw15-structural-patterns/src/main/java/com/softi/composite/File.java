package com.softi.composite;

public class File extends Node {

    private String content;
    
    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    void getContent() {
        System.out.printf("File %s", name);
        System.out.println(content);
    }

    public void setContent(String content) {
        this.content = content;
    }
}
