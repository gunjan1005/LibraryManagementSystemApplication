package com.capgemini.controller;

import com.capgemini.model.Book;
import com.capgemini.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/status/{status}")
    public List<Book> findBooksByStatus(@PathVariable String status) {
        List <Book> books = bookService.findBooksByStatus(status);
        return books;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Book> updateBookStatus(@PathVariable Long id, @RequestParam String status) {
        Book updatedBook = bookService.updateBookStatus(id, status);
        return ResponseEntity.ok(updatedBook);
    }

}
