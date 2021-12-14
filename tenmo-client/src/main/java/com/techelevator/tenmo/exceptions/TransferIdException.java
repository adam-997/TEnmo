package com.techelevator.tenmo.exceptions;

public class TransferIdException extends Exception {

    public TransferIdException() {
        super("Transfer Id is invalid, please enter another Id.");
    }
}