package com.backendMajorProject.librarymanagementsystem.DTO;

import com.backendMajorProject.librarymanagementsystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookRequestDTO {

    private String title;

    private int price;

    private Genre genre;

    private int author
}
