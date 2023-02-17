package com.abhi.assignment2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "as2accounts")
@Data
@NoArgsConstructor
public class Account {

    public Account(String docType) {
        this.docType = docType;
    }

    @Id
    private String id;
    @Version
    private Long  version;
    private String docType;

}
