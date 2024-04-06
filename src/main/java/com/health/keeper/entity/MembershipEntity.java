package com.health.keeper.entity;

import com.health.keeper.dto.MenuDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

}
