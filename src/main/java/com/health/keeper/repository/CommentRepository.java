package com.health.keeper.repository;

import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // select * from comment_table where board_id=? 이거랑 order by id desc; 이거
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity); //대소문자 잘지키기
}
