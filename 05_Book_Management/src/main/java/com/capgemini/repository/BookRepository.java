package com.capgemini.repository;

import com.capgemini.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenre(String genre);

    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByLanguage(String language);

    List<Book> findByLocation(String location);

}
