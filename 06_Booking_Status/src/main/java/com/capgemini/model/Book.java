package com.capgemini.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private String title;

    @Nonnull
    private String author;

    @Nonnull
    private String genre;

    @Nonnull
    private String language;

    @Nonnull
    private String location;

    @Nonnull
    private String status;

    @Nonnull
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

    @Nonnull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@Nonnull String author) {
        this.author = author;
    }

    @Nonnull
    public String getGenre() {
        return genre;
    }

    public void setGenre(@Nonnull String genre) {
        this.genre = genre;
    }

    @Nonnull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@Nonnull String language) {
        this.language = language;
    }

    @Nonnull
    public String getLocation() {
        return location;
    }

    public void setLocation(@Nonnull String location) {
        this.location = location;
    }

    @Nonnull
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nonnull String status) {
        this.status = status;
    }

}
