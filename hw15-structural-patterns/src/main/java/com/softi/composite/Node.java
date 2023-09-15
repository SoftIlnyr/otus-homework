package com.softi.composite;

public abstract class Node {

    protected String name;

    void addChild(Node node) {
        throw new UnsupportedOperationException();
    }

    void removeChild(Node node) {
        throw new UnsupportedOperationException();
    }

    Node getChild(String name) {
        throw new UnsupportedOperationException();
    }

    void getContent() {
        throw new UnsupportedOperationException();
    }
    
    public String getName() {
        return name;
    }
}
