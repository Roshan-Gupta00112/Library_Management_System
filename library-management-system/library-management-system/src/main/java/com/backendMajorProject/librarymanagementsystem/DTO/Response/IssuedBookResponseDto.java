package com.backendMajorProject.librarymanagementsystem.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssuedBookResponseDto {

    private String transactionNumber;

    private LocalDate issuedDate;

    private int bookId;

    private String bookName;

    private String authorName;
}
