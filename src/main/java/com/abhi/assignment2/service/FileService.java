package com.abhi.assignment2.service;

import com.abhi.assignment2.entity.Account;

import com.abhi.assignment2.repository.FileRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Value("${file.upload-dir}")
    private String filedirectory;


    public Account addFile(MultipartFile upload) throws IOException {

        try{

            String fileName = StringUtils.cleanPath(upload.getOriginalFilename());
            long size = upload.getSize();
            Path path = Paths.get(filedirectory, upload.getOriginalFilename());
            String s=path.toString();
            Account a=new Account();
            a.setDocType(new Binary(BsonBinarySubType.BINARY, upload.getBytes()).toString());
            a.setFileName(fileName);
            a.setSize(size);
            a.setDownloadUri(s);
            Account savedaccount=fileRepository.save(a);
            System.out.println(savedaccount);
            return savedaccount;

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

}
