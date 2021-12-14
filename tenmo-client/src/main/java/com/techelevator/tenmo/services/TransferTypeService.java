package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {

    private final String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();


    public TransferTypes getTransferType(AuthenticatedUser authenticatedUser, String description) {
        TransferTypes transferType = new TransferTypes();

        try {
            String url = baseUrl + "/transfertype/filter?description=" + description;
            transferType = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferTypes.class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Could not complete request. Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }
        return transferType;
    }

    public TransferTypes getTransferTypeFromId(AuthenticatedUser authenticatedUser, int transferTypeId) {
        TransferTypes transferType = new TransferTypes();

        try {
            String url = baseUrl + "/transfertype/" + transferTypeId;
            transferType = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferTypes.class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Could not complete request. Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Could not complete request due to server network issue. Please try again.");
        }


        return transferType;
    }

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}