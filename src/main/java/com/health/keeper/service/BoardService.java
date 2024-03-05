package com.health.keeper.service;

import com.health.keeper.dto.BoardDTO;
import com.health.keeper.entity.BoardEntity;
import com.health.keeper.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {
    //생성자 주입
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); //save 메서드는 매개변수로 Entity를 줘야하고, 리턴도 Entity 타입으로 된다.
        //그래서 서비스에서는 결국 DTO -> Entity 변환 또는 Entity -> DTO변환을 해야하게됨
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        //DB에서는 Entity로 넘겨주고 그것을 DTO로 바꿔서 가져가야함
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    // jpa제공 메서드말고 내가 만든 메서드 사용하는 경우에는 @Transactional 어노테이션 필수
    public void updateHits(Long id) {

        boardRepository.updateHits(id);
    }


    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){ //있으면
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.touUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        //save메서드가 insert와 update를 다 해줌. id값이 있냐없냐의 차이
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {

        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; // 한 페이지당 게시글 수

        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        // page 위치에 있는 값은 DB 기준 0부터 시작
        // Sort.by의 마지막 properties는 엔티티 컬럼 기준임. DB 컬럼 이름 X

        System.out.println(".getContent() = " + boardEntities.getContent());// 요청페이지에 해당하는 글
        System.out.println(".getTotalElements() = " + boardEntities.getTotalElements());// 전체 글 갯수
        System.out.println(".getNumber = " + boardEntities.getNumber());// DB로 요청한 페이지 번호 (1이면 0)
        System.out.println(".getTotalPages() = " + boardEntities.getTotalPages());// 전체 페이지 갯수
        System.out.println(".getSize() = " + boardEntities.getSize());// 한 페이지에 보여지는 글 갯수
        System.out.println(".hasPrevious() = " + boardEntities.hasPrevious());// 이전 페이지 존재 여부 (t/f)
        System.out.println(".isFirst() = " + boardEntities.isFirst());// 첫 페이지 여부 (t/f)
        System.out.println(".isLast() = " + boardEntities.isLast());// 마지막 페이지 여부 (t/f)

        //목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS =
                boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        // Page 인터페이스에서 제공해주는 map 메서드를 이용하여 Entity를 DTO 객체로 바꿔줌
        return boardDTOS;
    }
}
