package com.capgemini.service;

import com.capgemini.controller.LibraryProfileController;
import com.capgemini.model.LibraryProfile;
import com.capgemini.model.Book;
import com.capgemini.repository.LibraryProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LibraryProfileServiceTest {

    //@Autowired
   // @MockitoBean
    @InjectMocks
    private LibraryProfileServiceImpl libraryProfileService;

//    @MockitoBean
//    private LibraryProfileService libraryProfileServices;

    @InjectMocks
    private LibraryProfileController libraryProfileController;

    @Mock
    private LibraryProfileRepository libraryProfileRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testCreateLibraryProfile() {
        LibraryProfile profile = new LibraryProfile();
        when(libraryProfileRepository.save(profile)).thenReturn(profile);

        LibraryProfile createdProfile = libraryProfileService.createLibraryProfile(profile);

        assertNotNull(createdProfile);
        verify(libraryProfileRepository, times(1)).save(profile);
    }

    @Test
    void testUpdateLibraryProfile() {
        Long id = 1L;
        LibraryProfile profile = new LibraryProfile();
        profile.setName("Gunjan");

        LibraryProfile existingProfile = new LibraryProfile();
        existingProfile.setId(id);

        when(libraryProfileRepository.findById(id)).thenReturn(Optional.of(existingProfile));
        when(libraryProfileRepository.save(existingProfile)).thenReturn(existingProfile);

        LibraryProfile updatedProfile = libraryProfileService.updateLibraryProfile(id, profile);

        assertNotNull(updatedProfile);
        assertEquals("Gunjan", updatedProfile.getName());
        verify(libraryProfileRepository, times(1)).findById(id);
        verify(libraryProfileRepository, times(1)).save(existingProfile);
    }

    @Test
    void testGetLibraryProfileById() {
        Long id = 1L;
        LibraryProfile profile = new LibraryProfile();
        when(libraryProfileRepository.findById(id)).thenReturn(Optional.of(profile));

        LibraryProfile foundProfile = libraryProfileService.getLibraryProfileById(id);

        assertNotNull(foundProfile);
        verify(libraryProfileRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllLibraryProfiles() {
        List<LibraryProfile> profiles = List.of(new LibraryProfile(), new LibraryProfile());
        when(libraryProfileRepository.findAll()).thenReturn(profiles);

        List<LibraryProfile> foundProfiles = libraryProfileService.getAllLibraryProfiles();

        assertNotNull(foundProfiles);
        assertEquals(2, foundProfiles.size());
        verify(libraryProfileRepository, times(1)).findAll();
    }

    @Test
    void testDeleteLibraryProfile() {
        Long id = 1L;
        doNothing().when(libraryProfileRepository).deleteById(id);

        libraryProfileService.deleteLibraryProfile(id);

        verify(libraryProfileRepository, times(1)).deleteById(id);
    }


    @Test
    void testGetWelcomeMsg() {
        String welcomeMsg = "Welcome to the Book Management Service";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(welcomeMsg);

        String result = libraryProfileService.getWelcomeMsg();

        assertEquals(welcomeMsg, result);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }


//    @Test
//    void testGetWelcomeMsg() {
//        String welcomeMsg = "Welcome to the Book Management Service";
//        when(libraryProfileService.getWelcomeMsg()).thenReturn(welcomeMsg);
//
//        String result = libraryProfileService.getWelcomeMsg();
//
//        assertEquals(welcomeMsg, result);
//        verify(libraryProfileService, times(1)).getWelcomeMsg();
//    }


    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        when(restTemplate.getForObject("http://localhost:8082/api/books/1", Book.class)).thenReturn(book);

        Book foundBook = libraryProfileService.getBookById(1L);
        assertEquals(1L, foundBook.getId());
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(restTemplate.getForObject("http://localhost:8082/api/books/all", List.class)).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = libraryProfileService.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("New Book");
        when(restTemplate.postForObject("http://localhost:8082/api/books/create", book, Book.class)).thenReturn(book);

        Book createdBook = libraryProfileService.addBook(book);
        assertEquals("New Book", createdBook.getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");
        when(restTemplate.getForObject("http://localhost:8082/api/books/1", Book.class)).thenReturn(book);
        doNothing().when(restTemplate).put("http://localhost:8082/api/books/update/1", book);

        Book updatedBook = libraryProfileService.updateBook(1L, book);
        assertEquals("Updated Book", updatedBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(restTemplate).delete("http://localhost:8082/api/books/1");

        libraryProfileService.deleteBook(1L);
        verify(restTemplate, times(1)).delete("http://localhost:8082/api/books/1");
    }

    @Test
    public void testFindBooksByGenre() {
        Book book = new Book();
        book.setGenre("Fiction");
        when(restTemplate.getForObject("http://localhost:8082/api/books/genre/Fiction", List.class)).thenReturn(Arrays.asList(book));

        List<Book> books = libraryProfileService.findBooksByGenre("Fiction");
        assertEquals(1, books.size());
        assertEquals("Fiction", books.get(0).getGenre());
    }

    @Test
    public void testFindBooksByTitle() {
        Book book = new Book();
        book.setTitle("Test Title");
        when(restTemplate.getForObject("http://localhost:8082/api/books/title/Test Title", List.class)).thenReturn(Arrays.asList(book));

        List<Book> books = libraryProfileService.findBooksByTitle("Test Title");
        assertEquals(1, books.size());
        assertEquals("Test Title", books.get(0).getTitle());
    }

    @Test
    public void testFindBooksByAuthor() {
        Book book = new Book();
        book.setAuthor("Test Author");
        when(restTemplate.getForObject("http://localhost:8082/api/books/author/Test Author", List.class)).thenReturn(Arrays.asList(book));

        List<Book> books = libraryProfileService.findBooksByAuthor("Test Author");
        assertEquals(1, books.size());
        assertEquals("Test Author", books.get(0).getAuthor());
    }

    @Test
    public void testFindBooksByLanguage() {
        Book book = new Book();
        book.setLanguage("English");
        when(restTemplate.getForObject("http://localhost:8082/api/books/language/English", List.class)).thenReturn(Arrays.asList(book));

        List<Book> books = libraryProfileService.findBooksByLanguage("English");
        assertEquals(1, books.size());
        assertEquals("English", books.get(0).getLanguage());
    }

    @Test
    public void testFindBooksByLocation() {
        Book book = new Book();
        book.setLocation("Shelf A");
        when(restTemplate.getForObject("http://localhost:8082/api/books/location/Shelf A", List.class)).thenReturn(Arrays.asList(book));

        List<Book> books = libraryProfileService.findBooksByLocation("Shelf A");
        assertEquals(1, books.size());
        assertEquals("Shelf A", books.get(0).getLocation());
    }

    @Test
    public void testFindBooksByStatus() {
        Book book = new Book();
        book.setStatus("Available");
        when(restTemplate.getForObject("http://localhost:8083/api/books/status/Available", List.class)).thenReturn(Arrays.asList(book));

        List<Book> books = libraryProfileService.findBooksByStatus("Available");
        assertEquals(1, books.size());
        assertEquals("Available", books.get(0).getStatus());
    }

    //@Disabled
    @Test
    public void testUpdateBookStatus() {
        Book book = new Book();
        book.setId(1L);
        book.setStatus("Checked Out");
        when(restTemplate.getForObject("http://localhost:8083/api/books/1", Book.class)).thenReturn(book);
        doNothing().when(restTemplate).put("http://localhost:8083/api/books/1/status?status=Checked Out", "Checked Out");
        Book updatedBook = libraryProfileService.updateBookStatus(1L, "Checked Out");
//        assertEquals("Checked Out", updatedBook.getStatus());
    }




/*
    @Test
    void testGetAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.getAllBooks()).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.getAllBooks();

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).getAllBooks();
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        when(libraryProfileService.addBook(book)).thenReturn(book);

        Book addedBook = libraryProfileService.addBook(book);

        assertNotNull(addedBook);
        verify(libraryProfileService, times(1)).addBook(book);
    }

    @Test
    void testUpdateBook() {
        Long id = 1L;
        Book book = new Book();
        when(libraryProfileService.updateBook(id, book)).thenReturn(book);

        Book updatedBook = libraryProfileService.updateBook(id, book);

        assertNotNull(updatedBook);
        verify(libraryProfileService, times(1)).updateBook(id, book);
    }

    @Test
    void testDeleteBook() {
        Long id = 1L;
        doNothing().when(libraryProfileService).deleteBook(id);

        libraryProfileService.deleteBook(id);

        verify(libraryProfileService, times(1)).deleteBook(id);
    }

    @Test
    void testFindBooksByGenre() {
        String genre = "Fiction";
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.findBooksByGenre(genre)).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.findBooksByGenre(genre);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).findBooksByGenre(genre);
    }

    @Test
    void testFindBooksByTitle() {
        String title = "Title";
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.findBooksByTitle(title)).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.findBooksByTitle(title);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).findBooksByTitle(title);
    }

    @Test
    void testFindBooksByAuthor() {
        String author = "Author";
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.findBooksByAuthor(author)).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.findBooksByAuthor(author);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).findBooksByAuthor(author);
    }

    @Test
    void testFindBooksByLanguage() {
        String language = "English";
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.findBooksByLanguage(language)).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.findBooksByLanguage(language);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).findBooksByLanguage(language);
    }

    @Test
    void testFindBooksByLocation() {
        String location = "Library";
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.findBooksByLocation(location)).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.findBooksByLocation(location);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).findBooksByLocation(location);
    }

    @Test
    void testFindBooksByStatus() {
        String status = "Available";
        List<Book> books = List.of(new Book(), new Book());
        when(libraryProfileService.findBooksByStatus(status)).thenReturn(books);

        List<Book> foundBooks = libraryProfileService.findBooksByStatus(status);

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        verify(libraryProfileService, times(1)).findBooksByStatus(status);
    }

    @Test
    void testUpdateBookStatus() {
        Long id = 1L;
        String status = "Checked Out";
        Book book = new Book();
        when(libraryProfileService.updateBookStatus(id, status)).thenReturn(book);

        Book updatedBook = libraryProfileService.updateBookStatus(id, status);

        assertNotNull(updatedBook);
        verify(libraryProfileService, times(1)).updateBookStatus(id, status);
    }

*/





}
