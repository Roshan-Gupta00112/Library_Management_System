package com.backendMajorProject.librarymanagementsystem.Service;


import com.backendMajorProject.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Entity.LibraryCard;
import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import com.backendMajorProject.librarymanagementsystem.Enum.CardStatus;
import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import com.backendMajorProject.librarymanagementsystem.Repository.BookRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.CardRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        // Create Transaction Object
        Transaction transaction= new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        // 1st Step:- Checking the Validation of Card ID & Book ID
        LibraryCard card;
        try {
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Card ID");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card ID");
        }

        Book book;
        try {
            book=bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book Id");
        }

        // Now, both Card & Book are Valid
        transaction.setBook(book);
        transaction.setLibraryCard(card);

        // 2nd Step:- Checking Whether Card is Activated or not and Book is already issued or NOT
        if(card.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not Activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not Activated");
        }
        if(book.isIssued()==true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! Book is already issued");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! Book is already issued.");
        }

        // 3rd Step:- Now, I can issue the Book
        book.setIssued(true);
        // 4tth Step:- Setting the Parameters

        // Setting the Parameters of Book Object
        book.setLibraryCard(card);
        book.getTransactionList().add(transaction);

        // setting the parameters of Card Object
        card.getTransactionList().add(transaction);
        card.getBooksIssued().add(book);

        // Setting the Parameters of Transaction Object
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful");
        transactionRepository.save(transaction);


        // 4th Step:- Saving the Objects
        // Since, "Card" is the Parent class of both "Book & Transaction". So, if we save the Card object then the
        //  Book and transaction object automatically get saved because of CASCADE Operation
        cardRepository.save(card); // This will save Book and Transaction also

        // 5th Step:- Preparing Response Dto
        IssueBookResponseDto issueBookResponseDto=new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setBookName(book.getTitle());
        issueBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());
        return issueBookResponseDto;

    }
}
