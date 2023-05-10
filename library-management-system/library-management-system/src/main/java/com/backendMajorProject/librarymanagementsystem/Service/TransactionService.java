package com.backendMajorProject.librarymanagementsystem.Service;


import com.backendMajorProject.librarymanagementsystem.DTO.Request.IssueBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Request.ReturnBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.IssueBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.TransactionResponseDto;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

        // 1st Step:- Create Transaction Object
        Transaction transaction= new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);  // Because we are issuing a book
        transaction.setTransactionDate(LocalDate.now());

        // 2nd Step:- Checking the Validation of Card ID
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

        // 3rd Step:- Checking the Validation of BookID
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

        // 4th Step:- Checking Whether the Student already issued the same Book and hasn't returned yet
        List<Book> allIssuedBooks=card.getBooksIssued();
        for (Book b:allIssuedBooks){
            if(b.getId()==issueBookRequestDto.getBookId()){
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setMessage("You have already issued the same book and hasn't returned yet");
                transactionRepository.save(transaction);
                throw new Exception("You have already issued the same book and hasn't returned yet");
            }
        }

        // 5th Step:- Checking whether there is any count of the Particular Book is left or Not
        if(book.getQuantity()==0){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! currently the book " +book.getTitle()+ " is out of stock");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! All Book is already issued.");
        }
        // Now student can issue the Book
        transaction.setBook(book);


        // 6th Step:- Checking Whether Card is Activated or not
        if(card.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not Activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not Activated");
        }



        // Now, I can issue the Book or I can do the Transaction Successfully. So,
        // 7th Step:- Issuing a Book by Decreasing the book Quantity by 1
        book.setQuantity(book.getQuantity()-1);

        // 8th Step:- Setting the Parameters of Book
        book.setLibraryCard(card);
        book.getTransactionList().add(transaction);

        // 9th Step:- setting the parameters of Card Object
        card.getTransactionList().add(transaction);
        card.getBooksIssued().add(book);

        // 10th Step:- Setting the Parameters of Transaction Object
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful and you have successfully issued the book");
       // transactionRepository.save(transaction);  -- Bcoz of line 112 it will automatically get saved


        // 11th Step:- Saving the Objects
        // Since, "Card" is the Parent class of both "Book & Transaction". So, if we save the Card object then the
        //  Book and transaction object automatically get saved because of CASCADE Operation
        cardRepository.save(card); // This will save Book and Transaction also

        // 12th Step:- Preparing Response Dto
        IssueBookResponseDto issueBookResponseDto=new IssueBookResponseDto(transaction.getTransactionNumber(),
                book.getId(), book.getTitle(), book.getAuthor().getName());
        //issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        //issueBookResponseDto.setBookName(book.getTitle());
        //issueBookResponseDto.setTransactionStatus(transaction.getTransactionStatus());

        // 13th Step:- Sending an Email
        String text="Congrats!. " + card.getStudent().getName()+ " You have been issued " +book.getTitle()+ " book";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backenddefaultmailid@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);


        return issueBookResponseDto;

    }


    // Returning A BOOK
    public String returnBook(ReturnBookRequestDto returnBookRequestDto) throws Exception{

        // 1st Step:- Checking Fine
        if(isFine(returnBookRequestDto.getTransactionNumber())){
            int fine=calculateFine(returnBookRequestDto.getTransactionNumber());
            throw new Exception("Your have to pay fine of rs" +fine+ " first!");
        }

        // 2nd Step:- Creating Transaction Object
        Transaction transaction=new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setTransactionDate(LocalDate.now());
        transaction.setIssueOperation(false);  // Bcoz here we are returning a book



        // 3rd Step:- Checking Card Validity
        LibraryCard card;
        try {
            card=cardRepository.findById(returnBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Card Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card Id");
        }
        // Now, Card is Valid
        transaction.setLibraryCard(card);


        // 3rd Step:- Checking BooK Id
        Book book;
        try {
            book=bookRepository.findById(returnBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book Id");
            throw new Exception("Invalid Book Id");
        }
        // Now Book is Valid


        // Now we can return the book Successfully which means increasing the Book quantity by 1
        book.setQuantity(book.getQuantity()+1);
        book.setLibraryCard(null);  // because it's return book

        // 4th Step:- Setting the Parameters of Transaction
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful and you have successfully returned the book");
        transaction.setBook(book);

        // 5th Step:- Setting Parameters of Book
        book.setLibraryCard(card);
        List<Transaction> transactionList=book.getTransactionList();
        transactionList.add(transaction);
        book.setTransactionList(transactionList);

        // 6th Step:- Setting Parameters of Card
        List<Transaction> transactions=card.getTransactionList();
        transactions.add(transaction);
        card.setTransactionList(transactions);
        List<Book> books=card.getBooksIssued();
        for (Book b:books){
            if(b.getId()==book.getId()){
                books.remove(b);
            }
        }


        // Now Saving the changes in DB
        // Since Card is the Parent class of Both Transaction and Book class. So saving Card in the Db will save
        // Transaction and Book Object in the Db bcoz of CASCADE Operation
        cardRepository.save(card);


        return "You have successfully returned "  +book.getTitle()+ " book";


    }
    private boolean isFine(String transactionNumber){
        // Getting the Transaction Object from the DB
        Transaction transaction=transactionRepository.findByTransactionNumber(transactionNumber);

        LocalDate issueDate=transaction.getTransactionDate();

        if(issueDate.equals(LocalDate.now())) return false; // when book is returned on the same date

        int days= (int) ChronoUnit.DAYS.between(issueDate, LocalDate.now());

        if(days>=14) return true;

        return false;

    }
    private int calculateFine(String transactionNumber){
        // Getting the Transaction Object from the DB
        Transaction transaction=transactionRepository.findByTransactionNumber(transactionNumber);

        LocalDate issueDate=transaction.getTransactionDate();

        int days= (int) ChronoUnit.DAYS.between(issueDate, LocalDate.now());

        return 5*(days-14);

    }

    // Getting List of All Successful Transaction Done by User
    public String getAllSuccessfulTxns(int cardId){
        List<Transaction> txns=transactionRepository.getAllSuccessfulTxns(cardId);

        String ans ="";
        for(Transaction t:txns){
            ans+=t.getTransactionNumber();
            ans+="\n";
        }

        return ans;
    }



    // Getting List of all Failed Transaction Done by User
    public String getAllFailedTxns(int cardId){
        List<Transaction> transactions=transactionRepository.getAllFailedTxns(cardId);
        String ans="";
        for (Transaction t:transactions){
            ans+=t.getTransactionNumber();
            ans+="\n";
        }

        return ans;
    }



    // Getting List of all Successful Transaction done by User for Issuing a Book
    public List<TransactionResponseDto> getAllSuccessTransactionForIssueBook(int cardId){
        // Getting list of Transaction Object from the DB
        List<Transaction> transactions=transactionRepository.getAllSuccessTransactionForIssue(cardId);

        List<TransactionResponseDto> transactionResponseDtoList=new ArrayList<>();

        for(Transaction transaction:transactions){
            TransactionResponseDto transactionResponseDto=new TransactionResponseDto(transaction.getTransactionNumber(),
                    transaction.getTransactionDate());

            transactionResponseDtoList.add(transactionResponseDto);
        }

        return transactionResponseDtoList;
    }


    // Getting List of all Successful Transaction done by User to Return a Book
    public List<TransactionResponseDto> getAllSuccessTransactionForReturnBook(int cardId){

        // Getting Transaction List from DB
        List<Transaction>transactions=transactionRepository.getAllSuccessTransactionForReturn(cardId);

        List<TransactionResponseDto> transactionResponseDtoList=new ArrayList<>();

        for (Transaction t:transactions){
            TransactionResponseDto transactionResponseDto=new TransactionResponseDto(t.getTransactionNumber(),
                    t.getTransactionDate());

            transactionResponseDtoList.add(transactionResponseDto);
        }

        return transactionResponseDtoList;
    }
}
