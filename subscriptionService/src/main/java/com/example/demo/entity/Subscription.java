package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String firstName;
    private String gender;
    private LocalDate dateOfBirth;
    boolean consent;
    String newsletterId;

    public Subscription(){}

    public Subscription(String email, String firstName, String gender, LocalDate dateOfBirth, boolean consent, String newsletterId) {
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.consent = consent;
        this.newsletterId = newsletterId;
    }
}
