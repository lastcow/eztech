package me.chen.eztech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class FileItemDto {

    private String name;
    private Date dateModified;
    private boolean isDirectory;
    private long size;
    private boolean hasSubDirectories;
}
