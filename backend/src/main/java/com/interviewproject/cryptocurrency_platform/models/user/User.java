package com.interviewproject.cryptocurrency_platform.models.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    public static final BigDecimal STARTING_BALANCE = new BigDecimal("10000");

    private Long id;
    private String name;
    private String email;
    private String password;
    private BigDecimal balance;
    private String role;

    public User() {}
    public User(String name, String email, String password, BigDecimal balance, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }
    public User(Long id, String name, String email, String password, BigDecimal balance, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Treat email as username in the context of Spring User Details
    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdrawFromBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void depositToBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void reset() {
        balance = STARTING_BALANCE;
    }
}