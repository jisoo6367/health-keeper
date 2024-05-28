package com.health.keeper.dto;

import com.health.keeper.entity.LessonEntity;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class LessonDTO {
    private Long id; //예약 고유넘버
    private Long classId; //수업 고유아이디
    private Long userId; //이건 나중에 조인으로 가져오기
    private String className;
    private String instructorId; //강사아이디 (이름으로하는게 낫나?)
    private String reservationDate; //예약날짜
    private String startTime; //수업 시작시간
    private String endTime; //수업 종료시간
    private int booked; //예약상태 (몇명 찼는지..? 완료 그런거)
    private int capacity; // 총 예약가능인원


    // Entity -> DTO
    public static LessonDTO toLessonDTO(LessonEntity lessonEntity){
        LessonDTO lessonDTO = new LessonDTO();

        lessonDTO.setId(lessonEntity.getId());
        lessonDTO.setClassId(lessonEntity.getClassId());
        lessonDTO.setClassName(lessonEntity.getClassName());
        lessonDTO.setInstructorId(lessonEntity.getInstructorId());
        lessonDTO.setReservationDate(lessonEntity.getReservationDate());
        lessonDTO.setStartTime(lessonEntity.getStartTime());
        lessonDTO.setEndTime(lessonEntity.getEndTime());
        lessonDTO.setBooked(lessonEntity.getBooked());
        lessonDTO.setCapacity(lessonEntity.getCapacity());

        return lessonDTO;
    }

    // 수업 리스트를 시간순서대로 정렬하는 메서드
    public static List<LessonDTO> sortLessonsByTime(List<LessonDTO> lessons) {
        return lessons.stream()
                .sorted(Comparator.comparing(LessonDTO::getStartTime))
                .collect(Collectors.toList());
    }

}
