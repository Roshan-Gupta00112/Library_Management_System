package com.backendMajorProject.librarymanagementsystem.Repository;

import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findByName(String name);

    List<Author> findByDob(String dob);

    Author findByEmail(String email);
}
