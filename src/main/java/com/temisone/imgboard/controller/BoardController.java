package com.temisone.imgboard.controller;

import com.temisone.imgboard.entity.BoardEntity;
import com.temisone.imgboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


}
