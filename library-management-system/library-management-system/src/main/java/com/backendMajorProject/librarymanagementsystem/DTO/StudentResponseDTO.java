package com.backendMajorProject.librarymanagementsystem.DTO;

import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentResponseDTO {

    private String name;

    private String dob;

    private Branch branch;

    private String email;

    private String mobNo;
}
