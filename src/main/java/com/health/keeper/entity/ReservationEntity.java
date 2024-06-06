package com.health.keeper.entity;

import com.health.keeper.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// DB 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "reservation_table")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 고유번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id") // lesson_table의 id를 참조하는 외래 키
    private LessonEntity lessonEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // user_table의 id를 참조하는 외래 키
    private UserEntity userEntity;

    @Column
    private LocalDate reservationDate; // 예약 날짜

    // DTO -> Entity 변환작업
//    public static ReservationEntity toSaveEntity(ReservationDTO boardDTO){
//        BoardEntity boardEntity = new BoardEntity();
//
//        return boardEntity;
//    }

}
