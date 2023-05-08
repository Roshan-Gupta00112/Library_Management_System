package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.IssueBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.IssueBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all-successful-transaction")
    public String getAllSuccessfulTxns(@RequestParam("cardId") int cardID){

        return transactionService.getAllSuccessfulTxns(cardID);
    }
}
