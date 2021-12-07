package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    private Account getAccountByUserId(int userId) {
        Account account = null;
        try {
            account = restTemplate.getForObject(baseUrl + userId, Account.class);
        } catch (RestClientResponseException e) {
        }
        return account;
    }

    private Account getAccountByAccountId(int accountId) {
        Account account = null;
        try {
            account = restTemplate.getForObject(baseUrl + accountId, Account.class);
        } catch (RestClientResponseException e) {
        }
        return account;
    }

    private Balance getBalanceByAccountId(int accountId) {
        Balance balance = null;
        try {
            balance = restTemplate.getForObject(baseUrl + balance, Balance.class);
        } catch (RestClientResponseException e) {
        }
        return balance;
    }



    private HttpEntity<Account> makeEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(account, headers);

}
}
