package com.softi.facade;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Contact {

    private String name;
    private String email;
    private String phoneNumber;
    private String fax;

    @Builder
    public Contact(String name, String email, String phoneNumber, String fax) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
    }

}
