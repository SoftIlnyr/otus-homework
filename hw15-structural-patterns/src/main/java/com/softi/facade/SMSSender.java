package com.softi.facade;

public class SMSSender {
    
    public void sendMessage(String phoneNumber, String message) {
        System.out.printf("Sending message by SMS to the number %s%n", phoneNumber);
        System.out.printf("Message %s%n", message);
    }

}
