package com.backendMajorProject.librarymanagementsystem.DTO.Request;

import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentRequestDto {

    private String name;

    private String dob;   // In YYYY-MM-DD format

    private Branch branch;

    private String email;

    private String mobNo;
}
