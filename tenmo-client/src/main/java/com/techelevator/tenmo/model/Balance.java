package com.techelevator.tenmo.model;

public class Balance {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void transferFunds(double amount){
        double newBalance = balance - amount;
        if(balance > newBalance) {
            this.balance = newBalance;
        }
    }

    public void receiveFunds(double amount) {
        this.balance = balance + amount;
    }

}
