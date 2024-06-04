package com.health.keeper.service;

import com.health.keeper.entity.LessonEntity;
import com.health.keeper.entity.ReservationEntity;
import com.health.keeper.entity.UserEntity;
import com.health.keeper.repository.LessonRepository;
import com.health.keeper.repository.ReservationRepository;
import com.health.keeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean bookLesson(Long lessonId, Long userId, LocalDate reservationDate) {
        LessonEntity lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (lesson.getBooked() < lesson.getCapacity()) {
            lesson.setBooked(lesson.getBooked() + 1);
            lessonRepository.save(lesson);

            ReservationEntity reservation = new ReservationEntity();
            reservation.setLessonEntity(lesson);
            reservation.setUserEntity(user);
            reservation.setReservationDate(reservationDate);
            reservationRepository.save(reservation);

            return true;
        } else {
            return false;
        }
    }

    public List<ReservationEntity> getReservationsByUserId(Long id) {

        lessonRepository.updateBooked(id);
        return reservationRepository.findByUserEntityId(id);
    }
}
