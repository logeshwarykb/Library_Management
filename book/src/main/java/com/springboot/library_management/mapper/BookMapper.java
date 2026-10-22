package com.springboot.library_management.mapper;

import com.springboot.library_management.dto.BookDto;
import com.springboot.library_management.model.Book;

public class BookMapper {

    public static Book ConvertDtoToEntity(BookDto bookDto){
        Book book= new Book();
      book.setTitle(bookDto.title());
      book.setAuthor(bookDto.author());
      book.setIsbn(bookDto.isbn());
      book.setYear(bookDto.year());
      return book;
    }
}
