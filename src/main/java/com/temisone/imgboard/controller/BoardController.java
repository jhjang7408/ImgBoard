package com.temisone.imgboard.controller;

import com.temisone.imgboard.entity.BoardEntity;
import com.temisone.imgboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String main(){
        return "main";
    }



    @GetMapping("/board/write")
    public String writeForm(){
        return "write";
    }

    @PostMapping("/board/write")
    public String write(@ModelAttribute BoardEntity boardEntity, MultipartFile file) throws IOException {

        boardService.write(boardEntity, file);

        return "redirect:/board/list";
    }


    @GetMapping("/board/list")
    public String boardList(Model model){
        List<BoardEntity> list = boardService.findAll();

        model.addAttribute("list", list);

        return "list";
    }


    @GetMapping("/board/{id}")
    public String boardView(@PathVariable int id, Model model){

        Optional<BoardEntity> boardEntity = boardService.findById(id);

        model.addAttribute("read", boardEntity.get());

        return "view";
    }


    @GetMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable int id){
        boardService.delete(id);

        return "redirect:/board/list";
    }


    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable int id, Model model){
        Optional<BoardEntity> boardEntity = boardService.findById(id);

        model.addAttribute("chchch", boardEntity.get());

        return "update";
    }

    @PostMapping("/board/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute BoardEntity boardEntity, @RequestParam("file") MultipartFile file) throws IOException {

        System.out.println("수정 전 엔터티다 : " + boardEntity);
        System.out.println("이밈임이미임임이ㅣㅁ임이미임이미임이밍 : " + file);

        if(file == null || file.isEmpty()) {
            Optional<BoardEntity> boardEntity1 = boardService.findById(id);

            boardEntity.setImgpath(boardEntity1.get().getImgpath());
            boardEntity.setImgname(boardEntity1.get().getImgname());

            boardService.update(boardEntity);
            System.out.println("글자들만 수정 후 엔터티 내용이다 : " + boardEntity);
        } else {

            boardService.update2(boardEntity, file);
            System.out.println("이미지도 수정 이후 엔터티 내용이다 : " + boardEntity);
        }

        return "redirect:/board/list";
    }


}
