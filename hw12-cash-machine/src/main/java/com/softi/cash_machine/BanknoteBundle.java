package com.softi.cash_machine;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BanknoteBundle {

    @Getter
    Map<BanknoteType, Integer> banknoteMap;

    public BanknoteBundle() {
        banknoteMap = new HashMap<>();
        for (BanknoteType banknoteType : BanknoteType.values()) {
            banknoteMap.put(banknoteType, 0);
        }
    }

    public BanknoteBundle(BanknoteBundle banknoteBundle) {
        this.banknoteMap = new HashMap<>(banknoteBundle.getBanknoteMap());
    }
    
    public int getSum() {
        int result = 0;
        for (BanknoteType banknoteType : banknoteMap.keySet()) {
            result += banknoteType.getValue() * banknoteMap.get(banknoteType);
        }
        return result;
    }

    public void addBanknotes(BanknoteType banknoteType, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Значение должно быть больше нуля");
        }
        banknoteMap.put(banknoteType, banknoteMap.get(banknoteType) + amount);
    }
    
}
