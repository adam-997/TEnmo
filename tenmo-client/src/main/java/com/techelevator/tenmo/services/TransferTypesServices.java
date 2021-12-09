package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatuses;
import com.techelevator.tenmo.model.TransferTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypesServices {
    private String baseUrl;
    private RestTemplate restTemplate;

    private TransferTypes getTransferTypeByDesc (String description) {
        TransferTypes transferTypes = null;
        try {
            transferTypes = restTemplate.getForObject(baseUrl + description, TransferTypes.class);
        } catch (
                RestClientResponseException e) {
        }
        return transferTypes;
    }

    private TransferTypes getTransferTypeByTransferId (int transferId) {
        TransferTypes transferTypes = null;
        try {
            transferTypes = restTemplate.getForObject(baseUrl + transferId, TransferTypes.class);
        } catch (
                RestClientResponseException e) {
        }
        return transferTypes;
    }

    private HttpEntity<TransferTypes> makeEntity (TransferTypes transferTypes){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transferTypes, headers);
    }
}
