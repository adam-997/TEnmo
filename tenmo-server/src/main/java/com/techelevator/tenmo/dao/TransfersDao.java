package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransfersDao {

    void createTransfer(Transfer transfer);

    void updateTransfer(Transfer transfer);

    Transfer getTransferByTransferId(int transferId);

    List<Transfer> getAllTransfers();

    List<Transfer> getTransfersByUserId(int userId);

    List<Transfer> getPendingTransfersByUserId(int userId);


}
