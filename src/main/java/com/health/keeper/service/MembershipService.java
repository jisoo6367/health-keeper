package com.health.keeper.service;

import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.MembershipEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import com.health.keeper.repository.MembershipRepository;
import com.health.keeper.repository.MenuFileRepository;
import com.health.keeper.repository.MenuRepository;
import com.health.keeper.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MembershipService {
    //생성자 주입
    private final MembershipRepository membershipRepository;

    private final UserRepository userRepository;

    public List<MembershipDTO> findByUserId(Principal principal){

        long userId = 0;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userId = userRepository.findByUsername(userDetails.getUsername()).getId();
    }

        System.out.println("멤버쉽서비스에서 usreId 가져오는지 : " + userId);


        // 옵셔널 객체로 싸야하는 경우
//        Optional<MembershipEntity> optionalMembershipEntity = membershipRepository.findById(userId);
//
//        List<MembershipEntity> membershipEntityList = new ArrayList<>();
//        optionalMembershipEntity.ifPresent(membershipEntityList::add);

        //옵셔널 없이 그냥 List 되는 경우
        List<MembershipEntity> membershipEntityList = membershipRepository.findByUserEntityId(userId);

        // Entity -> DTO
        List<MembershipDTO> membershipDTOList = new ArrayList<>();
        for(MembershipEntity membershipEntity: membershipEntityList){
            membershipDTOList.add(MembershipDTO.toMembershipDTO(membershipEntity));
        }





        return membershipDTOList;

    }






}