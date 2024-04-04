package com.health.keeper.entity;

import com.health.keeper.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;

    private String interest;


    @Builder
    public UserEntity(String username, String password, String email, String phone, String role, String provider, String providerId, Timestamp createDate, String interest) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
        this.interest = interest;
    }
    // DTO를 Entity로
    public static UserEntity toUpdateEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();

        //id 있으면 save아니고 update
        userEntity.setId(userDTO.getId());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setRole(userDTO.getRole());
        userEntity.setProvider(userDTO.getProvider());
        userEntity.setProviderId(userDTO.getProviderId());
        userEntity.setCreateDate(userDTO.getCreateDate());
        userEntity.setInterest(userDTO.getInterest());

        return userEntity;
    }
}
