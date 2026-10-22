package com.springboot.library_management.service;
import com.springboot.library_management.dto.BookDto;
import com.springboot.library_management.exception.BookNotFoundException;
import com.springboot.library_management.mapper.BookMapper;
import com.springboot.library_management.model.Book;
import com.springboot.library_management.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class BookService {

 private  final BookRepository bookRepository;

    public void insertBook(BookDto bookDto){
        Book book= BookMapper.ConvertDtoToEntity(bookDto);
        bookRepository.save(book);
    }

    public Book getBookByIsbn(String isbn) {
      Optional<Book> optional = bookRepository.findByIsbn(isbn);
        if (optional.isEmpty())
            throw new BookNotFoundException("Book not found with ISBN: " + isbn);
        else
            return optional.get();
    }

    public List<Book> getAllBooks(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable).getContent();
    }

    public void updateBook(Long id, BookDto bookDto){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        book.setTitle(bookDto.title());
        book.setAuthor(bookDto.author());
        book.setIsbn(bookDto.isbn());
        book.setYear(bookDto.year());

        bookRepository.save(book);
    }

    public void deleteByIsbn(String isbn){
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
        bookRepository.delete(book);
    }

}
