package com.backendMajorProject.librarymanagementsystem.Repository;

import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthor(Author author);

    // Custom Complex Query
//    @Query(value = "select * from book b where b.number_of_pages=:(select MAX(b.number_of_pages) from book)", nativeQuery = true)
//    List<Book> getBooksWithMaximumPages();


    // Native Complex Query for Max Pages
    @Query(value = "select * from book where number_of_pages=(select MAX(number_of_pages)from book)", nativeQuery = true)
    List<Book> getBooksWithMaximumPages();

    // Native Complex Query for Max Price
    @Query(value = "select * from book where price=(select MAX(price) from book) ", nativeQuery = true)
    List<Book> getBooksWithMaximumPrice();


    // Native Complex Query for 2nd Max Pages
    @Query(value = "select * from book where number_of_pages= " +
            "(select MAX(number_of_pages) from book where number_of_pages<(select MAX(number_of_pages) from book))",
            nativeQuery = true)
    List<Book> booksHavingSecondMaxPages();

    // Native Complex Query for 2nd Max Price
    @Query(value = "select *from book where price=(select MAX(price) from book where price<(select MAX(price) from book ))", nativeQuery = true)
    List<Book> booksHavingSecondMaxPrice();
}
