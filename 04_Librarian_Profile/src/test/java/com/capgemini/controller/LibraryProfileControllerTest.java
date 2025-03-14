package com.capgemini.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import com.capgemini.model.LibraryProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.capgemini.model.Book;
import com.capgemini.service.LibraryProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(LibraryProfileController.class)
public class LibraryProfileControllerTest {

    @Autowired
     private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LibraryProfileService libraryProfileService;

    @MockitoBean
    private RestTemplate restTemplate;

    private LibraryProfile libraryProfile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        libraryProfile = new LibraryProfile();
        libraryProfile.setId(1L);
        libraryProfile.setAddress("Noida");
        libraryProfile.setContactNumber("9821614993");
        libraryProfile.setEmail("gunjankmr91@gmail.com");
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUpdateLibraryProfile() throws Exception {
        LibraryProfile profile = new LibraryProfile();
        profile.setId(1L);
        profile.setName("Updated Library");
        when(libraryProfileService.updateLibraryProfile(eq(1L), any(LibraryProfile.class))).thenReturn(profile);

        mockMvc.perform(put("/api/library-profiles/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Library"));
    }

    @Test
    public void testGetLibraryProfileById() throws Exception {
        LibraryProfile profile = new LibraryProfile();
        profile.setId(1L);
        when(libraryProfileService.getLibraryProfileById(1L)).thenReturn(profile);

        mockMvc.perform(get("/api/library-profiles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetAllLibraryProfiles() throws Exception {
        LibraryProfile profile1 = new LibraryProfile();
        LibraryProfile profile2 = new LibraryProfile();
        when(libraryProfileService.getAllLibraryProfiles()).thenReturn(Arrays.asList(profile1, profile2));

        mockMvc.perform(get("/api/library-profiles/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testDeleteLibraryProfile() throws Exception {
        doNothing().when(libraryProfileService).deleteLibraryProfile(1L);

        mockMvc.perform(delete("/api/library-profiles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Library profile with ID 1 has been successfully deleted."));
    }

    @Test
    void testGetWelcomeMsg() throws Exception {
        String welcomeMsg = "Welcome to the Book Management Service";
        when(libraryProfileService.getWelcomeMsg()).thenReturn(welcomeMsg);

        mockMvc.perform(get("/api/library-profiles/books/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string(welcomeMsg))
                .andDo(print());

        verify(libraryProfileService, times(1)).getWelcomeMsg();
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = new Book();
        book.setTitle("New Book");
        when(libraryProfileService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/library-profiles/books/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");

        when(libraryProfileService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/library-profiles/books/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andDo(print());

        verify(libraryProfileService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(libraryProfileService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/library-profiles/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andDo(print());

        verify(libraryProfileService, times(1)).getBookById(1L);
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).getAllBooks();
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(libraryProfileService).deleteBook(1L);

        mockMvc.perform(delete("/api/library-profiles/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ID 1 has been successfully deleted."))
                .andDo(print());

        verify(libraryProfileService, times(1)).deleteBook(1L);
    }

    @Test
    void testFindBooksByGenre() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.findBooksByGenre("Fiction")).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/genre/Fiction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).findBooksByGenre("Fiction");
    }

    @Test
    void testFindBooksByTitle() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.findBooksByTitle("Title")).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/title/Title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).findBooksByTitle("Title");
    }

    @Test
    void testFindBooksByAuthor() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.findBooksByAuthor("Author")).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/author/Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).findBooksByAuthor("Author");
    }

    @Test
    void testFindBooksByLanguage() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.findBooksByLanguage("English")).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/language/English"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).findBooksByLanguage("English");
    }

    @Test
    void testFindBooksByLocation() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.findBooksByLocation("Library")).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/location/Library"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).findBooksByLocation("Library");
    }

    @Test
    void testFindBooksByStatus() throws Exception {
        List<Book> books = List.of(new Book(), new Book());

        when(libraryProfileService.findBooksByStatus("Available")).thenReturn(books);

        mockMvc.perform(get("/api/library-profiles/books/status/Available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());

        verify(libraryProfileService, times(1)).findBooksByStatus("Available");
    }

    @Test
    void testUpdateBookStatus() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setStatus("Checked Out");

        when(libraryProfileService.updateBookStatus(eq(1L), eq("Checked Out"))).thenReturn(book);

        mockMvc.perform(put("/api/library-profiles/books/1/status")
                        .param("status", "Checked Out"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("Checked Out"))
                .andDo(print());

        verify(libraryProfileService, times(1)).updateBookStatus(eq(1L), eq("Checked Out"));
    }

}
