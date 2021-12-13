package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransfersController {


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



}
