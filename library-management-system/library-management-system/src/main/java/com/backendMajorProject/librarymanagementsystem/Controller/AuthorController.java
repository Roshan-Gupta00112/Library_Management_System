package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.AuthorRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.AuthorResponseDto;
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
    public AuthorResponseDto addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        return authorService.addAuthor(authorRequestDto);
    }


    @GetMapping("/get-by-id")
    public AuthorResponseDto getById(@RequestParam("id") int id){
        return authorService.getById(id);
    }

    @GetMapping("/get-by-name")
    public List<AuthorResponseDto> getByName(@RequestParam("name") String name){
        return authorService.getByName(name);
    }

    @GetMapping("/get-by-dob")
    public List<AuthorResponseDto> getAuthorByDob(@RequestParam("dob") String dob){
        return authorService.getByDob(dob);
    }

    @GetMapping("/get-by-email")
    public AuthorResponseDto getAuthorByEmail(@RequestParam("email") String email){
        return authorService.getByEmail(email);
    }



    @GetMapping("/get-All-Authors")
    public List<AuthorResponseDto> getAllAuthors(){
        return authorService.getAllAuthors();
    }
}
