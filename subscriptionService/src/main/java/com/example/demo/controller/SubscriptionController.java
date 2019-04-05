package com.example.demo.controller;

import com.example.demo.entity.Subscription;
import com.example.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String addSubscription(String email, String firstName, String gender,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                                  boolean consent, String newsletterId) {
        try {
            Subscription object = subscriptionService.createSubscription(email, firstName, gender,
                    dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    consent, newsletterId);
            makeCallToEmailService(email, firstName);
            return "id : " + object.getId().toString();
        }catch(Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void makeCallToEmailService(String email, String firstName) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("recipient", email);
            map.add("name", firstName);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<String> response
                    = restTemplate.postForEntity("http://email:8090/email", request, String.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Subscription> getSubscriptions() {
        try {
            return subscriptionService.getSubscriptions();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
