package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.*;
import com.backendMajorProject.librarymanagementsystem.Entity.LibraryCard;
import com.backendMajorProject.librarymanagementsystem.Entity.Student;
import com.backendMajorProject.librarymanagementsystem.Enum.CardStatus;
import com.backendMajorProject.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void addStudent(StudentRequestDTO studentRequestDTO){

//        LibraryCard card=new LibraryCard();
//        // Setting attributes of card
//        card.setStatus(CardStatus.ACTIVATED);
//        card.setValidTill("31/03/2025");
//        card.setStudent(student);
//
//        // set the card attribute in student
//        student.setCard(card);
//
//        studentRepository.save(student);

        // Now setting the value using DTO
        // create a student object
        Student student=new Student();
        student.setName(studentRequestDTO.getName());
        student.setDob(studentRequestDTO.getDob());
        student.setBranch(studentRequestDTO.getBranch());
        student.setEmail(studentRequestDTO.getEmail());
        student.setMobNo(studentRequestDTO.getMobNo());

        // creating a library_card
        LibraryCard card=new LibraryCard();
        // setting attributes of card
        card.setValidTill("31/03/2025");
        card.setStatus(CardStatus.ACTIVATED);
        // setting student attribute in card
        card.setStudent(student);

        // setting card attribute in student
        student.setCard(card);

        // saving in the database
        studentRepository.save(student);   // will save both student and card
    }



    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public List<Student> getAllStudentsByName(String name){
//        List<Student> allStudents=studentRepository.findAll();
//
//        List<Student> studentsByName=new ArrayList<>();
//
//        for(Student stud:allStudents){
//            if(stud.getName().equals(name)){
//                studentsByName.add(stud);
//            }
//        }
//
//        return studentsByName;

        // Using Custom Query
        return studentRepository.findByName(name);
    }

    public String getStudentNameByMobNo(String mobNo){
        return studentRepository.findByMobNo(mobNo).getName();
    }

    public String getStudentByEmail(String email){
        return studentRepository.findByEmail(email).getName();
    }

    public List<Student> getStudentByDob(String dob){
        return studentRepository.findByDob(dob);
    }



    public StudentResponseMobNoDto updateMobNo(StudentUpdateMobNoRequestDto studentUpdateMobNoRequestDto){

        // Conversion of Request Dto to a Student Object(Entity)
        Student student=studentRepository.findById(studentUpdateMobNoRequestDto.getId()).get();
        // updating the email
        student.setMobNo(studentUpdateMobNoRequestDto.getMobNo());

        // Updating in Db
        Student updatedStudent=studentRepository.save(student);

        // Conversion of Updated Student object to Response DTO
        StudentResponseMobNoDto studentResponseMobNoDto=new StudentResponseMobNoDto();
        studentResponseMobNoDto.setId(updatedStudent.getId());
        studentResponseMobNoDto.setName(updatedStudent.getName());
        studentResponseMobNoDto.setMobNo(updatedStudent.getMobNo());

        return studentResponseMobNoDto;
    }




    public StudentResponseEmailDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){

        Student student=studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());

        // update in db
        Student updatedStudent=studentRepository.save(student);

        // conversion of updated student to response dto
        StudentResponseEmailDto studentResponseEmailDto=new StudentResponseEmailDto();
        studentResponseEmailDto.setId(updatedStudent.getId());
        studentResponseEmailDto.setName(updatedStudent.getName());
        studentResponseEmailDto.setEmail(updatedStudent.getEmail());

        return studentResponseEmailDto;
    }
}
