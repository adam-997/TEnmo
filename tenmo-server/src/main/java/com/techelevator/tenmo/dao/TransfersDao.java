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

   // private int getMaxID() {
    //    int maxID = 0;
  //      for (Transfer r : getAllTransfers()) {
    //        if (r.getTransferId() > maxID) {
   //             maxID = r.getTransferId();
        //    }
//   }
   //     return maxID;
   // }
   // private int getMaxIdPlusOne() {
   //     return getMaxID() + 1;
    }



//}
