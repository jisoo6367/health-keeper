package com.health.keeper.service;

import com.health.keeper.dto.LessonDTO;
import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.LessonEntity;
import com.health.keeper.entity.MembershipEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import com.health.keeper.repository.LessonRepository;
import com.health.keeper.repository.MenuFileRepository;
import com.health.keeper.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LessonService {
    //생성자 주입
    private final LessonRepository lessonRepository;

    public List<LessonDTO> findById(String reservationDate){


        List<LessonEntity> lessonEntityList = lessonRepository.findByReservationDate(reservationDate);

        List<LessonDTO> lessonDTOList = new ArrayList<>();
        return lessonDTOList;
    };


}