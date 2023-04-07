package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.BookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Repository.AuthorRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Exception {

        // Get the Author Object
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();

        // Creating Book Object and Setting it's all Parameters
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setPrice(bookRequestDto.getPrice());
        book.setGenre(bookRequestDto.getGenre());
        book.setIssued(false);
        book.setAuthor(author);

        // Updating Parameters for Author Class
        author.getBooks().add(book);
        // will save both Book & Author because Author is the Parent Class having CASCADE for child(book)
        authorRepository.save(author);

        // Creating a Response DTO
        BookResponseDto bookResponseDto=new BookResponseDto();
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setPrice(book.getPrice());

        return bookResponseDto;

    }



    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
