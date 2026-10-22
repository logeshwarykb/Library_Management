package com.springboot.library_management.service;

import com.springboot.library_management.dto.BookDto;
import com.springboot.library_management.model.Book;
import com.springboot.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    // Valid test: get all books
    @Test
    public void getAllBooksTest() {
        Book book1 = new Book();
        book1.setTitle("Wings of Fire");
        book1.setAuthor("A. P. J. Abdul Kalam");
        book1.setIsbn("9788173711466");
        book1.setYear(1999);

        Book book2 = new Book();
        book2.setTitle("Ignited Minds");
        book2.setAuthor("A. P. J. Abdul Kalam");
        book2.setIsbn("9780143424120");
        book2.setYear(2002);

        when(bookRepository.findAll(PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(List.of(book1, book2)));

        assertEquals(2, bookService.getAllBooks(0, 5).size());

        verify(bookRepository, times(1))
                .findAll(PageRequest.of(0, 5));
        verifyNoMoreInteractions(bookRepository);
    }

    // Valid test: get a book by ISBN
    @Test
    public void getBookByIsbnTest() {
        Book book = new Book();
        book.setTitle("Wings of Fire");
        book.setAuthor("A. P. J. Abdul Kalam");
        book.setIsbn("9788173711466");
        book.setYear(1999);

        when(bookRepository.findByIsbn("9788173711466"))
                .thenReturn(Optional.of(book));

        Book result = bookService.getBookByIsbn("9788173711466");

        assertEquals("Wings of Fire", result.getTitle());
        assertEquals("9788173711466", result.getIsbn());

        verify(bookRepository, times(1))
                .findByIsbn("9788173711466");
        verifyNoMoreInteractions(bookRepository);
    }

    // Invalid test: ISBN not found
    @Test
    public void getBookByIsbnTest_Invalid() {
        when(bookRepository.findByIsbn("0000000000000"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bookService.getBookByIsbn("0000000000000"));

        assertTrue(exception.getMessage().contains("Book not found"));

        verify(bookRepository, times(1))
                .findByIsbn("0000000000000");
        verifyNoMoreInteractions(bookRepository);
    }

    // Valid test: insert a book
    @Test
    public void insertBookTest() {
        BookDto bookDto = new BookDto(
                "The Alchemist", "Paulo Coelho", "9780062315007", 1988);

        bookService.insertBook(bookDto);

        verify(bookRepository, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    // Valid test: update a book
    @Test
    public void updateBookTest() {
        Book book = new Book();
        book.setTitle("Wings of Fire");
        book.setAuthor("A. P. J. Abdul Kalam");
        book.setIsbn("9788173711466");
        book.setYear(1999);

        BookDto bookDto = new BookDto(
                "Wings of Fire - Updated",
                "A. P. J. Abdul Kalam",
                "9788173711466",
                1999);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        bookService.updateBook(1L, bookDto);

        assertEquals("Wings of Fire - Updated", book.getTitle());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository);
    }

    // Invalid test: update a book that does not exist
    @Test
    public void updateBookTest_Invalid() {
        BookDto bookDto = new BookDto(
                "Unknown Book", "Unknown Author", "0000000000000", 2020);

        when(bookRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.updateBook(99L, bookDto));

        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, never()).save(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    // Valid test: delete a book
    @Test
    public void deleteByIsbnTest() {
        Book book = new Book();
        book.setTitle("Wings of Fire");
        book.setAuthor("A. P. J. Abdul Kalam");
        book.setIsbn("9788173711466");
        book.setYear(1999);

        when(bookRepository.findByIsbn("9788173711466"))
                .thenReturn(Optional.of(book));

        bookService.deleteByIsbn("9788173711466");

        verify(bookRepository, times(1))
                .findByIsbn("9788173711466");
        verify(bookRepository, times(1)).delete(book);
        verifyNoMoreInteractions(bookRepository);
    }

    // Invalid test: delete a book that does not exist
    @Test
    public void deleteByIsbnTest_Invalid() {
        when(bookRepository.findByIsbn("0000000000000"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.deleteByIsbn("0000000000000"));

        verify(bookRepository, times(1))
                .findByIsbn("0000000000000");
        verify(bookRepository, never()).delete(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }
}
