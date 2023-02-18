package com.abhi.assignment2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Document(collection = "as2accounts")
@Data
@NoArgsConstructor
public class Account {

    @Id
    private String id;
    private String docType;
    private String fileName;
    private String downloadUri;
    private long size;



}
