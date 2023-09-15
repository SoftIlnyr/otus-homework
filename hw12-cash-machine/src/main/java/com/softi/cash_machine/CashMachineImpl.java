package com.softi.cash_machine;

import lombok.Setter;

import java.util.Map;

public class CashMachineImpl implements CashMachine {

    @Setter
    private AccountService accountService;
    @Setter
    private BanknoteService banknoteService;

    @Override
    public void putMoney(int accountId, Map<BanknoteType, Integer> banknotes) {
        int sum = banknoteService.putMoney(banknotes);
        accountService.increaseBalance(accountId, sum);
    }

    @Override
    public Map<BanknoteType, Integer> getMoney(int accountId, int sum) {
        accountService.checkAccountBalance(accountId, sum);
        Map<BanknoteType, Integer> result = banknoteService.getBanknotes(sum);
        accountService.descreaseBalance(accountId, sum);
        return result;
    }

    @Override
    public int getBalance(int accountId) {
        return accountService.getBalance(accountId);
    }
}
