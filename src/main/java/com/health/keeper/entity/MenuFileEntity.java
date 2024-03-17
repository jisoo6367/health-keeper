package com.health.keeper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menu_file_table")
public class MenuFileEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // JPA 에서 제공하는 어노테이션
    @ManyToOne(fetch = FetchType.LAZY) //부모 엔티티 조회시 필요한 상황에만 자식엔티티 같이 조회할 수 있음, EAGER는 항상 같이 가져옴
    @JoinColumn(name = "menu_id") // DB에 만들어지는 컬럼 이름
    private MenuEntity menuEntity; // 반드시 부모 엔티티 타입으로 적어줘야함


}
