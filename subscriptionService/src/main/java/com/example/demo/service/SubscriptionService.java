package com.example.demo.service;

import com.example.demo.entity.Subscription;
import com.example.demo.repository.SubscriptionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRespository subscriptionRespository;

    public Subscription createSubscription(String email, String firstName, String gender, LocalDate dateOfBirth, boolean consent, String newsletterId) {
        Subscription subscription = new Subscription(email, firstName, gender, dateOfBirth, consent, newsletterId);
        subscriptionRespository.save(subscription);
        return subscription;
    }

    public List<Subscription> getSubscriptions(){
        return subscriptionRespository.findAll();
    }
}
