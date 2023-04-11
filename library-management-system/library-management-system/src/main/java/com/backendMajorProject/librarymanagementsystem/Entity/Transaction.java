package com.backendMajorProject.librarymanagementsystem.Entity;

import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String transactionNumber;

    @Enumerated(EnumType.STRING)
    TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date transactionDate;

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
