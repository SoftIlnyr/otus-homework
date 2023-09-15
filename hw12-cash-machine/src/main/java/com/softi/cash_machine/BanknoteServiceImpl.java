package com.softi.cash_machine;

import lombok.Setter;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class BanknoteServiceImpl implements BanknoteService {

    @Setter
    private Map<BanknoteType, Integer> banknoteCellMap;
    @Setter
    private BanknoteSearchService banknoteSearchService;

    public BanknoteServiceImpl() {
        banknoteCellMap = new TreeMap<>(Comparator.comparing(BanknoteType::getValue).reversed());
        for (BanknoteType banknoteType : BanknoteType.values()) {
            banknoteCellMap.put(banknoteType, 0);
        }
    }

    @Override
    public int putMoney(Map<BanknoteType, Integer> banknotes) {
        int sum = 0;

        for (BanknoteType banknoteType : banknotes.keySet()) {
            sum += banknoteType.getValue() * banknotes.get(banknoteType);
            banknoteCellMap.put(banknoteType, banknoteCellMap.get(banknoteType) + banknotes.get(banknoteType));
        }

        return sum;
    }

    @Override
    public Map<BanknoteType, Integer> getBanknotes(int sum) {

        Map<BanknoteType, Integer> availableVariantMap = banknoteSearchService.searchBanknoteBundle(banknoteCellMap, sum);

        if (availableVariantMap.isEmpty()) {
            throw new IllegalStateException("Невозможно выдать данную сумму");
        }

        return availableVariantMap;
    }
}
