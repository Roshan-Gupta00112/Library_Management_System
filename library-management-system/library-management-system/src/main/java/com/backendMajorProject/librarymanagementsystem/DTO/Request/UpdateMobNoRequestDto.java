package com.backendMajorProject.librarymanagementsystem.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateMobNoRequestDto {

    private int id;

    private String mobNo;
}
