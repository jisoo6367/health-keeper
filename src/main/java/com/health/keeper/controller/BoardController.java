package com.health.keeper.controller;

import com.health.keeper.dto.BoardDTO;
import com.health.keeper.dto.CommentDTO;
import com.health.keeper.dto.MenuDTO;
import com.health.keeper.entity.BoardEntity;
import com.health.keeper.entity.CommentEntity;
import com.health.keeper.entity.MenuEntity;
import com.health.keeper.service.BoardService;
import com.health.keeper.service.CommentService;
import com.health.keeper.service.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor //서비스를 호출해야해서 붙이는 어노테이션?
@Controller
public class BoardController {
    //생성자 주입
    private final BoardService boardService;
    private final CommentService commentService;
    private final FileUploadService fileUploadService;

    @GetMapping("/board")
    public String showBoard() {
        return "board";
    }

    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
    //public String save(@ModelAttribute BoardDTO boardDTO, @RequestPart("boardFile")MultipartFile image) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        System.out.println("boardDTO = " + boardDTO.getBoardFile());
        boardService.save(boardDTO);
        //fileUploadService.uploadImageToImgBB(boardFile);
        //fileUploadService.uploadImage(image);
        return "redirect:/board/paging";
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        System.out.println("boardDTOList = " + boardDTOList);
        model.addAttribute("boardList", boardDTOList);

        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){
        // @PathVariable 어노테이션: 경로상에 있는 값을 가져올 때에 이용

        // 해당 게시글의 조회수를 하나 올린 후에 게시글 데이터를 가져와서 detail.html에 출력해야함
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        // + 댓글 목록 가져오기
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);

        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, @RequestParam("page") String page, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        model.addAttribute("page", page);
        System.out.println("수정페이지갈때 페이지 넘어오는지 : " + page);
        return "update";
    }

    @PostMapping("/update")
    public String update (@ModelAttribute BoardDTO boardDTO, @RequestParam("page") String page, Model model) throws IOException {

        BoardDTO board = boardService.update(boardDTO);

        List<String> delFiles = boardDTO.getDelFiles();
        System.out.println("컨트롤러로 넘어온 삭제파일 List : " + delFiles);
        if(delFiles != null) {
            for (String storedFileName : delFiles){
                boardService.deleteFile(storedFileName);

                File file = new File("C:\\springboot_img\\board\\" + storedFileName);
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("파일 삭제 성공: " + storedFileName);
                } else {
                    System.out.println("파일 삭제 실패: " + storedFileName);
                }

            }
        }


        System.out.println("컨트롤러 update 결과 : " + board);
        //BoardDTO(id=65, boardWriter=찐, boardPass=1234, boardTitle=첨파없이, boardContents=사이즈로 구분, boardHits=2, boardCreatedTime=2024-04-17T12:31:01.424787, boardUpdatedTime=2024-04-17T13:05:13.343654, boardFile=null, originalFileName=null, storedFileName=null, fileAttached=0, delFiles=null, searchType=null, searchValue=null)
        System.out.println("디테일로넘어가기직전 컨트롤러 " + board.getBoardFile());
        System.out.println("========" + board.getStoredFileName());

        model.addAttribute("board", board);
        model.addAttribute("page", page);
        return "detail";
        //return "redirect:/board/" + boardDTO.getId();
        // 이렇게 상세페이지로 가게하면, 수정만해도 조회수가 오르게 됨
    }

//    @GetMapping("/delete/{id}")
//    public String delete (@PathVariable("id") Long id){
//        boardService.delete(id);
//        System.out.println("기존 삭제 컨트롤러");
//        return "redirect:/board/paging";
//    }

    @PostMapping("/delete")
    public String delete (@RequestBody BoardDTO boardDTO){
        //boardService.delete(id);
        System.out.println("경로에 저장된 이미지도 삭제하는 컨트롤러");
        System.out.println(boardDTO.getId());
        System.out.println(boardDTO.getStoredFileName());

        // DB에서 삭제
        boardService.delete(boardDTO.getId());

        List<String> storedFileName = boardDTO.getStoredFileName();
        //[1711177757014_TouchKeyboardThemeLight001.jpg]
        if(storedFileName != null){
            for (String fileName : storedFileName) {
                File file = new File("C:\\springboot_img\\board\\" + fileName);
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("파일 삭제 성공: " + fileName);
                } else {
                    System.out.println("파일 삭제 실패: " + fileName);
                }
            }
        }


        return "redirect:/board/paging";
    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model,
                         BoardDTO boardDTO){
                         //@RequestParam(value = "searchValue", required = false) String keyword){
        // Pageable 인터페이스는 springframework.data.domain로 임포트/ java.awt.print 아님


        String keyword = boardDTO.getSearchValue();
        String type = boardDTO.getSearchType();

        Page<BoardDTO> boardList;

        if (keyword != null && !keyword.isEmpty()) {
            boardList = boardService.searchAndPaging(type, keyword, pageable);
            model.addAttribute("type", type);
            model.addAttribute("keyword", keyword);
        } else {
            boardList = boardService.paging(pageable);
        };


    //    pageable.getPageNumber();
    //    Page<BoardDTO> boardList = boardService.paging(pageable);

        //페이징 몇 쪽씩 보여줄건지 ex) << 1 2 3 >> / << 4 5 6 >>
        int blockLimit = 3;

        //해당 페이지의 맨첫번째 페이징숫자 ex) 2페이지를 보고있다면 1
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) -1) * blockLimit + 1;
        // (현재페이지-1)/몇쪽씩보여줄건지 =>하면 몫만 나오고 거기에 *몇쪽씩보여줄건지 +1

        //해당 페이지의 맨마지막 페이징숫자 ex) 2페이지를 보고있다면 3
        int endPage = Math.min((startPage + blockLimit - 1), boardList.getTotalPages());
        //int endPage = ((startPage + blockLimit -1) < boardList.getTotalPages()) ? startPage + blockLimit -1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        System.out.println("컨트롤러에서 " + boardList.getContent());

        model.addAttribute("keyword" , keyword);
        model.addAttribute("type" , type);
        System.out.println("keyword 살아있나 " + keyword +", " + type);

        return "paging";
    }




    @GetMapping(value = "/getFiles/{id}" , produces = {"application/json; charset=utf-8"})
    public @ResponseBody ResponseEntity<List<BoardDTO>> showAttachFiles(@PathVariable("id") BoardEntity boardEntity){
        System.out.println("첨부파일 가져오는 컨트롤러 ======");

        ResponseEntity<List<BoardDTO>> boardDTO = new ResponseEntity<List<BoardDTO>>(boardService.getAttachFileList(boardEntity), HttpStatus.OK);
        System.out.println("첨파가져오는 컨트롤러에서 boardDTO  : " + boardDTO);
        return new ResponseEntity<List<BoardDTO>>(boardService.getAttachFileList(boardEntity), HttpStatus.OK);
    }


}
