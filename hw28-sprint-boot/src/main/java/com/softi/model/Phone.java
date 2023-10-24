package com.softi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "phone")
public class Phone {

    @Id
    @Column(value = "id")
    private final Long id;

    @Column(value = "number")
    private final String number;

    @Column(value = "client_id")
    private final Long clientId;

    public Phone(String number, Long clientId) {
        this(null, number, clientId);
    }

    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }
}
