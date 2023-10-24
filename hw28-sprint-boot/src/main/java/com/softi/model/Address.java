package com.softi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "address")
public class Address {

    @Id
    private final Long id;

    private final String street;

    private final Long clientId;

    public Address(String street, Long clientId) {
        this(null, street, clientId);
    }


    @PersistenceCreator
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }
}
