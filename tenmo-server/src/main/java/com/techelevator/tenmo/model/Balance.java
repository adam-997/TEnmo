package com.techelevator.tenmo.model;

import com.techelevator.tenmo.exceptions.InsufficientFunds;

public class Balance {

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void transferFunds(double amount) throws InsufficientFunds {
        double newBalance = balance - amount;
        if(balance > newBalance) {
            this.balance = newBalance;
        }
        else {
            throw new InsufficientFunds();
        }
    }

    public void receiveFunds(double amount) {
            this.balance = balance + amount;
        }




    }

