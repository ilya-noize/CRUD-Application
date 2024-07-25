package com.example.crud_application.api;

import com.example.crud_application.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudApplicationRepository extends JpaRepository<Person, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
