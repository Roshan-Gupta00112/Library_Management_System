package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.BookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Request.ParticularBookRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.BookResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Repository.AuthorRepository;
import com.backendMajorProject.librarymanagementsystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
        Author author;
        try {
            author = authorRepository.findById(bookRequestDto.getAuthorId()).get();
        }
        catch (Exception e){
            throw new Exception("Author not present");
        }

        // Creating Book Object and Setting it's all Parameters
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setPrice(bookRequestDto.getPrice());
        book.setNumberOfPages(bookRequestDto.getNumberOfPages());
        book.setQuantity(bookRequestDto.getQuantity());
        //book.setIssued(false);
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
        bookResponseDto.setQuantity(book.getQuantity());
        bookResponseDto.setGenre(bookRequestDto.getGenre()); // we can also write book.getGenre()
        bookResponseDto.setAuthorName(author.getName());
        bookResponseDto.setPrice(book.getPrice());

        return "book added successfully!";

    }


    public BookResponseDto addParticularBook(ParticularBookRequestDto particularBookRequestDto){

        // Getting Book Object from the DB
        Book book=bookRepository.findById(particularBookRequestDto.getBookId()).get();

        // Increasing the Book Quantity
        book.setQuantity(book.getQuantity()+ particularBookRequestDto.getQuantity());

        // Saving Book in the DB
        Book updatedBook=bookRepository.save(book);


        // Creating BookResponseDto and Setting it's all attributes using All Args Constructor
        BookResponseDto bookResponseDto=new BookResponseDto(book.getTitle(), book.getNumberOfPages(), book.getQuantity(),
                book.getGenre(), book.getAuthor().getName(), book.getPrice());

        return bookResponseDto;

        // Note:- Since we are adding Particular Book which are already in the Library which means we are Increasing
        //        the count of that book. So, here no need to add that book in the Book List of the Author again

    }

    public List<BookResponseDto> getAllBooks(){

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        List<Book> bookList=bookRepository.findAll();

        for(Book book:bookList){
            BookResponseDto bookResponseDto=new BookResponseDto();

            bookResponseDto.setTitle(book.getTitle());
            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
            bookResponseDto.setQuantity(book.getQuantity());
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
            BookResponseDto bookResponseDto=new BookResponseDto(book.getTitle(), book.getNumberOfPages(), book.getQuantity(),
                    book.getGenre(), book.getAuthor().getName(), book.getPrice());

//            bookResponseDto.setTitle(book.getTitle());
//            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
//            bookResponseDto.setGenre(book.getGenre());
//            bookResponseDto.setAuthorName(book.getAuthor().getName());
//            bookResponseDto.setPrice(book.getPrice());

            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }


    public int noOfBooksByAnAuthor(int authorId){
        Author author=authorRepository.findById(authorId).get();

        //List<Book> bookList=author.getBooks();
        //return bookList.size();

        // we can also write above as
        return author.getBooks().size();
    }


    // Finding Books with Maximum pages Without Using Complex Native Query
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


    // Finding Books with Maximum Price Without Using Complex Native Query
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




    // Finding Books with Maximum pages USing Complex Native Query
    public List<BookResponseDto> getBooksWithMaximumPages(){

        // Getting Book Objects from CUSTOM COMPLEX QUERY
        List<Book> bookList=bookRepository.getBooksWithMaximumPages();

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){
            // creating BookResponseDto & Setting it's all Attributes
            BookResponseDto bookResponseDto=new BookResponseDto(book.getTitle(),book.getNumberOfPages(),book.getQuantity(),
                    book.getGenre(), book.getAuthor().getName(), book.getPrice());
//            bookResponseDto.setTitle(book.getTitle());
//            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
//            bookResponseDto.setGenre(book.getGenre());
//            bookResponseDto.setAuthorName(book.getAuthor().getName());
//            bookResponseDto.setPrice(book.getPrice());

            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }


    // Finding Books with Maximum Price USING Complex Native Query
    public List<BookResponseDto> getBooksWithMaximumPrice(){

        // Getting Book Objects from CUSTOM COMPLEX QUERY
        List<Book> bookList=bookRepository.getBooksWithMaximumPrice();

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){
            // creating BookResponseDto & Setting it's all Attributes
            BookResponseDto bookResponseDto=new BookResponseDto(book.getTitle(),book.getNumberOfPages(),book.getQuantity(),
                    book.getGenre(), book.getAuthor().getName(), book.getPrice());
//            bookResponseDto.setTitle(book.getTitle());
//            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
//            bookResponseDto.setGenre(book.getGenre());
//            bookResponseDto.setAuthorName(book.getAuthor().getName());
//            bookResponseDto.setPrice(book.getPrice());

            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }


    public List<BookResponseDto> booksHavingSecondMaxPages(){
        // Getting Book Objects from CUSTOM COMPLEX QUERY
        List<Book> bookList=bookRepository.booksHavingSecondMaxPages();

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){
            // creating BookResponseDto & Setting it's all Attributes
            BookResponseDto bookResponseDto=new BookResponseDto(book.getTitle(),book.getNumberOfPages(),book.getQuantity(),
                    book.getGenre(), book.getAuthor().getName(), book.getPrice());
//            bookResponseDto.setTitle(book.getTitle());
//            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
//            bookResponseDto.setGenre(book.getGenre());
//            bookResponseDto.setAuthorName(book.getAuthor().getName());
//            bookResponseDto.setPrice(book.getPrice());

            // Adding to Final List
            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }

    @GetMapping("/books-with-second-max-price")
    public List<BookResponseDto> booksHavingSecondMaxPrice(){

        // Getting Book Objects from CUSTOM COMPLEX QUERY
        List<Book> bookList=bookRepository. booksHavingSecondMaxPrice();

        List<BookResponseDto> bookResponseDtoList=new ArrayList<>();

        for(Book book:bookList){
            // creating BookResponseDto & Setting it's all Attributes
            BookResponseDto bookResponseDto=new BookResponseDto(book.getTitle(), book.getNumberOfPages(), book.getQuantity(),
                    book.getGenre(), book.getAuthor().getName(), book.getPrice());
//            bookResponseDto.setTitle(book.getTitle());
//            bookResponseDto.setNumberOfPages(book.getNumberOfPages());
//            bookResponseDto.setGenre(book.getGenre());
//            bookResponseDto.setAuthorName(book.getAuthor().getName());
//            bookResponseDto.setPrice(book.getPrice());

            // Adding to Final List
            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }


    public String removeParticularBooksFromLibrary(int bookId){
        // Getting Book Object from DB
        Book book=bookRepository.findById(bookId).get();

        // Removing Book from Author's BookList
        book.getAuthor().getBooks().remove(book);

        // Now removing Book from DB
        bookRepository.delete(book);

        return "All " +book.getTitle()+ " are removed from the Library!";

    }
}
