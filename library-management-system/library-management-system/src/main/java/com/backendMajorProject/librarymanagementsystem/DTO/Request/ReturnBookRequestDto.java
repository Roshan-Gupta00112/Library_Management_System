package com.backendMajorProject.librarymanagementsystem.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReturnBookRequestDto {

    private int cardId;

    private int bookId;
}
