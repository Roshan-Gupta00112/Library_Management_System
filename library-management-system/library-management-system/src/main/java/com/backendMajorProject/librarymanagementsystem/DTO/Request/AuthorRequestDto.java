package com.backendMajorProject.librarymanagementsystem.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthorRequestDto {

    private String name;

    private String dob;  // YY--MM--DD format

    private String email;
}
