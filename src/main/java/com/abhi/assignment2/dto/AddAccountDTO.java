package com.abhi.assignment2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Schema(description = "This is DTO object for new account addition")
public class AddAccountDTO {

    @Schema(description = "Primary Customer Name for this account")
    @NotNull
    @Length(min=4,max =60)
    private String customerName;
    @NotNull
    private Float accountBalance;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDate;
}
