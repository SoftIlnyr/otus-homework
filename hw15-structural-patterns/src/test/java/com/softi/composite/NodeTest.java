package com.softi.composite;

import org.junit.jupiter.api.Test;

class NodeTest {
    
    @Test
    public void testNodes() {
        Node rootFolder = new Folder("root");
        Node metadata = new File("metadata", "Some Meta");

        Node programFiles = new Folder("Program Files");
        Node javaFolder = new Folder("Java");
        Node java8Folder = new Folder("Java 8");
        Node java8Jar = new File("jre", "jre compile");
        Node intellijIdea = new Folder("IntellijIdea");
        Node ideaProjects = new Folder("IdeaProjects");
        
        rootFolder.addChild(metadata);
        rootFolder.addChild(programFiles);
        programFiles.addChild(javaFolder);
        programFiles.addChild(intellijIdea);
        javaFolder.addChild(java8Folder);
        java8Folder.addChild(java8Jar);
        intellijIdea.addChild(ideaProjects);
        
        rootFolder.getContent();

        intellijIdea.getContent();

        programFiles.getContent();
        programFiles.removeChild(javaFolder);
        programFiles.getContent();
        
        
    }

}