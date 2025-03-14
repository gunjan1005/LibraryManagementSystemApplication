package com.capgemini.service;

import com.capgemini.model.Book;
import com.capgemini.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {

        Optional<Book> existingBook = bookRepository.findById(id);

        if (existingBook.isPresent()) {

            Book bookToUpdate = existingBook.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setGenre(book.getGenre());
            bookToUpdate.setLanguage(book.getLanguage());
            bookToUpdate.setLocation(book.getLocation());// Update location

            return bookRepository.save(bookToUpdate);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    @Override
    public List<Book> findBooksByLocation(String location) {
        return bookRepository.findByLocation(location);
    }

}
