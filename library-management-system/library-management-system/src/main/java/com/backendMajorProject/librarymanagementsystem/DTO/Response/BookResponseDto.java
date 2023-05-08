package com.backendMajorProject.librarymanagementsystem.DTO.Response;

import com.backendMajorProject.librarymanagementsystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookResponseDto {

    private String title;

    private int numberOfPages;

    private int quantity;

    private Genre genre;

    private String authorName;

    private int price;
}
