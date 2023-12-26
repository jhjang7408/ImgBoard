package com.temisone.imgboard.service;

import com.temisone.imgboard.entity.BoardEntity;
import com.temisone.imgboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(BoardEntity boardEntity, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";

        UUID uuid = UUID.randomUUID();

        String imgname = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, imgname);

        file.transferTo(saveFile);

        boardEntity.setImgname(imgname);

        boardEntity.setImgpath("/" + imgname);

        boardRepository.save(boardEntity);
    }


    public List<BoardEntity> findAll(){
        return boardRepository.findAll();
    }


}
