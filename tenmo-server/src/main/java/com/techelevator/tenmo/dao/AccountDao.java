package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

public interface AccountDao {

    Account getAccountByUserId(int userId);

    Account getAccountByAccountId(int accountId);

    Balance getBalanceByUsername(String username);


    void updateAccount(Account accountUpdated);



}
