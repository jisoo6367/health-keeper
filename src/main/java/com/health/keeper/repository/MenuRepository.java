package com.health.keeper.repository;

import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

}
