package com.health.keeper.dto;

import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class MenuDTO {
    private Long id;
    private String menuWriter;
    private String menuComment;
    private String menuCategory;
    private LocalDateTime menuCreatedTime;
    private LocalDateTime menuUpdatedTime;

    private List<MultipartFile> menuFile; // save.html -> Controller 넘어갈 때 파일을 담는 용도
    private List<String> originalFileName;
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부 (첨부: 1 , 미첨부: 0)



}
