package com.backendMajorProject.librarymanagementsystem.Entity;

import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @CreatedDate
    private Date transactionDate;

    private boolean isIssueOperation;   // It tells whether a student is issue or Return a book where true is for issue

    @ManyToOne
    @JoinColumn
    Book book;


    @ManyToOne
    @JoinColumn
    LibraryCard libraryCard;
}
