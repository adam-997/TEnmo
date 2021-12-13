package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private final String baseUrl = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();


    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, int userId) {
        Account account = new Account();
        HttpEntity entity = makeAuthEntity(authenticatedUser);
        try {
            account = restTemplate.exchange(baseUrl + "/account/user/" + userId, HttpMethod.GET , entity , Account.class).getBody();
        } catch (RestClientResponseException e) {
        }
        return account;
    }
    public Account getAccountByAccountId(AuthenticatedUser authenticatedUser, int accountId) {
        Account account = new Account();
        HttpEntity entity = makeAuthEntity(authenticatedUser);
        try {
            account = restTemplate.exchange(baseUrl + "/account/" + accountId, HttpMethod.GET , entity , Account.class).getBody();
        } catch (RestClientResponseException e) {
        }
        return account;
    }


    public Balance getBalanceByUser(AuthenticatedUser authenticatedUser) {
        Balance balance = new Balance();
        HttpEntity entity = makeAuthEntity(authenticatedUser);
        try {
            ResponseEntity<Balance> response = restTemplate.exchange(baseUrl + "/account/balance" , HttpMethod.GET, entity, Balance.class);
            balance = response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Request unsuccessful. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Server network error. Code: " + e.getMessage());
        }
        return balance;
    }




    private HttpEntity makeAuthEntity(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }
}
