package com.backendMajorProject.librarymanagementsystem.DTO.Response;


import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookTransactionDetailsResponseDto {

    private String transactionNumber;

    private LocalDate transactionDate;

    private boolean isIssueOperation;

    private TransactionStatus transactionStatus;
}
