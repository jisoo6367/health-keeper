package com.health.keeper.service;

import com.health.keeper.dto.UserDTO;
import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

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
        System.out.println("유저서비스 findByUsername 에서 userDTO : " + userDTO);
        return userDTO;
    }

    public UserDTO findById (Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        UserEntity userEntity = userEntityOptional.get();
        UserDTO userDTO = UserDTO.toUserDTO(userEntity);
        return userDTO;
    }

    public Long update (UserDTO userDTO){

        System.out.println("update 서비스- userDTO: " + userDTO);
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        System.out.println("비크립트확인 : " + userDTO.getPassword());
        // DTO -> Entity
        UserEntity userEntity = UserEntity.toUpdateEntity(userDTO);
        System.out.println("update 서비스 - 엔티티타입으로 변환: " + userEntity);

        userRepository.save(userEntity);
        System.out.println("update 서비스에서 save(update)작동 후 : " + userEntity);

        return userDTO.getId();
    }

    public UserDTO findByPhone (String phone) {
        UserEntity userEntity = userRepository.findByPhone(phone);

        UserDTO userDTO = UserDTO.toUserDTO(userEntity);
        System.out.println("유저서비스에서 userDTO : " + userDTO);

        return userDTO;
    }

}