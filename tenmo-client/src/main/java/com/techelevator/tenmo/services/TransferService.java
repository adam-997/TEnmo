package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String baseUrl;
    private RestTemplate restTemplate;

    private Transfer getAccountByTransferId(int transferId) {
        Transfer transfer = null;
        try {
            transfer = restTemplate.getForObject(baseUrl + transferId, Transfer.class);
        } catch (RestClientResponseException e) {
            return transfer;
        }

        private Transfer getTransferByUserId ( int userId){
            Transfer transfer = null;
            try {
                transfer = restTemplate.getForObject(baseUrl + userId, Transfer.class);
            } catch (RestClientResponseException e) {
            }
            return transfer;
        }

        private Transfer getAllTransfers () {
            Transfer transfer = null;
            try {
                transfer = restTemplate.getForObject(baseUrl, Transfer.class);
            } catch (RestClientResponseException e) {
            }
            return transfer;
        }

        private Transfer getPendingTransfersByUserId ( int userId){
            Transfer transfer = null;
            try {
                transfer = restTemplate.getForObject(baseUrl + userId, Transfer.class);
            } catch (RestClientResponseException e) {
            }
            return transfer;
        }

        private void createTransfer (Transfer transfer){
            try {
                transfer = restTemplate.postForObject(baseUrl, transfer, Transfer.class);
            } catch (RestClientResponseException e) {

            }
        }
        private void updateTransfer (Transfer transfer){
            try {
                restTemplate.put(baseUrl, transfer, Transfer.class);
            } catch (RestClientResponseException e) {

            }
        }


        private HttpEntity<Transfer> makeEntity (Transfer transfer){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new HttpEntity<>(transfer, headers);
        }
    }
}
