package me.chen.eztech.controller;

import me.chen.eztech.dto.FileDto;
import me.chen.eztech.dto.FileItemDto;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {


    @GetMapping("/FileManagerApi")
    public FileDto getFiles(@RequestParam("command") String command, @RequestParam("arguments") String arguments){

        FileDto fileDto = new FileDto();
        fileDto.setSuccess(true);

        List<FileItemDto> fileItemDtos = new ArrayList<>();

        FileItemDto fileItemDto = new FileItemDto("Images", new Date(System.currentTimeMillis()), false, 1394, false);

        fileItemDtos.add(fileItemDto);

        fileDto.setResult(fileItemDtos);

        return fileDto;
    }
}
