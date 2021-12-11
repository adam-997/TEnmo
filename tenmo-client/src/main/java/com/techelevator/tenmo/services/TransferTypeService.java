package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {
    private final String baseUrl = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    public TransferTypes getTransferTypeByDesc (AuthenticatedUser authenticatedUser, String description) {
        TransferTypes transferTypes = null;
        HttpEntity<TransferTypes> entity = makeEntity(authenticatedUser);
        try {
            transferTypes = restTemplate.exchange(baseUrl + "/transfertype/filter?description=" + description, HttpMethod.GET , entity ,TransferTypes.class).getBody();

        } catch (
                RestClientResponseException e) {
        }
        return transferTypes;
    }


    public TransferTypes getTransferTypeByTransferId (AuthenticatedUser authenticatedUser, int transferId ) {
        TransferTypes transferTypes = null;
        HttpEntity<TransferTypes> entity = makeEntity(authenticatedUser);
        try {
            transferTypes = restTemplate.exchange(baseUrl + "transfertype/" + transferId, HttpMethod.GET , entity ,TransferTypes.class).getBody();
        } catch (
                RestClientResponseException e) {
        }
        return transferTypes;
    }

    private HttpEntity<TransferTypes>  makeEntity (AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());

        return new HttpEntity<>(headers);
    }
}