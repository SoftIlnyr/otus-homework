package com.softi.cash_machine;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements AccountService {

    Map<Integer, Integer> accountDatabase;

    public AccountServiceImpl() {
        accountDatabase = new HashMap<>();
    }

    @Override
    public int getBalance(int accountId) {
        checkAccountExists(accountId);
        return accountDatabase.get(accountId);
    }

    @Override
    public void increaseBalance(int accountId, int sum) {
        checkAccountExists(accountId);
        accountDatabase.put(accountId, accountDatabase.get(accountId) + sum);
        
    }

    @Override
    public void descreaseBalance(int accountId, int sum) {
        checkAccountExists(accountId);
        checkAccountBalance(accountId, sum);
        accountDatabase.put(accountId, accountDatabase.get(accountId) + sum);
    }

    @Override
    public void checkAccountBalance(int accountId, int sum) {
        checkAccountExists(accountId);
        if (accountDatabase.get(accountId) < sum) {
            throw new IllegalStateException("Недостаточно средств на счете " + accountId);
        }
    }

    private void checkAccountExists(int accountId) {
        if (!accountDatabase.containsKey(accountId)) {
            throw new IllegalArgumentException("Нет пользователя с id " + accountId);
        }
    }
}
