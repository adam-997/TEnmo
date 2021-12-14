package com.techelevator.tenmo.exceptions;

public class UserChoiceException extends Exception {
    public UserChoiceException(){
        super("You cannot send money to yourself. Please choose another user.");
    }
}