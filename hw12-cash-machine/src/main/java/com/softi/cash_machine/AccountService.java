package com.softi.cash_machine;

public interface AccountService {

    int getBalance(int accountId);

    void checkAccountBalance(int accountId, int sum);

    void increaseBalance(int accountId, int sum);

    void descreaseBalance(int accountId, int sum);
}
