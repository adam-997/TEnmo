package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransfersDao {

    Void createTransfer(Transfer transfer);

    Void updateTransfer(Transfer transfer);

    List<Transfer> getTransferByTransferId(int transferId);

    List<Transfer> getAllTransfers();

    List<Transfer> getTransferByUserId(int userId);

    List<Transfer> getPendingTransfersByUserId(int userId);


}
