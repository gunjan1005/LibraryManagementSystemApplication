package com.capgemini.repository;

import com.capgemini.model.LibraryProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryProfileRepository extends JpaRepository<LibraryProfile, Long> {
}
