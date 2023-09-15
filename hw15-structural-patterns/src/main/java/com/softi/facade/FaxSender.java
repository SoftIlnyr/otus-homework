package com.softi.facade;

public class FaxSender {

    public void sendMessage(String fax, String message) {
        System.out.printf("Sending message by fax number %s%n", fax);
        System.out.printf("Message %s%n", message);
    }

}
