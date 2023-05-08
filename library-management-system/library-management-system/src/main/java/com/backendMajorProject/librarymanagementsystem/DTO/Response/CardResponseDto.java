package com.backendMajorProject.librarymanagementsystem.DTO.Response;

import com.backendMajorProject.librarymanagementsystem.Enum.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardResponseDto {

    private int cardNo;

    private Date issueDate;

    private Date lastUpdatedOn;

    private CardStatus cardStatus;

    private String validTill;


}
