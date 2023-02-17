package com.abhi.assignment2.service;

import com.abhi.assignment2.entity.Account;

import com.abhi.assignment2.repository.FileRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    public String addFile(MultipartFile upload) throws IOException {

        try{
            Account a=new Account();
            a.setDocType(new Binary(BsonBinarySubType.BINARY, upload.getBytes()).toString());
//            demoDocument.setDocument(new Binary(BsonBinarySubType.BINARY, multipart.getBytes()));
            Account savedaccount=fileRepository.save(a);
            System.out.println(savedaccount);




        }catch (IOException e){
            e.printStackTrace();
            return "failure";
        }
        return "sucess";
    }

}
