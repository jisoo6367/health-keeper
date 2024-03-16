package com.health.keeper.entity;

import com.health.keeper.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// DB 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "menu_table")
public class MenuEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String menuWriter;

    @Column(length = 500)
    private String menuComment;

    @Column
    private int menuCategory; //아침, 점심, 저녁, 간식

    @Column
    private int fileAttached; // 첨부파일 있으면 1, 없으면 0




}
