package com.nithin.auth.user;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.*;

@Entity
@Table(name = "auth")
public class Auth {
    @Id
    private String authId = UUID.randomUUID().toString().replace("-", "");

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiresAt;


    @PrePersist
    public void onCreate() {
        this.expiresAt = LocalDateTime.now().plusMinutes(10);
        this.otp = OtpGenerator.generateOtp(6);
    }

    @PreUpdate
    public void onUpdate() {
        this.expiresAt = LocalDateTime.now().plusMinutes(10);
        this.otp = OtpGenerator.generateOtp(6);
    }

    public Auth() { }
    public Auth(String email) {
        this.email = email;
    }

    public String getAuthId(){
        return this.authId;
    }

    public String getEmail(){
        return this.email;
    }

    public String getOtp(){
        return this.otp;
    }

    public LocalDateTime getExpiry(){
        return this.expiresAt;
    }
}
