package com.softi.facade;

public class EmailSender {

    public void sendMessage(String email, String message) {
        System.out.printf("Sending email to email %s%n", email);
        System.out.printf("Message %s%n", message);
    }
}
