package com.backendMajorProject.librarymanagementsystem.Service;


import com.backendMajorProject.librarymanagementsystem.DTO.Request.IssueBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Request.ReturnBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.IssueBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Entity.LibraryCard;
import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import com.backendMajorProject.librarymanagementsystem.Enum.CardStatus;
import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import com.backendMajorProject.librarymanagementsystem.Repository.BookRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.CardRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private JavaMailSender emailSender;


    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        // Create Transaction Object
        Transaction transaction= new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        // 1st Step:- Checking the Validation of Card ID
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
        // Now, card is valid
        transaction.setLibraryCard(card);

        // 2nd Step:- Checking the Validation of BookID
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

        // Now, Book is Valid
        transaction.setBook(book);


        // 3rd Step:- Checking Whether Card is Activated or not
        if(card.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not Activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not Activated");
        }

        // 4th Step:- Checking whether the any count of the Particular Book is left or Not
        if(book.getQuantity()==0){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! currently the book " +book.getTitle()+ " is out of stock");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! All Book is already issued.");
        }

        // Now, I can issue the Book or I can do the Transaction Successfully
        // 5th Step:- Issuing a Book by Decreasing the book Quantity by 1
        book.setQuantity(book.getQuantity()-1);

        // 6th Step:- Setting the Parameters of Book
        book.setLibraryCard(card);
        book.getTransactionList().add(transaction);

        // 7th Step:- setting the parameters of Card Object
        card.getTransactionList().add(transaction);
        card.getBooksIssued().add(book);

        // 8th Step:- Setting the Parameters of Transaction Object
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful");
       // transactionRepository.save(transaction);  -- Bcoz of line 112 it will automatically get saved


        // 9th Step:- Saving the Objects
        // Since, "Card" is the Parent class of both "Book & Transaction". So, if we save the Card object then the
        //  Book and transaction object automatically get saved because of CASCADE Operation
        cardRepository.save(card); // This will save Book and Transaction also

        // 10th Step:- Preparing Response Dto
        IssueBookResponseDto issueBookResponseDto=new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setBookName(book.getTitle());
        issueBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());

        // 11th Step:- Sending an Email
        String text="Congrats!. " + card.getStudent().getName()+ " You have been issued " +book.getTitle()+ " book";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backenddefaultmailid@gmail.com");
        message.setTo(card.getStudent().getEmail());
        // Once Check for
        //message.setTo(card.getStudent().getMobNo());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);


        return issueBookResponseDto;

    }


    // Returning A BOOK
    public String returnBook(ReturnBookRequestDto returnBookRequestDto){

        //1st Step:- Creating Transaction Object


        // 1st Step:- Checking Card Validity
        LibraryCard card;

        try {
            card=cardRepository.findById(returnBookRequestDto.getCardId()).get();
        }
        catch (Exception e){

        }
    }

    public String getAllSuccessfulTxns(int cardId){
        List<Transaction> txns=transactionRepository.getAllSuccessfulTxns(cardId);

        String ans ="";
        for(Transaction t:txns){
            ans+=t.getTransactionNumber();
            ans+="\n";
        }

        return ans;
    }
}
