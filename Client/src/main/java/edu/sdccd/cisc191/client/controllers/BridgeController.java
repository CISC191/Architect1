package edu.sdccd.cisc191.client.controllers;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sdccd.cisc191.client.errors.FrontendException;
import edu.sdccd.cisc191.client.errors.InvalidPayloadException;
import edu.sdccd.cisc191.client.errors.UsernameTakenException;
import edu.sdccd.cisc191.client.models.LoginForm;
import edu.sdccd.cisc191.client.models.RegisterForm;
import edu.sdccd.cisc191.common.cryptography.Hasher;
import edu.sdccd.cisc191.common.entities.DataFetcher;
import edu.sdccd.cisc191.common.entities.User;

// bridging client with backend api

@RestController
@RequestMapping("/api")
public class BridgeController implements DataFetcher {
    private final String baseURL = backendEndpointURL + userEndpointURL;
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm form) throws FrontendException {
        String passwordHash = Hasher.hashNewPassword(form.getPassword());

        User newUser = new User(form.getEmail(), form.getUsername(), form.getNickname(), passwordHash, User.Role.Regular);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                baseURL + "/add", 
                HttpMethod.POST, 
                new HttpEntity<User>(newUser), 
                new ParameterizedTypeReference<>() {}
            );

            System.out.println(response.getStatusCode());

            if(response.getStatusCode().is4xxClientError()) {
                throw new UsernameTakenException();
            }
        } catch(ClassCastException e) {
            System.err.println(e.toString());
            throw new InvalidPayloadException();
        } catch(RestClientException e) {
            return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<String>("Account created successfully", null, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm form) throws InvalidPayloadException {
        System.out.println(form.toString());
        System.out.println(form.getPassword().isEmpty());
        if(form.getUsername().isEmpty() || form.getPassword().isEmpty()) {
            throw new InvalidPayloadException();
        }

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                baseURL + "/name/" + form.getUsername(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
            );
            
            if(response.getStatusCode().equals(HttpStatus.OK)) {
                return new ResponseEntity<String>("Invalid username or password", null, 0);
            }
        } catch(ClassCastException e) {
            System.err.println(e.toString());
            throw new InvalidPayloadException();
        } catch(RestClientException e) {
            return new ResponseEntity<String>(e.getMessage(), null, HttpStatus.FORBIDDEN);
        }

        try {
            User user = new ObjectMapper().readValue(response.getBody(), User.class);
            if(Hasher.isCorrectPassword(user, form.getPassword())) {
                return new ResponseEntity<String>("logged in", null, HttpStatus.OK);
            }
        } catch(JsonProcessingException e) {
            return new ResponseEntity<String>("User values are corrupted in database.", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<String>("invalid username or password", null, HttpStatus.FORBIDDEN);
    }
}