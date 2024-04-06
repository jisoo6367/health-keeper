package com.health.keeper.repository;

import com.health.keeper.entity.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {

    List<MembershipEntity> findByUserEntityId (@Param("userId")Long userId);

}
