package com.abhi.assignment2.externalsvc.custrefsvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private String customerName;
    private int age;
    private String relationship;
    private String address;
}
