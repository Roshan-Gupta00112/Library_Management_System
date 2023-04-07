package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.BookRequestDTO;
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
    public String addBook(@RequestBody BookRequestDTO bookRequestDTO){
        try {
            bookService.addBook(book);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage()+"Book not added");
        }
        return "Book added successfully";
    }


    @GetMapping("/get-all-books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
}
