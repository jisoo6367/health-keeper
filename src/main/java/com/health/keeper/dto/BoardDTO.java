package com.health.keeper.dto;

import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

// DTO (Data Transfer Object), VO, Bean
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits; //조회수
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private List<MultipartFile> boardFile; // save.html -> Controller 넘어갈 때 파일을 담는 용도
    private List<String> originalFileName;
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부 (첨부: 1 , 미첨부: 0)
    private List<String> delFiles;

    private String searchType;
    private String searchValue;

    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    // Entity -> DTO 변환작업
    public static BoardDTO toBoardDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        // detail에서 첨부파일 보여주기
        if (boardEntity.getFileAttached() == 0) { // 파일 없음
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        }else { //파일 있음
            /* //단일 파일일 때
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함
            // originalFileName과 storedFileName은 board_file_table(BoardFileEntity) 객체에 들어 있음
            // join
            // select * from board_table b, board_file_table bf where b.id = bf.board_id
            // and where b.id=?
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
            */
            //다중 파일일 떄
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            for (BoardFileEntity boardFileEntity: boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);

        }

        return boardDTO;
    }


    // update 페이지에서 첨부파일 보여주기위한
    // Entity -> DTO
    public static BoardDTO toBoardDTO(BoardFileEntity boardFileEntity){
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId(boardFileEntity.getId());
        boardDTO.setBoardCreatedTime(boardFileEntity.getCreatedTime());
        boardDTO.setOriginalFileName(Collections.singletonList(boardFileEntity.getOriginalFileName()));
        boardDTO.setStoredFileName(Collections.singletonList(boardFileEntity.getStoredFileName()));

        return boardDTO;

    };

}
