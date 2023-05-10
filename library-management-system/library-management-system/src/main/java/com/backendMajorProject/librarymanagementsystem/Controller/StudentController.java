package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.*;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.IssuedBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.ReturnedBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.StudentResponseDto;
import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import com.backendMajorProject.librarymanagementsystem.Exceptions.StudentNotFoundException;
import com.backendMajorProject.librarymanagementsystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public String addStudent(@RequestBody StudentRequestDto studentRequestDTO){
        return studentService.addStudent(studentRequestDTO);
    }


    @GetMapping("/get-student-by-id")
    public StudentResponseDto getStudentById(@RequestParam("id") int id) throws StudentNotFoundException {
        return studentService.getStudentById(id);
    }

    @GetMapping("/get-students-by-name")
    public List<StudentResponseDto> getAllStudentsByName(@RequestParam("name") String name){
        return studentService.getAllStudentsByName(name);
    }

    @GetMapping("/get-students-by-dob")
    public List<StudentResponseDto> getAllStudentsByDob(@RequestParam("dob") String dob){
        return studentService.getAllStudentsByDob(dob);
    }

    @GetMapping("/get-students-by-branch")
    public List<StudentResponseDto> getAllStudentsByBranch(@RequestParam("branch")Branch branch){
        return studentService.getAllStudentsByBranch(branch);
    }

    @GetMapping("/get-student-by-email")
    public StudentResponseDto getStudentByEmail(@RequestParam("email") String email){
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/get-student-by-mobNo")
    public StudentResponseDto getStudentByMobNo(@RequestParam("mobNo") String mobNo){
        return studentService.getStudentByMobNo(mobNo);
    }

    @GetMapping("/get-all-students")
    public List<StudentResponseDto> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/get-students-sorted-by-name")
    public List<StudentResponseDto> findAllStudentsSortedByName(){
        return studentService.findAllStudentsSortedByName();
    }

    @GetMapping("/get-students-by-exact-age")
    public List<StudentResponseDto> exactAge(@RequestParam("age") int age){
        return studentService.exactAge(age);
    }

    @GetMapping("/get-students-with-min-age")
    public List<StudentResponseDto> minAge(@RequestParam("age") int age){
        return studentService.minAge(age);
    }

    @GetMapping("/get-students-with-max-age")
    public List<StudentResponseDto> maxAge(@RequestParam("age") int age){
        return studentService.maxAge(age);
    }

    @GetMapping("/get-students-name-starting with")
    public List<StudentResponseDto> getAllStudentsWhoseNameStartingWith(@RequestParam("firstLetter") char ch){
        return studentService.getAllStudentsWhoseNameStartingWith(ch);
    }

    @GetMapping("/all-books-issued")
    public List<IssuedBookResponseDto> booksIssuedByStudent(@RequestParam("id") int studId){
        return studentService.booksIssuedByStudent(studId);
    }

    @GetMapping("/all-books-returned")
    public List<ReturnedBookResponseDto> booksReturnedByStudent(@RequestParam("id") int studId){
        return studentService.booksReturnedByStudent(studId);
    }


    @PutMapping("/update-name")
    public StudentResponseDto updateName(@RequestBody UpdateNameRequestDto updateNameRequestDto) throws StudentNotFoundException{
        return studentService.updateName(updateNameRequestDto);
    }

    @PutMapping("/update-dob")
    public StudentResponseDto updateDob(@RequestBody UpdateDobRequestDto updateDobRequestDto) throws StudentNotFoundException{
        return studentService.updateDob(updateDobRequestDto);
    }

    @PutMapping("/update-email")
    public StudentResponseDto updateEmail(@RequestBody UpdateEmailRequestDto updateEmailRequestDto) throws StudentNotFoundException{
        return studentService.updateEmail(updateEmailRequestDto);
    }

    @PutMapping("/update-mobNo")
    public StudentResponseDto updateMobNo(@RequestBody UpdateMobNoRequestDto updateMobNoRequestDto) throws StudentNotFoundException {
        return studentService.updateMobNo(updateMobNoRequestDto);
    }


    @DeleteMapping("/delete")
    public String deleteStudent(@RequestParam("id") int id) throws Exception{
        return studentService.deleteStudent(id);
    }
}
