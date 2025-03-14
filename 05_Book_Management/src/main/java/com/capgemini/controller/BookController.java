package com.capgemini.controller;

import com.capgemini.model.Book;
import com.capgemini.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomeMsg(){
        String msg ="Welcome in Book Management Service";
        return ResponseEntity.ok(msg);
    }


    @PostMapping("/create")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book with ID " + id + " has been successfully deleted.");
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> findBooksByGenre(@PathVariable String genre) {
        List<Book> books = bookService.findBooksByGenre(genre);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> findBooksByTitle(@PathVariable String title) {
        List<Book> books = bookService.findBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> findBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<Book>> findBooksByLanguage(@PathVariable String language) {
        List<Book> books = bookService.findBooksByLanguage(language);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Book>> findBooksByLocation(@PathVariable String location) {
        List<Book> books = bookService.findBooksByLocation(location);
        return ResponseEntity.ok(books);
    }

}
