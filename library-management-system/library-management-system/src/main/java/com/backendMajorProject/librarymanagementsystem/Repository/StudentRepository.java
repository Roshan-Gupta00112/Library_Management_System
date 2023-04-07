package com.backendMajorProject.librarymanagementsystem.Repository;


import com.backendMajorProject.librarymanagementsystem.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByMobNo(String mobNo);  // custom search by mobNo

    Student findByEmail(String email);  // custom search by email

    List<Student> findByDob(String dob); // custom search by DOB

    List<Student> findByName(String name);  // custom search by name
}
