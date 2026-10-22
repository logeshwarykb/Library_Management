package com.springboot.library_management.controller;

import com.springboot.library_management.dto.BookDto;
import com.springboot.library_management.model.Book;
import com.springboot.library_management.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/api/book/add")
    public void insertBook(@Valid @RequestBody BookDto bookDto){

        bookService.insertBook(bookDto);
    }

    @GetMapping("/api/book/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn){

        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping("/api/book/all")
    public List<Book> getAllBooks(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
        return bookService.getAllBooks(page, size);
    }

    @PutMapping("/api/book/update/{id}")
    public void updateBook(@PathVariable Long id,@Valid @RequestBody BookDto bookDto){
         bookService.updateBook(id, bookDto);
    }

    @DeleteMapping("/api/book/delete/{isbn}")
    public void deleteByIsbn(@PathVariable String isbn){
        bookService.deleteByIsbn(isbn);
    }
}
