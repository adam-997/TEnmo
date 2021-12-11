package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    AccountDao accountDao;
    @Autowired
    TransfersDao transfersDao;
    @Autowired
    TransferTypesDao transferTypesDao;
    @Autowired
    TransferStatusesDao transferStatusesDao;
    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public Balance getBalanceByUser( Principal principal) {

        return accountDao.getBalanceByUsername(principal.getName());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    @RequestMapping(path = "/transfers/{userId}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(@PathVariable int userId){
        return transfersDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfersByTransferId(@PathVariable int transferId){
        return transfersDao.getTransferByTransferId(transferId);
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
    public Account getAccountByAccountId(@PathVariable int accountId) {
        return accountDao.getAccountByAccountId(accountId);
    }

    @RequestMapping(path = "/account/{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId) {
        return accountDao.getAccountByUserId(userId);
    }

    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username){
        return userDao.findByUsername(username);
    }

    @RequestMapping(path = "/transferTypes/{id}", method = RequestMethod.GET)
    public TransferTypes getTransferDescById(@PathVariable int transferId){
        return transferTypesDao.getTransferTypeById(transferId);
    }
    @RequestMapping(path = "/transferStatus/{id}", method = RequestMethod.GET)
    public TransferStatuses getTransferStatusById(@PathVariable int transferStatusId){
        return transferStatusesDao.getTransferStatusById(transferStatusId);
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllPendingTransfers(@PathVariable int userId){
        return transfersDao.getPendingTransfersByUserId(userId);
    }








}