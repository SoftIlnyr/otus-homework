package com.softi.facade;

import com.softi.facade.Contact.ContactBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WoofDunderMifflinTest {

    private WoofDunderMifflin woofDunderMifflin;

    @BeforeEach
    void setUp() {
        woofDunderMifflin = new WoofDunderMifflin(new EmailSender(), new SMSSender(), new FaxSender());
    }

    @Test
    public void testSendWoof() {
        Contact user1 = new ContactBuilder().name("user1").email("example1@mail.com").phoneNumber("+1000").fax("101").build();
        
        woofDunderMifflin.sendWoof(user1, "Woof!!");
    }

}