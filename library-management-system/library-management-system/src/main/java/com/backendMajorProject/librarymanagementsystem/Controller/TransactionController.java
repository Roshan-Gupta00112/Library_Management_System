package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.IssueBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Request.ReturnBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.IssueBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.TransactionResponseDto;
import com.backendMajorProject.librarymanagementsystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto) {
        IssueBookResponseDto issueBookResponseDto;
        try {
            issueBookResponseDto= transactionService.issueBook(issueBookRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(issueBookResponseDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/return")
    public ResponseEntity returnBook(@RequestBody ReturnBookRequestDto returnBookRequestDto) {
        String str="";
        try {
            str=transactionService.returnBook(returnBookRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(str, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all-successful-transaction")
    public String getAllSuccessfulTxns(@RequestParam("cardId") int cardID){

        return transactionService.getAllSuccessfulTxns(cardID);
    }

    @GetMapping("/all-failed-transaction")
    public String getAllFailedTxns(@RequestParam("id") int cardId){
        return transactionService.getAllFailedTxns(cardId);
    }

    @GetMapping("/successful-transaction-for-issue-book")
    public List<TransactionResponseDto> getAllSuccessTransactionForIssueBook(@RequestParam("id") int cardId){
        return transactionService.getAllSuccessTransactionForIssueBook(cardId);
    }

    @GetMapping("/successful-transaction-for-return-book")
    public List<TransactionResponseDto> getAllSuccessTransactionForReturnBook(@RequestParam("id") int cardId){
        return transactionService.getAllSuccessTransactionForReturnBook(cardId);
    }
}
