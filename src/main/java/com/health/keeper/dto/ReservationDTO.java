package com.health.keeper.dto;

import com.health.keeper.entity.LessonEntity;
import com.health.keeper.entity.ReservationEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class ReservationDTO {
    private Long id; //예약 고유넘버
    private Long userId;
    private Long lessonId;
    private String reservationDate; //예약날짜



    // Entity -> DTO
    public static ReservationDTO toReservationDTO(ReservationEntity reservationEntity){
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setId(reservationEntity.getId());
        reservationDTO.setUserId(reservationEntity.getUserEntity().getId());
        reservationDTO.setLessonId(reservationEntity.getLessonEntity().getId());
        reservationDTO.setReservationDate(reservationEntity.getReservationDate());



        return reservationDTO;
    }



}
