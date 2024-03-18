package ru.vistar;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "users")
public class User {
    @Column(name = "user_id")
    private Long id;
    private String password;
    private String email;
    private Boolean enabled;
}

