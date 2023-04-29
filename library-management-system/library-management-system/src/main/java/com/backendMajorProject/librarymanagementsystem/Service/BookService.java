package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.BookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Repository.AuthorRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public String addBook(BookRequestDto bookRequestDto) throws Exception {

        // Get the Author Object
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();

        // Creating Book Object and Setting it's all Parameters
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setPrice(bookRequestDto.getPrice());
        book.setNumberOfPages(bookRequestDto.getNumberOfPages());
        book.setIssued(false);
        book.setGenre(bookRequestDto.getGenre());
        book.setAuthor(author);

        // Updating Parameters for Author Class
        author.getBooks().add(book);
        // will save both Book & Author because Author is the Parent Class having CASCADE for child(book)
        authorRepository.save(author);

        // Creating a Response DTO
        BookResponseDto bookResponseDto=new BookResponseDto();

        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setNumberOfPages(book.getNumberOfPages());
        bookResponseDto.setGenre(bookRequestDto.getGenre()); // we can also write book.getGenre()
        bookResponseDto.setAuthorName(author.getName());
        bookResponseDto.setPrice(book.getPrice());

        return "book added successfully!";

    }



    public List<BookResponseDto> getAllBooks(){

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        List<Book> bookList=bookRepository.findAll();

        for(Book book:bookList){
            BookResponseDto bookResponseDto=new BookResponseDto();

            bookResponseDto.setTitle(book.getTitle());
            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
            bookResponseDto.setGenre(book.getGenre());
            bookResponseDto.setAuthorName(book.getAuthor().getName());
            bookResponseDto.setPrice(book.getPrice());

            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }



    public List<BookResponseDto> getBooksOfAnAuthor(int authorId){
        List<Book> bookList=bookRepository.findByAuthor(authorRepository.findById(authorId).get());

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){
            BookResponseDto bookResponseDto=new BookResponseDto();

            bookResponseDto.setTitle(book.getTitle());
            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
            bookResponseDto.setGenre(book.getGenre());
            bookResponseDto.setAuthorName(book.getAuthor().getName());
            bookResponseDto.setPrice(book.getPrice());

            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }


    public int noOfBooksByAnAuthor(int authorId){
        Author author=authorRepository.findById(authorId).get();

        List<Book> bookList=author.getBooks();

        return bookList.size();
    }


    public List<BookResponseDto> booksWithMaximumPages(){
        List<Book> bookList=bookRepository.findAll();

        Collections.sort(bookList, (a,b) ->{
            return b.getNumberOfPages()-a.getNumberOfPages();
        });

        int maxPages=bookList.get(0).getNumberOfPages();

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){
            if(book.getNumberOfPages()==maxPages){
                BookResponseDto bookResponseDto=new BookResponseDto();

                bookResponseDto.setTitle(book.getTitle());
                bookResponseDto.setNumberOfPages(book.getNumberOfPages());
                bookResponseDto.setGenre(book.getGenre());
                bookResponseDto.setAuthorName(book.getAuthor().getName());
                bookResponseDto.setPrice(book.getPrice());

                bookResponseDtoList.add(bookResponseDto);
            }

            else break;
        }

        return bookResponseDtoList;
    }


    public List<BookResponseDto> booksWithMaxPrice(){
        List<Book> bookList=bookRepository.findAll();

        Collections.sort(bookList, (a,b) ->{
            return b.getPrice()-a.getPrice();
        });

        int maxPrice=bookList.get(0).getPrice();

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){

            if(book.getPrice()==maxPrice){

                BookResponseDto bookResponseDto=new BookResponseDto();

                bookResponseDto.setTitle(book.getTitle());
                bookResponseDto.setNumberOfPages(book.getNumberOfPages());
                bookResponseDto.setGenre(book.getGenre());
                bookResponseDto.setAuthorName(book.getAuthor().getName());
                bookResponseDto.setPrice(book.getPrice());

                bookResponseDtoList.add(bookResponseDto);
            }
            else break;
        }

        return bookResponseDtoList;
    }
}
