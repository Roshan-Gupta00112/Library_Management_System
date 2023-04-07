package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.AuthorRequestDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void addAuthor(AuthorRequestDto authorRequestDto){

        Author author= new Author();

        // Setting Attributes of Author object
        author.setName(authorRequestDto.getName());
        author.setDob(authorRequestDto.getDob());
        author.setEmail(authorRequestDto.getEmail());

        authorRepository.save(author);
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }
}
