package com.softi.cash_machine;

import java.util.Map;

public interface CashMachine {

    void putMoney(int accountId, Map<BanknoteType, Integer> banknotes);

    Map<BanknoteType, Integer> getMoney(int accountId, int sum);

    int getBalance(int accountId);

}
