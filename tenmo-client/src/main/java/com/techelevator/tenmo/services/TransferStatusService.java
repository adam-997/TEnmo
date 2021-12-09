package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatuses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusService {

    private String baseUrl;
    private RestTemplate restTemplate;

    private TransferStatuses getTransferStatusByDesc (String description) {
        TransferStatuses transferStatuses = null;
        try {
            transferStatuses = restTemplate.getForObject(baseUrl + description, TransferStatuses.class);
        } catch (
                RestClientResponseException e) {
        }
        return transferStatuses;
    }

    private TransferStatuses getTransferStatusById (int transferStatusId) {
        TransferStatuses transferStatuses = null;
        try {
            transferStatuses = restTemplate.getForObject(baseUrl + transferStatusId, TransferStatuses.class);
        } catch (
                RestClientResponseException e) {
        }
        return transferStatuses;
    }

    private HttpEntity<TransferStatuses> makeEntity (TransferStatuses transferStatuses){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transferStatuses, headers);
    }
}
