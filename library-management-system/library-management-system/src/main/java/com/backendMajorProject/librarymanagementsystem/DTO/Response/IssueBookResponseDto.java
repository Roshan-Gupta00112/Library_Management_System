package com.backendMajorProject.librarymanagementsystem.DTO.Response;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.ReturnBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.Entity.LibraryCard;
import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssueBookResponseDto {

    private String transactionId;

    private int bookId;

    private String bookName;

    private String authorName;

    //private TransactionStatus transactionStatus;
}

