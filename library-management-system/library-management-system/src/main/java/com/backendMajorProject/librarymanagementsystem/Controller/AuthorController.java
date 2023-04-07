package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.AuthorRequestDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public String addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        authorService.addAuthor(authorRequestDto);

        return "Author added successfully";
    }

    @GetMapping("/get-All-Authors")
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }
}
