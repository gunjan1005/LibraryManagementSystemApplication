package com.capgemini.service;

import com.capgemini.model.Book;
import java.util.List;

public interface BookService {


    List<Book> findBooksByStatus(String status);

    Book updateBookStatus(Long id, String status);
}
