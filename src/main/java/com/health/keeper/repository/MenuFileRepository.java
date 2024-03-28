package com.health.keeper.repository;

import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuFileRepository extends JpaRepository<MenuFileEntity, Long> {

    List<MenuFileEntity> findAllByMenuEntity(MenuEntity menuentity);

}
