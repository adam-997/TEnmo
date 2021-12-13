package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatuses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusService {

    private final String baseUrl = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    public TransferStatuses getTransferStatusByDesc (AuthenticatedUser authenticatedUser, String description) {
        TransferStatuses transferStatuses = new TransferStatuses();
        HttpEntity<TransferStatuses> entity = makeEntity(authenticatedUser);
        try {
            transferStatuses = restTemplate.exchange(baseUrl + "transferstatus/" + description, HttpMethod.GET , entity ,TransferStatuses.class).getBody();

        } catch (
                RestClientResponseException e) {
        }
        return transferStatuses;
    }
    public TransferStatuses getTransferStatusById (AuthenticatedUser authenticatedUser ,int transferStatusId) {
        TransferStatuses transferStatuses = new TransferStatuses();
        HttpEntity<TransferStatuses> entity = makeEntity(authenticatedUser);
        try {
            transferStatuses = restTemplate.exchange(baseUrl + "transferstatus/" + transferStatusId, HttpMethod.GET , entity ,TransferStatuses.class).getBody();
        } catch (
                RestClientResponseException e) {
        }
        return transferStatuses;
    }

    private HttpEntity<TransferStatuses>  makeEntity (AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());

        return new HttpEntity<>(headers);
    }
}
