package com.backendMajorProject.librarymanagementsystem.Repository;

import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Custom COMPLEX QUERY
    // to Get the List of all Successful Transaction done by a User with given CardId
    @Query(value = "select * from transaction t where t.library_card_card_no=:cardId AND t.transaction_status='SUCCESS'",
            nativeQuery = true)
    List<Transaction> getAllSuccessfulTxnsWithCardNo(int cardId);
}
