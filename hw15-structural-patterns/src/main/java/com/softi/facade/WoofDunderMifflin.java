package com.softi.facade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WoofDunderMifflin implements WoofServiceFacade {

    private EmailSender emailSender;
    private SMSSender smsSender;
    private FaxSender faxSender;

    @Override
    public void sendWoof(Contact contact, String message) {
        if (contact == null) {
            return;
        }
        emailSender.sendMessage(contact.getEmail(), message);
        smsSender.sendMessage(contact.getPhoneNumber(), message);
        faxSender.sendMessage(contact.getFax(), message);
    }
}
