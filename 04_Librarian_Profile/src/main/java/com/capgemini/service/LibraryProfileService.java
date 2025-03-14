package com.capgemini.service;
import com.capgemini.model.Book;
import com.capgemini.model.LibraryProfile;

import java.util.List;


public interface LibraryProfileService {

    // Methods to call LibrarianProfileService endpoints

    LibraryProfile createLibraryProfile(LibraryProfile libraryProfile);

    LibraryProfile updateLibraryProfile(Long id, LibraryProfile libraryProfile);

    LibraryProfile getLibraryProfileById(Long id);

    List<LibraryProfile> getAllLibraryProfiles();

    void deleteLibraryProfile(Long id);


    // Methods to call BookManagementService endpoints

    String getWelcomeMsg();

    Book getBookById(Long id);

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    List<Book> findBooksByGenre(String genre);

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthor(String author);

    List<Book> findBooksByLanguage(String language);

    List<Book> findBooksByLocation(String location);

    // Methods to call BookingStatusService endpoints

    List<Book> findBooksByStatus(String status);

    Book updateBookStatus(Long id, String status);
}
