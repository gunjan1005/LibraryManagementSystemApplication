package com.capgemini.service;

import com.capgemini.model.Book;
import com.capgemini.model.LibraryProfile;
import com.capgemini.repository.LibraryProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class LibraryProfileServiceImpl implements LibraryProfileService{

    @Autowired
    private LibraryProfileRepository libraryProfileRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String BOOK_SERVICE_URL = "http://localhost:8082/api/books";

    private static final String BOOKING_STATUS_URL = "http://localhost:8083/api/books";


    //Book Management Service implementation

    @Override
    public String getWelcomeMsg() {
        String url = BOOK_SERVICE_URL + "/welcome";
        return restTemplate.getForObject(url, String.class);
    }

    public Book getBookById(Long id) {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/" + id, Book.class);
    }

    public List<Book> getAllBooks() {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/all", List.class);
    }

    public Book addBook(Book book) {
        return restTemplate.postForObject(BOOK_SERVICE_URL + "/create", book, Book.class);
    }

    public Book updateBook(Long id, Book book) {
        restTemplate.put(BOOK_SERVICE_URL + "/update/" + id, book);
        return getBookById(id);
    }

    public void deleteBook(Long id) {
        restTemplate.delete(BOOK_SERVICE_URL + "/" + id);
   }

    public List<Book> findBooksByGenre(String genre) {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/genre/" + genre, List.class);
    }

    public List<Book> findBooksByTitle(String title) {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/title/" + title, List.class);
    }

    public List<Book> findBooksByAuthor(String author) {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/author/" + author, List.class);
    }

    public List<Book> findBooksByLanguage(String language) {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/language/" + language, List.class);
    }

    public List<Book> findBooksByLocation(String location) {
        return restTemplate.getForObject(BOOK_SERVICE_URL + "/location/" + location, List.class);
    }

    //Booking Status Service implementation

    @Override
    public List<Book> findBooksByStatus(String status) {
        String url = BOOKING_STATUS_URL + "/status/" + status;
        return restTemplate.getForObject(url, List.class);
    }

    @Override
    public Book updateBookStatus(Long id, String status) {
        String url = BOOKING_STATUS_URL + "/" + id + "/status?status=" + status;
        restTemplate.put(url, status);
        return getBookById(id);
    }


    //Profile Management Service implementation

    @Override
    public LibraryProfile createLibraryProfile(LibraryProfile libraryProfile) {
        return libraryProfileRepository.save(libraryProfile);
    }

    @Override
    public LibraryProfile updateLibraryProfile(Long id, LibraryProfile libraryProfile) {

        Optional<LibraryProfile> existingProfile = libraryProfileRepository.findById(id);

        if (existingProfile.isPresent()) {

            LibraryProfile profileToUpdate = existingProfile.get();
            profileToUpdate.setName(libraryProfile.getName());
            profileToUpdate.setAddress(libraryProfile.getAddress());
            profileToUpdate.setContactNumber(libraryProfile.getContactNumber());
            profileToUpdate.setEmail(libraryProfile.getEmail());

            return libraryProfileRepository.save(profileToUpdate);
        } else {
            throw new RuntimeException("Library Profile not found with id: " + id);
        }
    }

    @Override
    public LibraryProfile getLibraryProfileById(Long id) {
        return libraryProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library Profile not found with id: " + id));
    }

    @Override
    public List<LibraryProfile> getAllLibraryProfiles() {
        return libraryProfileRepository.findAll();
    }

    @Override
    public void deleteLibraryProfile(Long id) {

        libraryProfileRepository.deleteById(id);
    }

}
