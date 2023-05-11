package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.BookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Request.BookCountRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.BookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.BookTransactionDetailsResponseDto;
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

    @PutMapping("/increase-particular-book-count")
    public BookResponseDto increaseParticularBookCount(@RequestBody BookCountRequestDto bookCountRequestDto){
        return bookService.increaseParticularBookCount(bookCountRequestDto);
    }

    @PutMapping("/decrease-particular-book-count")
    public BookResponseDto decreaseParticularBookCount(@RequestBody BookCountRequestDto bookCountRequestDto){
        return bookService.decreaseParticularBookCount(bookCountRequestDto);
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

    // Finding the Book with Max Pages without using custom native query
    @GetMapping("/books-with-max-pages")
    public List<BookResponseDto> booksWithMaxPages(){
        return bookService.booksWithMaxPages();
    }

    // Finding the Book with Max Price without using custom native query
    @GetMapping("/books-with-max-price")
    public List<BookResponseDto> booksWithMaxPrice(){
        return bookService.booksWithMaxPrice();
    }


    // Finding the Book with Max Pages USING Custom Native Query
    @GetMapping("/books-with-maximum-pages")
    public List<BookResponseDto> booksWithMaximumPages(){
        return bookService.booksWithMaximumPages();
    }

    // Finding the Book with Max Price USING Custom Native Query
    @GetMapping("/books-with-maximum-price")
    public List<BookResponseDto> booksWithMaximumPrice(){
        return bookService.booksWithMaximumPrice();
    }

    @GetMapping("/books-with-second-max-pages")
    public List<BookResponseDto> booksHavingSecondMaxPages(){
        return bookService.booksHavingSecondMaxPages();
    }

    @GetMapping("/books-with-second-max-price")
    public List<BookResponseDto> booksHavingSecondMaxPrice(){
        return bookService.booksHavingSecondMaxPrice();
    }

    @GetMapping("/books-with-maximum-quantity")
    public List<BookResponseDto> booksWithMaximumQuantity(){
        return bookService.booksWithMaximumQuantity();
    }

    @GetMapping("/transaction-details-of-book")
    public List<BookTransactionDetailsResponseDto> bookTransactionDetails(@RequestParam("id") int bookId){
        return bookService.bookTransactionDetails(bookId);
    }



    @DeleteMapping("/delete-all-the-counts-of-particular-book")
    public String removeParticularBooksFromLibrary(@RequestParam("bookId") int bookId){
        return bookService.removeParticularBooksFromLibrary(bookId);
    }
}
