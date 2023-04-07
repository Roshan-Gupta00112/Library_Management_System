package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.BookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public BookResponseDto addBook(@RequestBody BookRequestDto bookRequestDto) throws Exception {
        return bookService.addBook(bookRequestDto);
    }


    @GetMapping("/get-all-books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
}