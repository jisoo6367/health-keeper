package com.health.keeper.dto;

import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data //(@Getter + @Setter + @ToString + @NoArgsConstructor + @AllArgsConstructor)
public class MenuDTO {
    private Long id;
    private String menuWriter;
    private String menuComment;
    private String menuCategory;
    private LocalDate  menuCreatedTime;
    private LocalDateTime menuUpdatedTime;

    private List<MultipartFile> menuFile; // save.html -> Controller 넘어갈 때 파일을 담는 용도
    private List<String> originalFileName;
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부 (첨부: 1 , 미첨부: 0)

    private List<String> delFiles;

    // Entity -> DTO
    public static MenuDTO toMenuDTO(MenuEntity menuEntity){
        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setId(menuEntity.getId());
        menuDTO.setMenuWriter(menuEntity.getMenuWriter());
        menuDTO.setMenuComment(menuEntity.getMenuComment());
        menuDTO.setMenuCategory(menuEntity.getMenuCategory());
        menuDTO.setMenuCreatedTime(menuEntity.getCreatedTime());

        //첨부파일
        if(menuEntity.getFileAttached() == 0){
            menuDTO.setFileAttached(0);
        }else{
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            menuDTO.setFileAttached(menuEntity.getFileAttached());
            for(MenuFileEntity menuFileEntity: menuEntity.getMenuFileEntityList()){
                originalFileNameList.add(menuFileEntity.getOriginalFileName());
                storedFileNameList.add(menuFileEntity.getStoredFileName());
            }
            menuDTO.setOriginalFileName(originalFileNameList);
            menuDTO.setStoredFileName(storedFileNameList);
        }
        return menuDTO;
    }


    // Entity -> DTO
    public static MenuDTO toMenuDTO(MenuFileEntity menuFileEntity){
        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setId(menuFileEntity.getId());
        menuDTO.setOriginalFileName(Collections.singletonList(menuFileEntity.getOriginalFileName()));
        menuDTO.setStoredFileName(Collections.singletonList(menuFileEntity.getStoredFileName()));

        return menuDTO;

    };

}
