package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.AuthorRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.AuthorResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Author;
import com.backendMajorProject.librarymanagementsystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto){

        Author author= new Author();

        // Setting Attributes of Author object
        author.setName(authorRequestDto.getName());
        author.setDob(authorRequestDto.getDob());
        author.setEmail(authorRequestDto.getEmail());

        authorRepository.save(author);

        // Creating AuthorResponseDto & Setting it's all attributes
        AuthorResponseDto authorResponseDto=new AuthorResponseDto();
        authorResponseDto.setId(author.getId());
        authorResponseDto.setName(author.getName());
        authorResponseDto.setDob(author.getDob());
        authorResponseDto.setEmail(author.getEmail());

        return authorResponseDto;
    }


    public AuthorResponseDto getById(int id){
        // Getting the Author Object from the DB
        Author author=authorRepository.findById(id).get();

        // Creating AuthorResponseDto and Setting it's all attributes
        AuthorResponseDto authorResponseDto=new AuthorResponseDto();
        authorResponseDto.setId(author.getId());
        authorResponseDto.setName(author.getName());
        authorResponseDto.setDob(author.getDob());
        authorResponseDto.setEmail(author.getEmail());

        return authorResponseDto;
    }


    public List<AuthorResponseDto> getByName(String name){
        // Getting Authors list from DB
        List<Author> authorList=authorRepository.findByName(name);

        List<AuthorResponseDto> authorResponseDtoList=new ArrayList<>();

        for(Author author:authorList){

            // Creating AuthorResponseDto & Setting it's all attributes
            AuthorResponseDto authorResponseDto=new AuthorResponseDto();
            authorResponseDto.setId(author.getId());
            authorResponseDto.setName(author.getName());
            authorResponseDto.setDob(author.getDob());
            authorResponseDto.setEmail(author.getEmail());

            // Storing all AuthorResponseDto in the final List
            authorResponseDtoList.add(authorResponseDto);
        }

        return authorResponseDtoList;
    }

    public List<AuthorResponseDto> getByDob(String dob){
        // Getting Authors list from DB
        List<Author> authorList=authorRepository.findByDob(dob);

        List<AuthorResponseDto> authorResponseDtoList=new ArrayList<>();

        for(Author author:authorList){

            // Creating AuthorResponseDto & Setting it's all attributes
            AuthorResponseDto authorResponseDto=new AuthorResponseDto();
            authorResponseDto.setId(author.getId());
            authorResponseDto.setName(author.getName());
            authorResponseDto.setDob(author.getDob());
            authorResponseDto.setEmail(author.getEmail());

            // Storing all AuthorResponseDto in the final List
            authorResponseDtoList.add(authorResponseDto);
        }

        return authorResponseDtoList;
    }

    public AuthorResponseDto getByEmail(String email){

        // Getting Author Object from DB
        Author author=authorRepository.findByEmail(email);

        // Creating AuthorResponseDto & Setting it's all attributes
        AuthorResponseDto authorResponseDto=new AuthorResponseDto();
        authorResponseDto.setId(author.getId());
        authorResponseDto.setName(author.getName());
        authorResponseDto.setDob(author.getDob());
        authorResponseDto.setEmail(author.getEmail());

        return authorResponseDto;
    }


    public List<AuthorResponseDto> getAllAuthors(){

        // Getting all Authors from the DB
        List<Author> authorList= authorRepository.findAll();

        List<AuthorResponseDto> authors=new ArrayList<>();

        for(Author author:authorList){

            // Creating AuthorResponseDto & Setting it's all attributes
            AuthorResponseDto authorResponseDto=new AuthorResponseDto();
            authorResponseDto.setId(author.getId());
            authorResponseDto.setName(author.getName());
            authorResponseDto.setDob(author.getDob());
            authorResponseDto.setEmail(author.getEmail());

            // Storing all authorResponseDto in a List
            authors.add(authorResponseDto);
        }
        return authors;
    }
}
