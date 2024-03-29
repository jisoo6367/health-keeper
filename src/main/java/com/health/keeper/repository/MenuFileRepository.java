package com.health.keeper.repository;

import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuFileRepository extends JpaRepository<MenuFileEntity, Long> {

    List<MenuFileEntity> findAllByMenuEntity(MenuEntity menuentity);

    @Modifying
    void deleteByStoredFileName(@Param("storedFileName") String storedFileName);
}
