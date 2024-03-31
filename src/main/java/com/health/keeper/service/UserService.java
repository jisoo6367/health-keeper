package com.health.keeper.service;

import com.health.keeper.dto.UserDTO;
import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    //생성자 주입
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO findByUsername (Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName());


        UserDTO userDTO = UserDTO.toUserDTO(userEntity);
        userDTO.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        System.out.println("유저서비스에서 userDTO : " + userDTO);
        return userDTO;
    }


}