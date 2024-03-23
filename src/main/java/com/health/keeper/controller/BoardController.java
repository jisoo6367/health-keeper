package com.health.keeper.controller;

import com.health.keeper.dto.BoardDTO;
import com.health.keeper.dto.CommentDTO;
import com.health.keeper.entity.CommentEntity;
import com.health.keeper.service.BoardService;
import com.health.keeper.service.CommentService;
import com.health.keeper.service.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String updateForm(@PathVariable("id") Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update (@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
        //return "redirect:/board/" + boardDTO.getId();
        // 이렇게 상세페이지로 가게하면, 수정만해도 조회수가 오르게 됨
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.delete(id);

        return "redirect:/board/paging";
    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        // Pageable 인터페이스는 springframework.data.domain로 임포트/ java.awt.print 아님

    //    pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);

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

        return "paging";
    }
}
