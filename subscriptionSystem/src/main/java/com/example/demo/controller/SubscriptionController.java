package com.example.demo.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user}")
    private String USER;
    @Value("${password}")
    private String PASSWORD;
    @Value("${url.subscription}")
    private String SUBSCRIPTION_URL;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getSubscriptions() {
        try {
            HttpHeaders headers = createHeaders(USER, PASSWORD);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(SUBSCRIPTION_URL,
                    HttpMethod.GET, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public String createSubscription(String email, String firstName, String gender,
                                     String dateOfBirth,
                                     boolean consent, String newsletterId) {
        HttpHeaders headers = createHeaders(USER, PASSWORD);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("email", email);
        map.add("firstName", firstName);
        map.add("gender", gender);
        map.add("dateOfBirth", dateOfBirth);
        map.add("consent", consent);
        map.add("newsletterId", newsletterId);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response
                = restTemplate.postForEntity(SUBSCRIPTION_URL, request, String.class);
        return response.getBody();
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
