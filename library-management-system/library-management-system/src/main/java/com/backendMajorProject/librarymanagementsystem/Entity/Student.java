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

    @Column(nullable = false)
    private String name;

    @Column(name = "DOB", nullable = false)
    private String dob;    // In YYYY-MM-DD format

    @Enumerated(EnumType.STRING)
    private Branch branch;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobNo;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    LibraryCard card;
}
