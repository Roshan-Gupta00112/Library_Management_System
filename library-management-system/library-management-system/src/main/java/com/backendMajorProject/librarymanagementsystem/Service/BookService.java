package com.backendMajorProject.librarymanagementsystem.Service;

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

    public String addBook(Book book) throws Exception {

        Author author;
        try{
            author=authorRepository.findById(book.getAuthor().getId()).get();
        }catch (Exception e){
            throw new Exception("Invalid author id");
        }

        List<Book> myBooks=author.getBooks();
        myBooks.add(book);

        authorRepository.save(author);  // Since we have done changes so saving the author again

        return "Book added successfully";

    }



    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
