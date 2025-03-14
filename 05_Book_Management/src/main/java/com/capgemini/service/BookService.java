package com.capgemini.service;


import com.capgemini.model.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    Book updateBook(Long id, Book book);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    void deleteBook(Long id);

    List<Book> findBooksByGenre(String genre);

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthor(String author);

    List<Book> findBooksByLanguage(String language);

    List<Book> findBooksByLocation(String location);

}
