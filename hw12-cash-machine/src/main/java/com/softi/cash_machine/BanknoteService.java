package com.softi.cash_machine;

import java.util.Map;

public interface BanknoteService {

    int putMoney(Map<BanknoteType, Integer> banknotes);

    Map<BanknoteType, Integer> getBanknotes(int sum);
}
