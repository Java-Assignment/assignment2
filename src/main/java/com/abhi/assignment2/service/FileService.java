package com.abhi.assignment2.service;

import com.abhi.assignment2.dto.AccountDTO;
import com.abhi.assignment2.entity.Account;

import com.abhi.assignment2.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Value("${file.upload-dir}")
    private String filedirectory;

    private  static  String fileName;


    public Account addFile(MultipartFile upload) throws IOException {

        try{

            fileName = StringUtils.cleanPath(upload.getOriginalFilename());
            long size = upload.getSize();
            Path path = Paths.get(filedirectory, upload.getOriginalFilename());
            String s=path.toString();
            Account a=new Account();
            a.setId(a.getId());
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

    public String getAccounts(String id) throws FileNotFoundException {
        Optional<Account> ac = fileRepository.findById(id);
         String filename= ac.get().getFileName();
        List<String> account=new ArrayList<>();
        String fileContent = "";
        if (ac.isPresent()) {
            Path path = Paths.get(filedirectory,filename);
            File f = new File(path.toUri());
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                fileContent = fileContent.concat(sc.nextLine()+"\n");
                account.add(fileContent);
            }
        } else{
            return  "File not found ";
        }
        return  fileContent;
        }

    }

