package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatuses;

import java.util.List;

public interface TransferStatusesDao {

    TransferStatuses getTransferStatusByDesc(String description);

    TransferStatuses getTransferStatusById(int transferStatusId);
}
