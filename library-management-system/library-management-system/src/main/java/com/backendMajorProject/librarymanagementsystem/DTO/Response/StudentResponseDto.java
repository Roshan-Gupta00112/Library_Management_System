package com.backendMajorProject.librarymanagementsystem.DTO.Response;

import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentResponseDto {

    private int id;

    private String name;

    private String dob;   // In YYYY-MM-DD format

    private Branch branch;

    private String email;

    private String mobNo;

    CardResponseDto cardResponseDto;
}
