package com.health.keeper.repository;

import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {


    List<MenuEntity> findByMenuWriter (@Param("menuWriter")String menuWriter);

    List<MenuEntity> findByMenuWriterAndCreatedTime
            (@Param("menuWriter")String menuWriter, @Param("createdTime") LocalDate createdTime);
}
