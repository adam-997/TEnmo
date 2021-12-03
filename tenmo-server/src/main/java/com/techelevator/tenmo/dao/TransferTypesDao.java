package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferTypes;

public interface TransferTypesDao {

    TransferTypes getTransferTypeByDesc(String description);

    TransferTypes getTransferTypeById(int transferId);
}
