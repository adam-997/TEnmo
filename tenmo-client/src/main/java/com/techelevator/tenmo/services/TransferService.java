package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService{

    private final String baseUrl = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();


    public Transfer getTransferByTransferId(AuthenticatedUser authenticatedUser, int transferId) {
        HttpEntity entity = makeEntity(authenticatedUser);
        Transfer transfer = null;
        try {
            transfer = restTemplate.exchange(baseUrl + "/transfers/" + transferId, HttpMethod.GET , entity ,Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Server network issue. Error code: " + e);
        }
        return transfer;
    }

    public Transfer[] getTransfersByUserId (AuthenticatedUser authenticatedUser, int userId) {
        Transfer[] transfer = null;
        HttpEntity entity = makeEntity(authenticatedUser);
        try {
            transfer = restTemplate.exchange(baseUrl + "/transfers/" + userId, HttpMethod.GET , entity ,Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Server network issue. Error code: " + e);

        }
        return transfer;
    }

    public Transfer[] getAllTransfers (AuthenticatedUser authenticatedUser) {
        Transfer[] transfer = null;
        HttpEntity entity = makeEntity(authenticatedUser);
        try {
            transfer = restTemplate.exchange(baseUrl + "/transfers", HttpMethod.GET , entity ,Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Server network issue. Error code: " + e);
        }
        return transfer;
    }

//    public Transfer[] getPendingTransfersByUserId ( AuthenticatedUser authenticatedUser, int userId){
//        Transfer[] transfer = null;
//        HttpEntity entity = makeEntity(authenticatedUser);
//        try {
//            transfer = restTemplate.exchange(baseUrl + "/transfers/" + userId, HttpMethod.GET , entity ,Transfer[].class).getBody();
//        } catch (RestClientResponseException e) {
//            System.out.println("Server network issue. Error code: " + e);
//
//        }
//        return transfer;
//    }


    public void updateTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpEntity entity = makeTransferEntity(authenticatedUser, transfer);
        restTemplate.exchange(baseUrl + "/transfers/" + transfer.getTransferId(), HttpMethod.PUT, entity, Transfer.class);
    }


    public void makeTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpEntity entity = makeTransferEntity(authenticatedUser, transfer);
        restTemplate.exchange(baseUrl + "/transfers/" + transfer.getTransferId(), HttpMethod.POST, entity, Transfer.class);
    }



    private HttpEntity makeEntity (AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());

        return new HttpEntity<>(headers);
    }
    private HttpEntity<Transfer> makeTransferEntity (AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(transfer, headers);
        return entity;
    }
}

