package com.health.keeper.service;

import com.health.keeper.dto.BoardDTO;
import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.BoardFileEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.entity.MenuFileEntity;
import com.health.keeper.repository.BoardFileRepository;
import com.health.keeper.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {
    //생성자 주입
    private final BoardRepository boardRepository;

    private final BoardFileRepository boardFileRepository;


    public void save(BoardDTO boardDTO) throws IOException {

        for (MultipartFile file : boardDTO.getBoardFile()) {

            if (file.getSize() == 0) {
                System.out.println("첨부파일 없을 때");
                BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
                boardRepository.save(boardEntity); //save 메서드는 매개변수로 Entity를 줘야하고, 리턴도 Entity 타입으로 된다.
            } else {

                System.out.println("첨부 파일 있을 때");
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만듦
                4. 저장 경로 설정
                5. 경로에 파일 저장
                6. board_table에 게시글 데이터 save 처리
                7. board_file_table에 첨부파일 데이터 save 처리
            */
            /* //단일 파일 업로드
            MultipartFile boardFile = boardDTO.getBoardFile(); // 1
            String originalFilename = boardFile.getOriginalFilename(); // 2
            String storedFileName = System.currentTimeMillis()+ "_" + originalFilename; // 3
            String savePath = "C:/springboot_img/" + storedFileName; // 4
            boardFile.transferTo(new File(savePath)); // 5 파일 저장까지만 완료
            //add exception처리하면 컨트롤러의 save도 빨간줄 동일처리해줘야함

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            //DB에 저장하기 전이라서 boardEntity에는 Id 값이 없음
            Long saveId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(saveId).get();

            // 파일 엔티티로 변환하기 위한 작업
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
            */


                // 다중 파일 업로드로 바꾸면서 부모데이터가 먼저 저장이 되어야 해서 순서가 바뀌게됨
                BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
                //DB에 저장하기 전이라서 boardEntity에는 Id 값이 없음
                Long saveId = boardRepository.save(boardEntity).getId(); //게시글의 번호
                BoardEntity board = boardRepository.findById(saveId).get();
                //System.out.println("=====그냥 findById(게시글번호) : " + boardRepository.findById(saveId));
                //Optional[com.health.keeper.entity.BoardEntity@782dd072]
                //System.out.println("=====거기에 .get() : " + board);
                //com.health.keeper.entity.BoardEntity@3eb2fc9e

                for (MultipartFile boardFile : boardDTO.getBoardFile()) { // 1
                    String originalFilename = boardFile.getOriginalFilename(); // 2
                    String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3
                    String savePath = "C:/springboot_img/board/" + storedFileName; // 4
                    boardFile.transferTo(new File(savePath)); // 5 파일 저장까지만 완료
                    //add exception처리하면 컨트롤러의 save도 빨간줄 동일처리해줘야함

                    // 파일 엔티티로 변환하기 위한 작업
                    BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                    boardFileRepository.save(boardFileEntity);
                } //내부 for-end

            }// else-end
        }// for-end
    }//save메서드-end


    //서비스에서는 결국 DTO -> Entity 변환 또는 Entity -> DTO변환을 해야하게됨


    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        //DB에서는 Entity로 넘겨주고 그것을 DTO로 바꿔서 가
        // r
        // 져가야함
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

    @Transactional
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

    @Transactional
    public BoardDTO update(BoardDTO boardDTO) throws IOException {

        BoardEntity boardEntity = null;

        for (MultipartFile file : boardDTO.getBoardFile()) {
            if (file != null && !file.isEmpty()) {
                //System.out.println("수정 서비스 추가하는 첨파 있을 때");

                BoardEntity board = boardRepository.findById(boardDTO.getId()).get();
                String originalFilename = file.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+ "_" + originalFilename;
                String savePath = "C:/springboot_img/board/" + storedFileName;
                file.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);

                boardEntity = BoardEntity.touUpdateFileEntity(boardDTO);
                boardRepository.save(boardEntity);

            }else{
                //System.out.println("수정 서비스 추가하는 첨파 없을 때");

                boardEntity = BoardEntity.touUpdateEntity(boardDTO);
                boardRepository.save(boardEntity);
                //save메서드가 insert와 update를 다 해줌. id값이 있냐없냐의 차이
            }
        }

        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void deleteFile(String storedFileName) {
        boardFileRepository.deleteByStoredFileName(storedFileName);
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




    public Page<BoardDTO> searchAndPaging(String type, String keyword, Pageable pageable) {
        Page<BoardEntity> boardEntities;

        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; // 한 페이지당 게시글 수

        if (type.equals("boardTitle")) {
            boardEntities = boardRepository.findByBoardTitleContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if (type.equals("boardContents")) {
            boardEntities = boardRepository.findByBoardContentsContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if (type.equals("boardWriter")) {
            boardEntities = boardRepository.findByBoardWriterContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if (type.equals("boardTitleAndBoardContents")) {
            boardEntities = boardRepository.findByBoardContentsAndBoardTitleContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else {
            // 예외 처리 또는 기본값 설정
            return Page.empty(); // 혹은 다른 적절한 처리
        }
        //System.out.println("서비스에서 처리결과 " + boardEntities);

        // 페이지 번호를 1로 시작하도록 조정
        int pageNumber = pageable.getPageNumber() + 1;
        Pageable adjustedPageable = PageRequest.of(pageNumber - 1, pageable.getPageSize(), pageable.getSort());


        Page<BoardDTO> boardDTOS =
                boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        // Page 인터페이스에서 제공해주는 map 메서드를 이용하여 Entity를 DTO 객체로 바꿔줌

        //System.out.println("서비스 결과 boardDTOS : " + boardDTOS);
        return boardDTOS;
    }


    //첨부파일 조회
    public List<BoardDTO> getAttachFileList(BoardEntity boardEntity){

        List<BoardFileEntity> boardFileEntityList = boardFileRepository.findAllByBoardEntity(boardEntity);

        //Entity -> DTOList
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardFileEntity boardFileEntity: boardFileEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardFileEntity));
        }
        return boardDTOList;
    }

}