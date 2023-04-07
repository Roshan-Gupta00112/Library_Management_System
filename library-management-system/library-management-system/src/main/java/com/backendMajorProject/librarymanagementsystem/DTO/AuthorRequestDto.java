package com.backendMajorProject.librarymanagementsystem.DTO;

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

    private String dob;

    private String email;
}
