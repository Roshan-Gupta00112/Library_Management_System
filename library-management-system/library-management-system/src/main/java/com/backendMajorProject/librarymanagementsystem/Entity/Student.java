package com.backendMajorProject.librarymanagementsystem.Entity;

import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String dob;

    @Enumerated(EnumType.STRING)
    private Branch branch;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobNo;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    LibraryCard card;
}