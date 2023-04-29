package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.AuthorRequestDto;
import com.backendMajorProject.librarymanagementsystem.DTO.AuthorResponseDto;
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

    public void addAuthor(AuthorRequestDto authorRequestDto){

        Author author= new Author();

        // Setting Attributes of Author object
        author.setName(authorRequestDto.getName());
        author.setDob(authorRequestDto.getDob());
        author.setEmail(authorRequestDto.getEmail());

        authorRepository.save(author);
    }


    public AuthorResponseDto getByEmail(String email){
        Author author=authorRepository.findByEmail(email);

        AuthorResponseDto authorResponseDto=new AuthorResponseDto();

        authorResponseDto.setName(author.getName());
        authorResponseDto.setDob(author.getDob());
        authorResponseDto.setEmail(author.getEmail());

        return authorResponseDto;
    }

    public List<AuthorResponseDto> getByDob(String dob){
        List<Author> authorList=authorRepository.findByDob(dob);

        List<AuthorResponseDto> authorResponseDtoList=new ArrayList<>();

        for(Author author:authorList){
            AuthorResponseDto authorResponseDto=new AuthorResponseDto();

            authorResponseDto.setName(author.getName());
            authorResponseDto.setDob(author.getDob());
            authorResponseDto.setEmail(author.getEmail());

            authorResponseDtoList.add(authorResponseDto);
        }

        return authorResponseDtoList;
    }
    public List<AuthorResponseDto> getAllAuthors(){

        List<Author> authorList= authorRepository.findAll();

        List<AuthorResponseDto> authors=new ArrayList<>();

        for(Author author:authorList){
            AuthorResponseDto authorResponseDto=new AuthorResponseDto();
            authorResponseDto.setName(author.getName());
            authorResponseDto.setDob(author.getDob());
            authorResponseDto.setEmail(author.getEmail());
            authors.add(authorResponseDto);
        }
        return authors;
    }
}
