package com.backendMajorProject.librarymanagementsystem.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorResponseDto {

    private int id;

    private String name;

    private String dob;   // YY--MM--DD format

    private String email;
}
