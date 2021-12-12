package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService {

    private RestTemplate restTemplate;
    private String baseUrl = "http//localhost:8080/";

    public UserService(){
        this.restTemplate = new RestTemplate();

    }

    public User[] getAllUsers(AuthenticatedUser authenticatedUser){
        User[] users = null;
        HttpEntity<User> entity = makeAuthEntity(authenticatedUser);
        try {
            users = restTemplate.exchange(baseUrl + "users", HttpMethod.GET , entity ,User[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Server network issue. Error code: " + e);
        }
        return users;
    }

    public User getUserByUserId (AuthenticatedUser authenticatedUser, int userId){
        User user = null;
        HttpEntity<User> entity = makeAuthEntity(authenticatedUser);
        try {
            user = restTemplate.exchange(baseUrl + "user", HttpMethod.GET , entity , User.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Server network issue. Error code: " + e);

        }
        return user;
    }


    private HttpEntity makeAuthEntity(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }

}
