package com.backendMajorProject.librarymanagementsystem.Repository;

import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Custom COMPLEX QUERY
    // to Get the List of all Successful Transaction done by a User with given CardId
    @Query(value = "select * from transaction t where t.library_card_card_no=:cardId AND t.transaction_status='SUCCESS'",
            nativeQuery = true)
    List<Transaction> getAllSuccessfulTxns(int cardId);

    // Getting List of all Failed Transaction done by a User
    @Query(value = "select * from transaction t where t.library_card_card_no=:cardId AND t.transaction_status='FAILED'",
           nativeQuery = true)
    List<Transaction> getAllFailedTxns(int cardId);


    // finding Transaction using TransactionNumber
    Transaction findByTransactionNumber(String transactionNumber);


    // Getting List of all Successful Transaction Done by User for Issuing a Book
    @Query(value = "select * from transaction t where t.library_card_card_no=:cardId AND t.transaction_status='SUCCESS' AND t.is_issue_operation=true",
            nativeQuery = true)
    List<Transaction> getAllSuccessTransactionForIssue(int cardId);


    // Getting List of all Successful Transaction Done by User for Returning a Book
    @Query(value = "select * from transaction t where t.library_card_card_no=:cardId AND t.transaction_status='SUCCESS' AND t.is_issue_operation=false",
            nativeQuery = true)
    List<Transaction> getAllSuccessTransactionForReturn(int cardId);
}
