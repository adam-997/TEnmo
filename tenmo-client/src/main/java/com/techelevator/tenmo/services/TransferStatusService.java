package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatuses;
import com.techelevator.tenmo.model.TransferTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusService {
    private final String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();


    public TransferStatuses getTransferStatus(AuthenticatedUser authenticatedUser, String description) {
        TransferStatuses transferStatus = new TransferStatuses();
        try {
            String url = baseUrl + "/transferstatus/filter?description=" + description;
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferStatuses.class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Could not complete request. Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }
        return transferStatus;
    }

    public TransferStatuses getTransferStatusById(AuthenticatedUser authenticatedUser, int transferStatusId) {
        TransferStatuses transferStatus = new TransferStatuses();
        try {
            String url = baseUrl + "/transferstatus/" + transferStatusId;
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferStatuses.class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Could not complete request. Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }

        return transferStatus;
    }

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

}