package com.health.keeper.dto;

import com.health.keeper.entity.LessonEntity;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class LessonDTO {
    private Long id; //예약 고유넘버
    private Long classId; //수업 고유아이디
    private Long userId; //이건 나중에 조인으로 가져오기
    private String className;
    private Long instructorId; //강사아이디 (이름으로하는게 낫나?)
    private String reservationDate; //예약날짜
    private String startTime; //수업 시작시간
    private String endTime; //수업 종료시간
    private String status; //예약상태 (몇명 찼는지..? 완료 그런거)


    // Entity -> DTO
    public static LessonDTO toLessonDTO(LessonEntity lessonEntity){
        LessonDTO lessonDTO = new LessonDTO();

        lessonDTO.setId(lessonEntity.getId());
        lessonDTO.setClassId(lessonEntity.getClassId());
        lessonDTO.setClassName(lessonEntity.getClassName());
        lessonDTO.setInstructorId(lessonEntity.getClassId());
        lessonDTO.setReservationDate(lessonEntity.getReservationDate());
        lessonDTO.setStartTime(lessonEntity.getStartTime());
        lessonDTO.setEndTime(lessonEntity.getEndTime());
        lessonDTO.setStatus(lessonEntity.getStatus());

        return lessonDTO;
    }

}
