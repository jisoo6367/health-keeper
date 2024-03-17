package com.health.keeper.repository;

import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuFileRepository extends JpaRepository<MenuFileEntity, Long> {
}
