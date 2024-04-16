package com.health.keeper.repository;

import com.health.keeper.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // update board_table set board_hits= board_hits +1 where id=?
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    // Entity를 기준으로 약어 사용이 필수
    // 실제 DB컬럼이름 말고 Entity에 정의한 컬럼 이름으로 적어야함
    // :콜론뒤 id는 @Param("이거")
    @Modifying // update,delete 쿼리를 작성할때는 @Modifying 어노테이션 필수
    void updateHits(@Param("id") Long id);



    @Query("SELECT b FROM BoardEntity b WHERE b.boardContents LIKE %:keyword%")
    Page<BoardEntity> findByBoardContentsContaining(@Param("keyword") String keyword,Pageable pageable);


    @Query("SELECT b FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword%")
    Page<BoardEntity> findByBoardTitleContaining(@Param("keyword") String keyword,Pageable pageable);

    @Query("SELECT b FROM BoardEntity b WHERE b.boardWriter LIKE %:keyword%")
    Page<BoardEntity> findByBoardWriterContaining(@Param("keyword") String keyword,Pageable pageable);

    @Query("SELECT b FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword% OR b.boardContents LIKE %:keyword%")
    Page<BoardEntity> findByBoardContentsAndBoardTitleContaining(@Param("keyword") String keyword,Pageable pageable);

}
