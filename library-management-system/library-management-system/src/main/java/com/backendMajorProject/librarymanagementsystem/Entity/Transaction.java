package com.backendMajorProject.librarymanagementsystem.Entity;

import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String transactionNumber;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private LocalDate transactionDate;

    private boolean isIssueOperation;   // It tells whether a student is issue or Return a book where true is for issue

    private String message;
    // It will tell whether the Transaction Completed or Not and if Not then display the reason behind its failure

    @ManyToOne
    @JoinColumn
    Book book;


    @ManyToOne
    @JoinColumn
    LibraryCard libraryCard;
}
