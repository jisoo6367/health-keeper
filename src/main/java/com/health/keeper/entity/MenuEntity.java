package com.health.keeper.entity;

import com.health.keeper.dto.BoardDTO;
import com.health.keeper.dto.MenuDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// DB 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "menu_table")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String menuWriter;

    @Column(length = 500)
    private String menuComment;

    @Column
    private String menuCategory; //아침, 점심, 저녁, 간식

    @Column
    private int fileAttached; // 첨부파일 있으면 1, 없으면 0

    @Column
    @Temporal(TemporalType.DATE) // Date 타입으로 매핑
    private LocalDate createdTime;

    @OneToMany(mappedBy = "menuEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MenuFileEntity> menuFileEntityList = new ArrayList<>();


    // DTO -> Entity (첨부파일 없는 경우)
    public static MenuEntity toSaveEntity(MenuDTO menuDTO){
        MenuEntity menuEntity = new MenuEntity();

        menuEntity.setMenuCategory(menuDTO.getMenuCategory());
        menuEntity.setMenuComment(menuDTO.getMenuComment());
        menuEntity.setMenuWriter(menuDTO.getMenuWriter());
        menuEntity.setCreatedTime(menuDTO.getMenuCreatedTime());
        menuEntity.setFileAttached(0);

        return menuEntity;
    }

    // DTO -> Entity (첨부파일 있는 경우)
    public static MenuEntity toSaveFileEntity(MenuDTO menuDTO){
        MenuEntity menuEntity = new MenuEntity();

        menuEntity.setMenuCategory(menuDTO.getMenuCategory());
        menuEntity.setMenuComment(menuDTO.getMenuComment());
        menuEntity.setMenuWriter(menuDTO.getMenuWriter());
        menuEntity.setCreatedTime(menuDTO.getMenuCreatedTime());
        menuEntity.setFileAttached(1);

        return menuEntity;
    }

    public static MenuEntity toUpdateEntity(MenuDTO menuDTO){
        MenuEntity menuEntity = new MenuEntity();

        menuEntity.setId(menuDTO.getId()); //Id값 있으니까 insert문 아니고 update문임!
        menuEntity.setMenuCategory(menuDTO.getMenuCategory());
        menuEntity.setMenuComment(menuDTO.getMenuComment());
        menuEntity.setMenuWriter(menuDTO.getMenuWriter());
        menuEntity.setCreatedTime(menuDTO.getMenuCreatedTime());
        menuEntity.setFileAttached(0);

//        if(menuDTO.getStoredFileName().isEmpty()){
//            menuEntity.setFileAttached(0);
//        }else{ menuEntity.setFileAttached(1);}
        //이렇게 구분하면 메서드 하나로 나눌 수 있어서 깔끔한데,
        //수정페이지에서 기존에 있던 첨부파일을 지우고 다른 파일을 추가하면 .getStoredFileName()이 is null이 나와서 오류가남
        //-> 메서드를 두개로 나누고 서비스에서 구분

        return menuEntity;
    }
    public static MenuEntity toUpdateFileEntity(MenuDTO menuDTO) {
        MenuEntity menuEntity = new MenuEntity();

        menuEntity.setId(menuDTO.getId()); //Id값 있으니까 insert문 아니고 update문임!
        menuEntity.setMenuCategory(menuDTO.getMenuCategory());
        menuEntity.setMenuComment(menuDTO.getMenuComment());
        menuEntity.setMenuWriter(menuDTO.getMenuWriter());
        menuEntity.setCreatedTime(menuDTO.getMenuCreatedTime());
        menuEntity.setFileAttached(1);

        return menuEntity;
    }
}
