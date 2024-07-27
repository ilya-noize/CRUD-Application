package com.example.crud_application.api.service;

import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class CrudApplicationServiceImplIntegrationTest {
    @Autowired
    private CrudApplicationService service;

    @Test
    void updateFirstName() {
        PersonNewDto personNewDto = PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build();
        PersonDto personDtoSaved = service.create(personNewDto);

        long id = personDtoSaved.getId();
        String update_first = "UPDATE First";
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(id)
                .firstname(update_first).build();

        PersonDto personDtoUpdated = service.update(personUpdateDto);
        assertEquals(id, personDtoUpdated.getId());
        assertEquals(update_first, personDtoUpdated.getFirstname());
        assertEquals("Last", personDtoUpdated.getLastname());
        assertEquals("Middle", personDtoUpdated.getMiddlename());
        service.delete(id);
    }

    private static String getRandomEmail() {

        return format("%s@mail.com", UUID.randomUUID());
    }

    @Test
    void updateFirstNameLastName() {
        PersonNewDto personNewDto = PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build();
        PersonDto personDtoSaved = service.create(personNewDto);

        long id = personDtoSaved.getId();
        String update_first = "UPDATE First";
        String update_last = "UPDATE Last";
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(id)
                .firstname(update_first)
                .lastname(update_last).build();

        PersonDto personDtoUpdated = service.update(personUpdateDto);
        assertEquals(id, personDtoUpdated.getId());
        assertEquals(update_first, personDtoUpdated.getFirstname());
        assertEquals(update_last, personDtoUpdated.getLastname());
        assertEquals("Middle", personDtoUpdated.getMiddlename());
        service.delete(id);
    }

    @Test
    void updateFirstNameLastNameMiddleName() {
        PersonNewDto personNewDto = PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build();
        PersonDto personDtoSaved = service.create(personNewDto);

        long id = personDtoSaved.getId();
        String update_first = "UPDATE First";
        String update_last = "UPDATE Last";
        String update_middle = "UPDATE Middle";
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(id)
                .firstname(update_first)
                .lastname(update_last)
                .middlename(update_middle).build();

        PersonDto personDtoUpdated = service.update(personUpdateDto);
        assertEquals(id, personDtoUpdated.getId());
        assertEquals(update_first, personDtoUpdated.getFirstname());
        assertEquals(update_last, personDtoUpdated.getLastname());
        assertEquals(update_middle, personDtoUpdated.getMiddlename());
        service.delete(id);
    }

    @Test
    void updateLastNameMiddleName() {
        PersonNewDto personNewDto = PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build();
        PersonDto personDtoSaved = service.create(personNewDto);

        long id = personDtoSaved.getId();
        String update_last = "UPDATE Last";
        String update_middle = "UPDATE Middle";
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(id)
                .firstname(personDtoSaved.getFirstname())
                .lastname(update_last)
                .middlename(update_middle).build();

        PersonDto personDtoUpdated = service.update(personUpdateDto);
        assertEquals(id, personDtoUpdated.getId());
        assertEquals(personDtoSaved.getFirstname(), personDtoUpdated.getFirstname());
        assertEquals(update_last, personDtoUpdated.getLastname());
        assertEquals(update_middle, personDtoUpdated.getMiddlename());
        service.delete(id);
    }

    @Test
    void updateFirstNameMiddleName() {
        PersonNewDto personNewDto = PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build();
        PersonDto personDtoSaved = service.create(personNewDto);

        long id = personDtoSaved.getId();
        String update_first = "UPDATE First";
        String update_middle = "UPDATE Middle";
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(id)
                .firstname(update_first)
                .lastname(personDtoSaved.getLastname())
                .middlename(update_middle).build();

        PersonDto personDtoUpdated = service.update(personUpdateDto);
        assertEquals(id, personDtoUpdated.getId());
        assertEquals(update_first, personDtoUpdated.getFirstname());
        assertEquals(personDtoSaved.getLastname(), personDtoUpdated.getLastname());
        assertEquals(update_middle, personDtoUpdated.getMiddlename());
        service.delete(id);
    }


    @Test
    void updateMiddleName() {
        PersonNewDto personNewDto = PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build();
        PersonDto personDtoSaved = service.create(personNewDto);

        long id = personDtoSaved.getId();
        String update_middle = "UPDATE Middle";
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(id)
                .firstname(personDtoSaved.getFirstname())
                .lastname(personDtoSaved.getLastname())
                .middlename(update_middle).build();

        PersonDto personDtoUpdated = service.update(personUpdateDto);
        assertEquals(id, personDtoUpdated.getId());
        assertEquals(personDtoSaved.getFirstname(), personDtoUpdated.getFirstname());
        assertEquals(personDtoSaved.getLastname(), personDtoUpdated.getLastname());
        assertEquals(update_middle, personDtoUpdated.getMiddlename());
        service.delete(id);
    }
}