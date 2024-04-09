package com.health.keeper.entity;

import com.health.keeper.dto.MembershipDTO;
import com.health.keeper.dto.MenuDTO;
import com.health.keeper.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// DB 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "membership_table")
public class MembershipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name; // 회원권 이름

    @Column
    private Long amount; // 회원권 횟수

    @Column
    private Long period; // 사용가능 기간 (일수로 체크)

    @Column
    private String status; // 상태 (사용대기, 사용중, 정지, 사용완료)

    @Column
    @Temporal(TemporalType.DATE) // Date 타입으로 매핑
    private LocalDate start_date;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate end_date;

    //자식테이블에서
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static MembershipEntity toSaveEntity(MembershipDTO membershipDTO, UserDTO userDTO){
        MembershipEntity membershipEntity = new MembershipEntity();

        UserEntity userEntity = UserEntity.toUpdateEntity(userDTO);
        System.out.println("멤버쉽엔티티에서 userId 넣기위해 dto-> entity로 변환 : " + userEntity);

        membershipEntity.setUserEntity(userEntity);
        membershipEntity.setName(membershipDTO.getName());
        membershipEntity.setAmount(membershipDTO.getAmount());
        membershipEntity.setPeriod(membershipDTO.getPeriod());
        membershipEntity.setStatus(membershipDTO.getStatus());
        membershipEntity.setStart_date(membershipDTO.getStart_date());
        membershipEntity.setEnd_date((membershipDTO.getStart_date()));

        return membershipEntity;
    }


}
