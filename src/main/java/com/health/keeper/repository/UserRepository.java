package com.health.keeper.repository;

import com.health.keeper.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> { //<엔티티이름, pk 타입>

    public UserEntity findByUsername(String username);

    public UserEntity findByPhone(String phone);

    public int countByUsername(String username);
}
