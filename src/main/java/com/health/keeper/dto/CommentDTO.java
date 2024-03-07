package com.health.keeper.dto;

import com.health.keeper.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id; //댓글의 id값
    private String commentWriter;
    private String commentContents;
    private Long boardId; //게시글 번호
    private LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        //commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        //boardId 매개변수 없이 위애껄로 쓸거면 서비스에 @Transactional 붙여야함
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }
}
