package com.capgemini.controller;

import com.capgemini.model.Book;
import com.capgemini.model.LibraryProfile;
import com.capgemini.service.LibraryProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/api/library-profiles")
@Tag(name = "Library Profile Management System")
public class LibraryProfileController {

    @Autowired
    private LibraryProfileService libraryProfileService;

    @Autowired
    private RestTemplate restTemplate;

    //End point for Book Management Service
    //@ApiOperation(value = "Get welcome message from Book Management Service")
    @Operation(summary = "Get welcome message from Book Management Service")
    @GetMapping("/books/welcome")
    public ResponseEntity<String> getWelcomeMsg() {
        String msg = libraryProfileService.getWelcomeMsg();
        return ResponseEntity.ok(msg);
    }

    //@ApiOperation(value = "Add a new book")
    @Operation(summary = "Add a new book")
    @PostMapping("/books/create")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = libraryProfileService.addBook(book);
        return ResponseEntity.ok(createdBook);
    }

    //@ApiOperation(value = "Update an existing book")
    @Operation(summary = "Update an existing book")
    @PutMapping("/books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = libraryProfileService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    //@ApiOperation(value = "Get a book by ID")
    @Operation(summary = "Get a book by ID")
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = libraryProfileService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    //@ApiOperation(value = "Get all books")
    @Operation(summary = "Get all books")
    @GetMapping("/books/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryProfileService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    //@ApiOperation(value = "Delete a book by ID")
    @Operation(summary = "Delete a book by ID")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        libraryProfileService.deleteBook(id);
        return ResponseEntity.ok("Book with ID " + id + " has been successfully deleted.");
    }

    //@ApiOperation(value = "Find books by genre")
    @Operation(summary = "Find books by genre")
    @GetMapping("/books/genre/{genre}")
    public ResponseEntity<List<Book>> findBooksByGenre(@PathVariable String genre) {
        List<Book> books = libraryProfileService.findBooksByGenre(genre);
        return ResponseEntity.ok(books);
    }

    //@ApiOperation(value = "Find books by title")
    @Operation(summary = "Find books by title")
    @GetMapping("/books/title/{title}")
    public ResponseEntity<List<Book>> findBooksByTitle(@PathVariable String title) {
        List<Book> books = libraryProfileService.findBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    //@ApiOperation(value = "Find books by author")
    @Operation(summary = "Find books by author")
    @GetMapping("/books/author/{author}")
    public ResponseEntity<List<Book>> findBooksByAuthor(@PathVariable String author) {
        List<Book> books = libraryProfileService.findBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    //@ApiOperation(value = "Find books by language")
    @Operation(summary = "Find books by language")
    @GetMapping("/books/language/{language}")
    public ResponseEntity<List<Book>> findBooksByLanguage(@PathVariable String language) {
        List<Book> books = libraryProfileService.findBooksByLanguage(language);
        return ResponseEntity.ok(books);
    }

    //@ApiOperation(value = "Find books by location")
    @Operation(summary = "Find books by location")
    @GetMapping("/books/location/{location}")
    public ResponseEntity<List<Book>> findBooksByLocation(@PathVariable String location) {
        List<Book> books = libraryProfileService.findBooksByLocation(location);
        return ResponseEntity.ok(books);
    }

//End point for BookingStatus Service

    //@ApiOperation(value = "Find books by status")
    @Operation(summary = "Find books by status")
    @GetMapping("/books/status/{status}")
    public ResponseEntity<List<Book>> findBooksByStatus(@PathVariable String status) {
        List<Book> books = libraryProfileService.findBooksByStatus(status);
        return ResponseEntity.ok(books);
    }

    //@ApiOperation(value = "Update book status")
    @Operation(summary = "Update book status")
    @PutMapping("/books/{id}/status")
    public ResponseEntity<Book> updateBookStatus(@PathVariable Long id, @RequestParam String status) {
        Book updatedBook = libraryProfileService.updateBookStatus(id, status);
        return ResponseEntity.ok(updatedBook);
    }

//End point for LibrarianProfile Service

    //@ApiOperation(value = "Create a new library profile")
    @Operation(summary = "Create a new library profile")
    @PostMapping("/create")
    public ResponseEntity<LibraryProfile> createLibraryProfile(@RequestBody LibraryProfile libraryProfile) {
        LibraryProfile createdProfile = libraryProfileService.createLibraryProfile(libraryProfile);
        return ResponseEntity.ok(createdProfile);
    }

    //@ApiOperation(value = "Update an existing library profile")
    @Operation(summary = "Update an existing library profile")
    @PutMapping("/update/{id}")
    public ResponseEntity<LibraryProfile> updateLibraryProfile(@PathVariable Long id, @RequestBody LibraryProfile libraryProfile) {
        LibraryProfile updatedProfile = libraryProfileService.updateLibraryProfile(id, libraryProfile);
        return ResponseEntity.ok(updatedProfile);
    }

    //@ApiOperation(value = "Get a library profile by ID")
    @Operation(summary = "Get a library profile by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LibraryProfile> getLibraryProfileById(@PathVariable Long id) {
        LibraryProfile libraryProfile = libraryProfileService.getLibraryProfileById(id);
        return ResponseEntity.ok(libraryProfile);
    }

    //@ApiOperation(value = "Get all library profiles")
    @Operation(summary = "Get all library profiles")
    @GetMapping("/all")
    public ResponseEntity<List<LibraryProfile>> getAllLibraryProfiles() {
        List<LibraryProfile> libraryProfiles = libraryProfileService.getAllLibraryProfiles();
        return ResponseEntity.ok(libraryProfiles);
    }

    //@ApiOperation(value = "Delete a library profile by ID")
    @Operation(summary = "Delete a library profile by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLibraryProfile(@PathVariable Long id) {
        libraryProfileService.deleteLibraryProfile(id);
        return ResponseEntity.ok("Library profile with ID " + id + " has been successfully deleted.");
    }

}
