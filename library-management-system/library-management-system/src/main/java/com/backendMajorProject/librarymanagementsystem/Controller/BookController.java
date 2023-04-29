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
    public String addBook(@RequestBody BookRequestDto bookRequestDto) throws Exception {
        return bookService.addBook(bookRequestDto);
    }


    @GetMapping("/get-all-books")
    public List<BookResponseDto> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/get-all-books-by-an-author")
    public List<BookResponseDto> getBooksOfAnAuthor(@RequestParam("authorId") int authorId){
        return bookService.getBooksOfAnAuthor(authorId);
    }

    @GetMapping("/count-of-books-by-an-author")
    public int noOfBooksByAnAuthor(@RequestParam("authorId") int authorId){
        return bookService.noOfBooksByAnAuthor(authorId);
    }


    @GetMapping("/books-with-maximum-pages")
    public List<BookResponseDto> booksWithMaximumPages(){
        return bookService.booksWithMaximumPages();
    }


    @GetMapping("/books-with-max-price")
    public List<BookResponseDto> booksWithMaxPrice(){
        return bookService.booksWithMaxPrice();
    }
}
