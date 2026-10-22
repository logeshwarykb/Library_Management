package com.springboot.library_management.repository;

import com.springboot.library_management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book , Long>{

    Optional<Book> findByIsbn(String isbn);

}
