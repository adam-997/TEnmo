package com.techelevator.tenmo.model;

public class TransferStatuses {

    private int transferStatusId;
    private String description;

    public TransferStatuses(int transferStatusId, String description) {
        this.transferStatusId = transferStatusId;
        this.description = description;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
