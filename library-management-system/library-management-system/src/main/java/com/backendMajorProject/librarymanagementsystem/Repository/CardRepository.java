package com.backendMajorProject.librarymanagementsystem.Repository;

import com.backendMajorProject.librarymanagementsystem.Entity.LibraryCard;
import com.backendMajorProject.librarymanagementsystem.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<LibraryCard, Integer> {
}
