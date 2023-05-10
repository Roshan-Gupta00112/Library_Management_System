package com.backendMajorProject.librarymanagementsystem.Service;

import com.backendMajorProject.librarymanagementsystem.DTO.Request.*;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.CardResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.IssuedBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.ReturnedBookResponseDto;
import com.backendMajorProject.librarymanagementsystem.DTO.Response.StudentResponseDto;
import com.backendMajorProject.librarymanagementsystem.Entity.Book;
import com.backendMajorProject.librarymanagementsystem.Entity.LibraryCard;
import com.backendMajorProject.librarymanagementsystem.Entity.Student;
import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import com.backendMajorProject.librarymanagementsystem.Enum.Branch;
import com.backendMajorProject.librarymanagementsystem.Enum.CardStatus;
import com.backendMajorProject.librarymanagementsystem.Enum.TransactionStatus;
import com.backendMajorProject.librarymanagementsystem.Exceptions.StudentNotFoundException;
import com.backendMajorProject.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public String addStudent(StudentRequestDto studentRequestDTO){

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
        card.setValidTill("2025-05-01"); // In YYYY-MM-DD format
        card.setStatus(CardStatus.ACTIVATED);
        // setting student attribute in card
        card.setStudent(student);

        // setting card attribute in student
        student.setCard(card);

        // saving in the database
        studentRepository.save(student);   // will save both student and card

        return "Student has been added successfully";
    }


    public StudentResponseDto getStudentById(int id) throws StudentNotFoundException{
        try {
            Student student=studentRepository.findById(id).get();

            // Prepare StudentResponseDto
            StudentResponseDto studentResponseDto=new StudentResponseDto();

            // Setting Parameters of StudentResponseDto
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            // Preparing CardResponseDto
            CardResponseDto cardResponseDto=new CardResponseDto();

            // Setting attributes of CardResponseDto
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            // Setting cardResponseDto  in studentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            return studentResponseDto;
        }
        catch (Exception e){
            throw new StudentNotFoundException("Invalid student id!");
        }

    }


    public List<StudentResponseDto> getAllStudentsByName(String name){
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

        // Using Custom Query for Attributes
        List<Student> studentList=studentRepository.findByName(name);

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){
            StudentResponseDto studentResponseDto=new StudentResponseDto();

            // Setting attributes of StudentResponseDto
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            studentResponseDto.setCardResponseDto(cardResponseDto);

            studentResponseDtoList.add(studentResponseDto);
        }

        return studentResponseDtoList;
    }



    public List<StudentResponseDto> getAllStudentsByDob(String dob){

        List<Student>studentList= studentRepository.findByDob(dob);
        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for (Student student:studentList){
            // Preparing StudentResponseDto and Setting it's all Attributes
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            // Preparing CardResponseDto and setting it's all attributes
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            // setting CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            // Adding to final DtoList
            studentResponseDtoList.add(studentResponseDto);
        }

        return studentResponseDtoList;
    }


    public List<StudentResponseDto> getAllStudentsByBranch(Branch branch){

        List<Student> studentList=studentRepository.findByBranch(branch);

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for (Student student:studentList){

            // Preparing StudentResponseDto & Setting it's all attributes
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            // Preparing CardResponseDto & Setting it's all attributes
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            // Setting CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            // Adding StudentResponseDto to the final list
            studentResponseDtoList.add(studentResponseDto);
        }

        return studentResponseDtoList;
    }



    public StudentResponseDto getStudentByEmail(String email){

        Student student= studentRepository.findByEmail(email);

        // Creating StudentResponseDto and Setting it's all attributes
        StudentResponseDto studentResponseDto =new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setDob(student.getDob());
        studentResponseDto.setBranch(student.getBranch());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setMobNo(student.getMobNo());

        // Creating CardResponseDto & Setting it's all attributes
        CardResponseDto cardResponseDto=new CardResponseDto();
        cardResponseDto.setCardNo(student.getCard().getCardNo());
        cardResponseDto.setIssueDate(student.getCard().getIssueDate());
        cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
        cardResponseDto.setCardStatus(student.getCard().getStatus());
        cardResponseDto.setValidTill(student.getCard().getValidTill());

        // Setting CardResponseDto attribute of StudentResponseDto
        studentResponseDto.setCardResponseDto(cardResponseDto);

        return studentResponseDto;

    }




    public StudentResponseDto getStudentByMobNo(String mobNo){

        Student student=studentRepository.findByMobNo(mobNo);

        // Creating StudentResponseDto and setting it's all attributes
        StudentResponseDto studentResponseDto=new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setDob(student.getDob());
        studentResponseDto.setBranch(student.getBranch());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setMobNo(student.getMobNo());

        // Creating CardResponseDto & Setting it's all attributes
        CardResponseDto cardResponseDto=new CardResponseDto();
        cardResponseDto.setCardNo(student.getCard().getCardNo());
        cardResponseDto.setIssueDate(student.getCard().getIssueDate());
        cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
        cardResponseDto.setCardStatus(student.getCard().getStatus());
        cardResponseDto.setValidTill(student.getCard().getValidTill());

        // Setting CardResponseDto attribute of StudentResponseDto
        studentResponseDto.setCardResponseDto(cardResponseDto);

        return studentResponseDto;
    }


    public List<StudentResponseDto> getAllStudents(){
        List<Student> studentList=studentRepository.findAll();

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            // Setting attributes of StudentResponseDto
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            // Setting Attribute of CardResponseDto
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            studentResponseDto.setCardResponseDto(cardResponseDto);

            // Adding to final list
            studentResponseDtoList.add(studentResponseDto);
        }

        return studentResponseDtoList;
    }

    public List<StudentResponseDto> findAllStudentsSortedByName(){
        // getting Student Objects from the DB using CUSTOM COMPLEX QUERY
        List<Student> studentList=studentRepository.findAllStudentsSortedByNameUsingNativeQuery();

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){
            // Creating StudentResponseDto and setting it's all attributes
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            // Creating CardResponseDto and Setting it's all Attributes
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            // Setting CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            // Adding to final list
            studentResponseDtoList.add(studentResponseDto);
        }

        return studentResponseDtoList;
    }




    private int calculateAge(LocalDate dob){
        LocalDate todayDate=LocalDate.now();

        if(dob!=null && todayDate!=null){
            return Period.between(dob, todayDate).getYears();
        }
        else return 0;
    }
    public List<StudentResponseDto> exactAge(int age){

        // Getting all Students Object from DB
        List<Student> studentList=studentRepository.findAll();

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){
            LocalDate dOB=LocalDate.parse(student.getDob());

            int studentAge=calculateAge(dOB);

            if(studentAge==age){
                // Creating StudentResponseDto and setting it's all attributes
                StudentResponseDto studentResponseDto=new StudentResponseDto();
                studentResponseDto.setId(student.getId());
                studentResponseDto.setName(student.getName());
                studentResponseDto.setDob(student.getDob());
                studentResponseDto.setBranch(student.getBranch());
                studentResponseDto.setEmail(student.getEmail());
                studentResponseDto.setMobNo(student.getMobNo());

                // Creating CardResponseDto & Setting it's all attributes
                CardResponseDto cardResponseDto=new CardResponseDto();
                cardResponseDto.setCardNo(student.getCard().getCardNo());
                cardResponseDto.setIssueDate(student.getCard().getIssueDate());
                cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
                cardResponseDto.setCardStatus(student.getCard().getStatus());
                cardResponseDto.setValidTill(student.getCard().getValidTill());

                // Setting CardResponseDto attribute of StudentResponseDto
                studentResponseDto.setCardResponseDto(cardResponseDto);

                // Adding to final list
                studentResponseDtoList.add(studentResponseDto);
            }
        }
        return studentResponseDtoList;
    }

    public List<StudentResponseDto> minAge(int age){

        // Getting all Student objects from DB
        List<Student> studentList=studentRepository.findAll();

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){

            LocalDate dOB=LocalDate.parse(student.getDob());

            int studentAge=calculateAge(dOB);

            if(studentAge>=age){
                // Creating StudentResponseDto and setting it's all attributes
                StudentResponseDto studentResponseDto=new StudentResponseDto();
                studentResponseDto.setId(student.getId());
                studentResponseDto.setName(student.getName());
                studentResponseDto.setDob(student.getDob());
                studentResponseDto.setBranch(student.getBranch());
                studentResponseDto.setEmail(student.getEmail());
                studentResponseDto.setMobNo(student.getMobNo());

                // Creating CardResponseDto & Setting it's all attributes
                CardResponseDto cardResponseDto=new CardResponseDto();
                cardResponseDto.setCardNo(student.getCard().getCardNo());
                cardResponseDto.setIssueDate(student.getCard().getIssueDate());
                cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
                cardResponseDto.setCardStatus(student.getCard().getStatus());
                cardResponseDto.setValidTill(student.getCard().getValidTill());

                // Setting CardResponseDto attribute of StudentResponseDto
                studentResponseDto.setCardResponseDto(cardResponseDto);

                // Adding to final list
                studentResponseDtoList.add(studentResponseDto);
            }
        }

        return studentResponseDtoList;
    }


    public List<StudentResponseDto> maxAge(int age){

        // Getting all Student objects from DB
        List<Student> studentList=studentRepository.findAll();

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){

            LocalDate dOB=LocalDate.parse(student.getDob());

            int studentAge=calculateAge(dOB);

            if(studentAge<=age){
                // Creating StudentResponseDto and setting it's all attributes
                StudentResponseDto studentResponseDto=new StudentResponseDto();
                studentResponseDto.setId(student.getId());
                studentResponseDto.setName(student.getName());
                studentResponseDto.setDob(student.getDob());
                studentResponseDto.setBranch(student.getBranch());
                studentResponseDto.setEmail(student.getEmail());
                studentResponseDto.setMobNo(student.getMobNo());

                // Creating CardResponseDto & Setting it's all attributes
                CardResponseDto cardResponseDto=new CardResponseDto();
                cardResponseDto.setCardNo(student.getCard().getCardNo());
                cardResponseDto.setIssueDate(student.getCard().getIssueDate());
                cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
                cardResponseDto.setCardStatus(student.getCard().getStatus());
                cardResponseDto.setValidTill(student.getCard().getValidTill());

                // Setting CardResponseDto attribute of StudentResponseDto
                studentResponseDto.setCardResponseDto(cardResponseDto);

                // Adding to final list
                studentResponseDtoList.add(studentResponseDto);
            }
        }

        return studentResponseDtoList;
    }



    public List<StudentResponseDto> getAllStudentsWhoseNameStartingWith(char ch){

        // Getting Student Objects from the DB Using CUSTOM COMPLEX QUERY
        List<Student> studentList=studentRepository.getAllStudentsWhoseNameStartingWith(ch);

        List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();

        for(Student student:studentList){
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            // Setting attributes of StudentResponseDto
            studentResponseDto.setId(student.getId());
            studentResponseDto.setName(student.getName());
            studentResponseDto.setDob(student.getDob());
            studentResponseDto.setBranch(student.getBranch());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setMobNo(student.getMobNo());

            // Setting Attribute of CardResponseDto
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(student.getCard().getCardNo());
            cardResponseDto.setIssueDate(student.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(student.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(student.getCard().getStatus());
            cardResponseDto.setValidTill(student.getCard().getValidTill());

            studentResponseDto.setCardResponseDto(cardResponseDto);

            // Adding to final list
            studentResponseDtoList.add(studentResponseDto);
        }

        return studentResponseDtoList;
    }


    public List<IssuedBookResponseDto> booksIssuedByStudent(int studId){

        // Getting the Card Object from the DB
        LibraryCard card=studentRepository.findById(studId).get().getCard();

        // Getting All the Transaction List
        List<Transaction> transactions=card.getTransactionList();

        List<IssuedBookResponseDto> issuedBookResponseDtoList=new ArrayList<>();

        for(Transaction transaction:transactions){
            if(transaction.getTransactionStatus()==TransactionStatus.SUCCESS && transaction.isIssueOperation()==true){
                IssuedBookResponseDto issuedBookResponseDto=new IssuedBookResponseDto(transaction.getTransactionNumber(),
                        transaction.getTransactionDate(), transaction.getBook().getId(), transaction.getBook().getTitle(),
                        transaction.getBook().getAuthor().getName());

                issuedBookResponseDtoList.add(issuedBookResponseDto);
            }
        }
        return issuedBookResponseDtoList;
    }

    public List<ReturnedBookResponseDto> booksReturnedByStudent(int studId){

        // Getting the Card Object from the DB
        LibraryCard card=studentRepository.findById(studId).get().getCard();

        // Getting Transaction List
        List<Transaction> transactions=card.getTransactionList();

        List<ReturnedBookResponseDto> returnedBookResponseDtoList=new ArrayList<>();

        for (Transaction transaction:transactions){
            if(transaction.getTransactionStatus()==TransactionStatus.SUCCESS && transaction.isIssueOperation()==false){
                ReturnedBookResponseDto returnedBookResponseDto=new ReturnedBookResponseDto(transaction.getTransactionNumber(),
                        transaction.getTransactionDate(), transaction.getBook().getId(), transaction.getBook().getTitle(),
                        transaction.getBook().getAuthor().getName());

                returnedBookResponseDtoList.add(returnedBookResponseDto);
            }
        }

        return returnedBookResponseDtoList;
    }




    public StudentResponseDto updateName(UpdateNameRequestDto updateNameRequestDto) throws StudentNotFoundException{

        try {
            //Getting the Student Object
            Student student=studentRepository.findById(updateNameRequestDto.getId()).get();

            // setting New Name of the Student
            student.setName(updateNameRequestDto.getName());

            // Saving it in the DB
            Student updatedStudent=studentRepository.save(student);

            // Creating StudentResponseDto & Setting it's all parameters
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setId(updatedStudent.getId());
            studentResponseDto.setName(updatedStudent.getName());
            studentResponseDto.setDob(updatedStudent.getDob());
            studentResponseDto.setBranch(updatedStudent.getBranch());
            studentResponseDto.setEmail(updatedStudent.getEmail());
            studentResponseDto.setMobNo(updatedStudent.getMobNo());

            // Creating CardResponseDto & Setting it's all parameters
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(updatedStudent.getCard().getCardNo());
            cardResponseDto.setIssueDate(updatedStudent.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(updatedStudent.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(updatedStudent.getCard().getStatus());
            cardResponseDto.setValidTill(updatedStudent.getCard().getValidTill());

            // Setting CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            return studentResponseDto;
        }
        catch (Exception e){
            throw new StudentNotFoundException("Invalid student id!");
        }

    }



    public StudentResponseDto updateDob(UpdateDobRequestDto updateDobRequestDto) throws StudentNotFoundException{
        try{
            // Getting the Student Object from the DB
            Student student=studentRepository.findById(updateDobRequestDto.getId()).get();
            student.setDob(updateDobRequestDto.getDob());

            // Saving Student Object in the Db
            Student updatedStudent=studentRepository.save(student);

            // Creating StudentResponseDto & Setting all it's attributes
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setId(updatedStudent.getId());
            studentResponseDto.setName(updatedStudent.getName());
            studentResponseDto.setDob(updatedStudent.getDob());
            studentResponseDto.setBranch(updatedStudent.getBranch());
            studentResponseDto.setEmail(updatedStudent.getEmail());
            studentResponseDto.setMobNo(updatedStudent.getMobNo());

            // Creating CardResponseDto and Setting it's all attributes
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(updatedStudent.getCard().getCardNo());
            cardResponseDto.setIssueDate(updatedStudent.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(updatedStudent.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(updatedStudent.getCard().getStatus());
            cardResponseDto.setValidTill(updatedStudent.getCard().getValidTill());

            // Setting CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            return studentResponseDto;
        }
        catch (Exception e){
            throw new StudentNotFoundException("Invalid student id!");
        }

    }


    public StudentResponseDto updateEmail(UpdateEmailRequestDto updateEmailRequestDto) throws StudentNotFoundException{

        try {
            // Getting the Student Object from the DB
            Student student=studentRepository.findById(updateEmailRequestDto.getId()).get();
            //Updating email
            student.setEmail(updateEmailRequestDto.getEmail());

            // updating in db
            Student updatedStudent=studentRepository.save(student);

            // conversion of updated student to response dto
            StudentResponseDto studentResponseDto=new StudentResponseDto();
            studentResponseDto.setId(updatedStudent.getId());
            studentResponseDto.setName(updatedStudent.getName());
            studentResponseDto.setDob(updatedStudent.getDob());
            studentResponseDto.setBranch(updatedStudent.getBranch());
            studentResponseDto.setEmail(updatedStudent.getEmail());
            studentResponseDto.setMobNo(updatedStudent.getMobNo());

            // Creating CardResponseDto & Setting it's all attributes
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(updatedStudent.getCard().getCardNo());
            cardResponseDto.setIssueDate(updatedStudent.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(updatedStudent.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(updatedStudent.getCard().getStatus());
            cardResponseDto.setValidTill(updatedStudent.getCard().getValidTill());

            // Setting the CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            return studentResponseDto;
        }
        catch (Exception e){
            throw new StudentNotFoundException("Invalid student id!");
        }
    }

    public StudentResponseDto updateMobNo(UpdateMobNoRequestDto updateMobNoRequestDto) throws StudentNotFoundException {

        try {
            // Conversion of Request Dto to a Student Object(Entity)
            Student student = studentRepository.findById(updateMobNoRequestDto.getId()).get();
            // updating the email
            student.setMobNo(updateMobNoRequestDto.getMobNo());

            // Updating in Db
            Student updatedStudent = studentRepository.save(student);

            // Conversion of Updated Student object to Response DTO
            StudentResponseDto studentResponseDto = new StudentResponseDto();
            studentResponseDto.setId(updatedStudent.getId());
            studentResponseDto.setName(updatedStudent.getName());
            studentResponseDto.setDob(updatedStudent.getDob());
            studentResponseDto.setBranch(updatedStudent.getBranch());
            studentResponseDto.setEmail(updatedStudent.getEmail());
            studentResponseDto.setMobNo(updatedStudent.getMobNo());

            // Creating CardResponseDto & Setting it's all attributes
            CardResponseDto cardResponseDto=new CardResponseDto();
            cardResponseDto.setCardNo(updatedStudent.getCard().getCardNo());
            cardResponseDto.setIssueDate(updatedStudent.getCard().getIssueDate());
            cardResponseDto.setLastUpdatedOn(updatedStudent.getCard().getUpdationDate());
            cardResponseDto.setCardStatus(updatedStudent.getCard().getStatus());
            cardResponseDto.setValidTill(updatedStudent.getCard().getValidTill());

            // Setting CardResponseDto attribute of StudentResponseDto
            studentResponseDto.setCardResponseDto(cardResponseDto);

            return studentResponseDto;
        } catch (Exception e) {
            throw new StudentNotFoundException("Invalid student id!");
        }

    }


    public String deleteStudent(int studId) throws Exception{
        Student student;
        try {
            student=studentRepository.findById(studId).get();
        }catch (Exception e){
            throw new StudentNotFoundException("Invalid student id!");
        }

        int noOfBooksNeedToReturn= isAllBooksReturned(studId);
        if(noOfBooksNeedToReturn!=0){
            throw new Exception("You haven't returned " +noOfBooksNeedToReturn+ " books. You must have to return first!");
        }

        studentRepository.delete(student);

        return "Student removed successfully";
    }
    private int isAllBooksReturned(int studId){
        // Getting Card Object from the DB
        LibraryCard card=studentRepository.findById(studId).get().getCard();

        // Getting List of all issued books by the student
        List<Book>issuedBooksList=card.getBooksIssued();

        // When student either returned all issued books or not issued any book
        return issuedBooksList.size();
    }





}
