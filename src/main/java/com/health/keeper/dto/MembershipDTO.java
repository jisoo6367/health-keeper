package com.health.keeper.dto;

import com.health.keeper.entity.MembershipEntity;
import lombok.*;
import java.time.LocalDate;

// DTO (Data Transfer Object), VO, Bean
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {
    private Long id;
    private String name;
    private Long amount;
    private Long period;
    private String status;
    private LocalDate start_date;
    private LocalDate end_date;

    // Entity -> DTO
    public static MembershipDTO toMembershipDTO(MembershipEntity membershipEntity){
        MembershipDTO membershipDTO = new MembershipDTO();

        membershipDTO.setId(membershipEntity.getId());
        membershipDTO.setName(membershipEntity.getName());
        membershipDTO.setAmount(membershipEntity.getAmount());
        membershipDTO.setPeriod(membershipEntity.getPeriod());
        membershipDTO.setStatus(membershipEntity.getStatus());
        membershipDTO.setStart_date(membershipEntity.getStart_date());
        membershipDTO.setEnd_date(membershipEntity.getEnd_date());
        return membershipDTO;
    }

}
