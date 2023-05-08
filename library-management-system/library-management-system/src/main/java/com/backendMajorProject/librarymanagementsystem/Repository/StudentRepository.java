package com.backendMajorProject.librarymanagementsystem.Repository;


import com.backendMajorProject.librarymanagementsystem.Entity.Student;
import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByName(String name);  // custom search by name

    List<Student> findByDob(String dob); // custom search by DOB

    List<Student> findByBranch(Branch branch);  // Custom search by Branch

    Student findByEmail(String email);  // custom search by email

    Student findByMobNo(String mobNo);  // custom search by mobNo


    // Custom Complex Query
    @Query(value = "select * from student s where left(s.name, 1)=:ch", nativeQuery = true)
    List<Student> getAllStudentsWhoseNameStartingWith(char ch);

    @Query(value = "SELECT * FROM student ORDER BY name", nativeQuery = true)
    List<Student> findAllStudentsSortedByNameUsingNativeQuery();


    // We can also write the above Custom Query like this
//    @Query(value = "SELECT * FROM student s ORDER BY s.name", nativeQuery = true)
//    List<Student> findAllStudentsSortedByNameUsingNativeQuery();
}
