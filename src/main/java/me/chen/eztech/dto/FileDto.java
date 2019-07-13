package me.chen.eztech.dto;


import lombok.Data;

import java.util.List;

@Data
public class FileDto {

    private boolean success;
    private String errorId;
    private List<FileItemDto> result;
}
