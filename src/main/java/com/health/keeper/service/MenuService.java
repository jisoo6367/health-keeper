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


            MenuEntity menuEntity = MenuEntity.toSaveEntity(menuDTO);
            menuRepository.save(menuEntity); //save 메서드는 매개변수로 Entity를 줘야하고, 리턴도 Entity 타입으로 된다.

    }//save메서드-end








}