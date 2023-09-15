package com.softi.cash_machine;

import lombok.Getter;

public enum BanknoteType {
    RUB_5000(5000),
    RUB_2000(2000),
    RUB_1000(1000),
    RUB_500(500),
    RUB_200(200),
    RUB_100(100),
    RUB_50(50);

    @Getter
    private int value;
    
    BanknoteType(int value) {
        this.value = value;
    }
}
