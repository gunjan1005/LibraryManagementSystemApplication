package com.capgemini.service;

import com.capgemini.model.Book;
//import com.capgemini.model.Book_Status;
import com.capgemini.repository.BookRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findBooksByStatus(String status) {

        return bookRepository.findByStatus(status);
    }

    @Override
    public Book updateBookStatus(Long id, String status) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            bookToUpdate.setStatus(status);
            return bookRepository.save(bookToUpdate);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

}
