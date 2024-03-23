package com.health.keeper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // JPA 에서 제공하는 어노테이션
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY) //부모 엔티티 조회시 필요한 상황에만 자식엔티티 같이 조회할 수 있음, EAGER는 항상 같이 가져옴
    @JoinColumn(name = "board_id") // DB에 만들어지는 컬럼 이름
    private BoardEntity boardEntity; // 반드시 부모 엔티티 타입으로 적어줘야함

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String storedFileName){
        BoardFileEntity boardFileEntity = new BoardFileEntity();

        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        boardFileEntity.setBoardEntity(boardEntity); // pk값이 아니라 부모엔티티 객체를 넘겨줘야한다.

        return boardFileEntity;
    }
}
