package com.health.keeper.service;

import com.health.keeper.dto.BoardDTO;
import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import com.health.keeper.repository.BoardFileRepository;
import com.health.keeper.repository.BoardRepository;
import com.health.keeper.repository.MenuFileRepository;
import com.health.keeper.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MenuService {
    //생성자 주입
    private final MenuRepository menuRepository;

    private final MenuFileRepository menuFileRepository;

    //서비스에서는 결국 DTO -> Entity 변환 또는 Entity -> DTO변환을 해야하게됨
    public void save(MenuDTO menuDTO) throws IOException {

            if(menuDTO.getFileAttached() == 0){
                System.out.println("서비스의 이프절인지");
                MenuEntity menuEntity = MenuEntity.toSaveEntity(menuDTO);
                menuRepository.save(menuEntity);

            } else{
                System.out.println("서비스의 엘스절인지");

                MenuEntity menuEntity = MenuEntity.toSaveFileEntity(menuDTO);
                //메뉴테이블에 올리고 게시글의 번호 가져오기
                Long saveId = menuRepository.save(menuEntity).getId();
                MenuEntity menu = menuRepository.findById(saveId).get();
                System.out.println("findById(게시글번호) = " + menuRepository.findById(saveId));
                //Optional[com.health.keeper.entity.MenuEntity@21e63377]
                System.out.println("거기에 .get() = " + menu);
                //com.health.keeper.entity.MenuEntity@2054b41c

                for(MultipartFile menuFile : menuDTO.getMenuFile()){
                    String originalFilename = menuFile.getOriginalFilename();
                    String storedFileName = System.currentTimeMillis()+ "_" + originalFilename;
                    String savePath = "C:/springboot_img/menu/" + storedFileName;
                    menuFile.transferTo(new File(savePath));

                    MenuFileEntity menuFileEntity = MenuFileEntity.toMenuFileEntity(menu, originalFilename, storedFileName);
                    menuFileRepository.save(menuFileEntity);
                }
            }
    }//save메서드-end

    @Transactional
    public List<MenuDTO> findAll() {
        List<MenuEntity> menuEntityList = menuRepository.findAll();

        System.out.println("서비스 findAll에서 메뉴엔티티리스트" + menuEntityList);
        //Entity -> DTO
        List<MenuDTO> menuDTOList = new ArrayList<>();
        for(MenuEntity menuEntity: menuEntityList){
            menuDTOList.add(MenuDTO.toMenuDTO(menuEntity));
        }
        return menuDTOList;
    }

    @Transactional
    public MenuDTO findById(Long id){
        Optional<MenuEntity> OptionalMenuEntity = menuRepository.findById(id);

        if(OptionalMenuEntity.isPresent()){
            MenuEntity menuEntity = OptionalMenuEntity.get();
            MenuDTO menuDTO = MenuDTO.toMenuDTO(menuEntity);
            return  menuDTO;
        }else{
            return null;
        }
    }


    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}