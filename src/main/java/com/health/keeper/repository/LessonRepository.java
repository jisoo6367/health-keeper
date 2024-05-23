package com.health.keeper.repository;

import com.health.keeper.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    List<LessonEntity> findByReservationDate (@Param("reservationDate")String reservationDate);


}
