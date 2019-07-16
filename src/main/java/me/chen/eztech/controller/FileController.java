package me.chen.eztech.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.chen.eztech.dto.FileDto;
import me.chen.eztech.dto.FileItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {

    @Value("${file.root}")
    private String fileRoot;

    @GetMapping("/FileManagerApi")
    public FileDto getFiles(@RequestParam("command") String command, @RequestParam("arguments") String arguments){

        FileDto fileDto = new FileDto();
        JsonObject argumentObj = new JsonParser().parse(arguments).getAsJsonObject();

        String parentId = argumentObj.get("parentId").getAsString();
        String rootDir = fileRoot;


        if(parentId == null || parentId.length() == 0){
            rootDir = fileRoot;
        }else{
            rootDir = fileRoot + parentId;
        }

        String dir = rootDir;

        List<FileItemDto> fileItemDtos = new ArrayList<>();

        try {
            List<File> fileList = Files.walk(Paths.get(rootDir), 1)
                    .filter(path -> !path.equals(Paths.get(dir)))
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            fileList.forEach(file -> {
                fileItemDtos.add(new FileItemDto(file.getName(), new Date(file.lastModified()), file.isDirectory(), file.length(), false));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileDto.setSuccess(true);

        fileDto.setResult(fileItemDtos);

        return fileDto;
    }


    @PostMapping("/FileManagerApi")
    @ResponseStatus(HttpStatus.OK)
    public void fileManager(@RequestParam("command") String command,
                            @RequestParam("arguments") String arguments,
                            @RequestPart(value = "chunk", required = false) byte[] chunk,
                            final HttpServletRequest request){

        System.out.println("==== Reading");

        JsonObject argumentObj = new JsonParser().parse(arguments).getAsJsonObject();

        if(command.equalsIgnoreCase("CreateDir")){
            // Create folder
            // Get root folder
            String dir = fileRoot+argumentObj.get("parentId").getAsString();
            String folderName = argumentObj.get("name").getAsString();

            if(folderName != null && folderName.length()>0){
                // Crate folder
                try {
                    Files.createDirectories(Paths.get(dir+File.separator + folderName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(command.equalsIgnoreCase("UploadChunk")){
            // Upload file
            // Get input stream
            try {

//                byte[] chunks = ((StandardMultipartHttpServletRequest) request).getMultiFileMap().get("chunk").get(0).getBytes();
                Path path = Paths.get("/Users/lastcow/upload/test.jpeg");

                Files.write(path, chunk);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * Upload file
     * @param file
     * @return
     */
    @PostMapping("/file/upload")
    public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file){

        if(file.isEmpty()){
            return ResponseEntity.ok(null);
        }

        try{
            // Get the file and save it to disk
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileRoot + file.getOriginalFilename());
            Files.write(path, bytes);

        }catch (IOException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(null);
    }
}
