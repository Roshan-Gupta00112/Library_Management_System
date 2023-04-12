package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import com.backendMajorProject.librarymanagementsystem.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto) throws Exception {
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
    public String getAllSuccessfulTxnsWithCardNo(@RequestParam("cardId") int cardID){

        return transactionService.getAllSuccessfulTxnsWithCardNo(cardID);
    }
}
