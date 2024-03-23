package com.health.keeper.entity;

import com.health.keeper.dto.MenuDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY) //부모 엔티티 조회시 필요한 상황에만 자식엔티티 같이 조회할 수 있음, EAGER는 항상 같이 가져옴
    @JoinColumn(name = "menu_id") // DB에 만들어지는 컬럼 이름
    private MenuEntity menuEntity; // 반드시 부모 엔티티 타입으로 적어줘야함


    public static MenuFileEntity toMenuFileEntity(MenuEntity menuEntity, String originalFileName, String storedFileName){
        MenuFileEntity menuFileEntity = new MenuFileEntity();
        System.out.println("변수확인 오리지널 : " + originalFileName );
        System.out.println("변수확인 스토얼드 : " + storedFileName);
        menuFileEntity.setOriginalFileName(originalFileName);
        menuFileEntity.setStoredFileName(storedFileName);
        menuFileEntity.setMenuEntity(menuEntity);

        return menuFileEntity;
    }
}
