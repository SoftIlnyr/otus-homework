package com.softi.composite;

import java.util.Map;
import java.util.TreeMap;

public class Folder extends Node {

    private Map<String, Node> childNodes = new TreeMap<>();

    public Folder(String name) {
        this.name = name;
    }

    @Override
    void addChild(Node node) {
        childNodes.put(node.getName(), node);
    }

    @Override
    void removeChild(Node node) {
        childNodes.remove(node.getName());
    }

    @Override
    Node getChild(String name) {
        return childNodes.get(name);
    }

    @Override
    void getContent() {
        childNodes.keySet().forEach(System.out::println);
    }
}
