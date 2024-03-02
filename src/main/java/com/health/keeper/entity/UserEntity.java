package com.health.keeper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data //이거는 왜 붙이는거더라?
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public UserEntity(String username, String password, String email, String phone, String role, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}
