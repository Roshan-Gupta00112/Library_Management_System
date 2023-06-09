package com.backendMajorProject.librarymanagementsystem.DTO.Request;

import com.backendMajorProject.librarymanagementsystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookRequestDto {

    private String title;

    private int price;

    private int numberOfPages;

    private int quantity;

    private Genre genre;

    private int authorId;
}
