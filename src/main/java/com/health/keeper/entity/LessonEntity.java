package com.health.keeper.entity;

import com.health.keeper.dto.MenuDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DB 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "lesson_table")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //예약 고유넘버

    @Column
    private Long classId; //수업 고유아이디

    @Column(length = 500)
    private String className;

    @Column
    private String instructorId; //강사 아이디

    @Column
    //@Temporal(TemporalType.DATE) // Date 타입으로 매핑
    private String reservationDate; //예약날짜

    @Column
    private String startTime;//수업 시작시간

    @Column
    private String endTime; //수업 종료시간

    @Column
    private int booked; //현재 예약인원 수

    @Column
    private int capacity; //총 예약 가능인원

    // JPA 에서 제공하는 어노테이션
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY) //부모 엔티티 조회시 필요한 상황에만 자식엔티티 같이 조회할 수 있음, EAGER는 항상 같이 가져옴
    @JoinColumn(name = "user_id") // DB에 만들어지는 컬럼 이름
    private UserEntity userEntity; // 반드시 부모 엔티티 타입으로 적어줘야함

    @OneToMany(mappedBy = "lessonEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>();

}
