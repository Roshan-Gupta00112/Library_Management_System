package com.backendMajorProject.librarymanagementsystem.Controller;

import com.backendMajorProject.librarymanagementsystem.DTO.*;
import com.backendMajorProject.librarymanagementsystem.Entity.Student;
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
        studentService.addStudent(studentRequestDTO);

        return "Student has been added successfully";
    }


    @GetMapping("get-student-by-id")
    public StudentResponseDto getStudentById(@RequestParam("id") int id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/get-all-students")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/get-students-by-name")
    public List<Student> getAllStudentsByName(@RequestParam String name){
        return studentService.getAllStudentsByName(name);
    }

    @GetMapping("/get-student-by-mobNo")
    public String getStudentNameByMobNo(@RequestParam("mobNo") String mobNo){
        return studentService.getStudentNameByMobNo(mobNo);
    }

    @GetMapping("/get-student-by-email")
    public String getStudentByEmail(@RequestParam("email") String email){
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/get-student-by-dob")
    public List<Student> getStudentByDob(@RequestParam("dob") String dob){
        return studentService.getStudentByDob(dob);
    }



    @PutMapping("/update-mobNo")
    public StudentResponseMobNoDto updateMobNo(@RequestBody StudentUpdateMobNoRequestDto studentUpdateMobNoRequestDto) throws StudentNotFoundException{
        return studentService.updateMobNo(studentUpdateMobNoRequestDto);
    }

    @PutMapping("/update-email")
    public StudentResponseEmailDto updateEmail(@RequestBody StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        return studentService.updateEmail(studentUpdateEmailRequestDto);
    }
}
