package com.health.keeper.repository;

import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

    List<BoardFileEntity> findAllByBoardEntity(BoardEntity boardEntity);

    @Modifying
    void deleteByStoredFileName(@Param("storedFileName") String storedFileName);
}
