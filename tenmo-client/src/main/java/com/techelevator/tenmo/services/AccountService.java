package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public Account getAccountByUserId(int userId) {
        Account account = null;
        try {
            account = restTemplate.getForObject(baseUrl + userId, Account.class);
        } catch (RestClientResponseException e) {
        }
        return account;
    }

    public Account getAccountByAccountId(int accountId) {
        Account account = null;
        try {
            account = restTemplate.getForObject(baseUrl + accountId, Account.class);
        } catch (RestClientResponseException e) {
        }
        return account;
    }

    public Balance getBalanceByUser(AuthenticatedUser authenticatedUser) {
        Balance balance = null;
        HttpEntity entity = makeAuthEntity(authenticatedUser);
        try {
            ResponseEntity<Balance> response = restTemplate.exchange(baseUrl + "balance" , HttpMethod.GET, entity, Balance.class);
            balance = response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Request unsuccessful. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Server network error. code: " + e.getMessage());
        }
        return balance;
    }



    private HttpEntity<Account> makeEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(account, headers);

}
    private HttpEntity<Void> makeAuthEntity(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }
}
