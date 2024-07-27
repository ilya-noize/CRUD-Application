package com.example.crud_application.api.service;

import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;
import com.example.crud_application.exceptions.CrudApplicationNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static com.example.crud_application.api.service.CrudApplicationServiceImpl.PERSON_WITH_ID_NOT_FOUND;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class CrudApplicationServiceImplIntegrationTest {
    @Autowired
    private CrudApplicationService service;

    @Test
    void updateFirstName() {
        PersonDto personDtoSaved = getPersonDtoSaved();

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

    private PersonDto getPersonDtoSaved() {

        return service.create(PersonNewDto.builder()
                .email(getRandomEmail())
                .firstname("First")
                .lastname("Last")
                .middlename("Middle").build()
        );
    }

    private static String getRandomEmail() {

        return format("%s@mail.com", UUID.randomUUID());
    }

    @Test
    void updateFirstNameLastName() {
        PersonDto personDtoSaved = getPersonDtoSaved();

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
        PersonDto personDtoSaved = getPersonDtoSaved();

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
        PersonDto personDtoSaved = getPersonDtoSaved();

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
        PersonDto personDtoSaved = getPersonDtoSaved();

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
        PersonDto personDtoSaved = getPersonDtoSaved();

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

    @Test
    void get_existsPerson() {
        PersonDto personDtoSaved = getPersonDtoSaved();

        long id = personDtoSaved.getId();
        PersonDto personDtoGot = service.get(id);
        assertEquals(personDtoSaved, personDtoGot);
        service.delete(id);
    }

    @Test
    void get_notExistsPerson() {
        PersonDto personDtoSaved = getPersonDtoSaved();
        long id = personDtoSaved.getId();
        service.delete(id);
        assertThrows(CrudApplicationNotFoundException.class,
                () -> service.get(id),
                format(PERSON_WITH_ID_NOT_FOUND, id));
    }


    @Test
    void delete_NotExistsPerson() {
        PersonDto personDtoSaved = getPersonDtoSaved();
        long id = personDtoSaved.getId();

        service.delete(id);
        assertThrows(CrudApplicationNotFoundException.class,
                () -> service.delete(id),
                format(PERSON_WITH_ID_NOT_FOUND, id));
    }
}