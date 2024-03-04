package com.blogapplication.blogapplication.service.impl;

import com.blogapplication.blogapplication.service.FileService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Component

public class FileServiceImp implements FileService {
    @Override
    //upload image
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name
        String name=file.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));
        //Full path
        String filePath=path+ File.separator+fileName1;
        File f=new File(path);
        //create folder if not created
        if(!f.exists())
        {
            f.mkdir();
        }
        //copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName1;
    }
    //show image
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
}
