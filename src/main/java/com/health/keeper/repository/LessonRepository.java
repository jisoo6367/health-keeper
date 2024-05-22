package com.health.keeper.repository;

import com.health.keeper.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {


}
