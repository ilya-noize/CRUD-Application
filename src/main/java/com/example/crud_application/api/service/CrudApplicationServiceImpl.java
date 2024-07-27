package com.example.crud_application.api.service;

import com.example.crud_application.api.CrudApplicationRepository;
import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonMapper;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;
import com.example.crud_application.entity.Person;
import com.example.crud_application.exceptions.CrudApplicationEmailExistsException;
import com.example.crud_application.exceptions.CrudApplicationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrudApplicationServiceImpl implements CrudApplicationService {
    public static final String PERSON_WITH_ID_NOT_FOUND = "Person with ID:%s not found.";
    private final CrudApplicationRepository repository;
    private final PersonMapper mapper;

    @Override
    public PersonDto create(PersonNewDto personNewDto) {
        String email = personNewDto.getEmail();
        if (repository.existsByEmailIgnoreCase(email)) {

            throw new CrudApplicationEmailExistsException(format("E-mail %s exists.", email));
        }

        Person person = repository.save(mapper.toEntityFromNewDto(personNewDto));

        log.debug("Created Person with ID {}.", person.getId());

        return mapper.toDto(person);
    }

    @Override
    public PersonDto update(PersonUpdateDto personUpdateDto) {
        Long id = personUpdateDto.getId();
        Person person = repository.findById(id).orElseThrow(
                () -> new CrudApplicationNotFoundException(format(PERSON_WITH_ID_NOT_FOUND, id))
        );
        Person personUpdate = mapper.toEntityFromUpdateDto(personUpdateDto);

        personUpdate.setEmail(person.getEmail());

        String firstname = personUpdate.getFirstname();
        if (firstname == null || firstname.equals(person.getFirstname())) {
            personUpdate.setFirstname(person.getFirstname());
        }
        String lastname = personUpdate.getLastname();
        if (lastname == null || lastname.equals(person.getLastname())) {
            personUpdate.setLastname(person.getLastname());
        }
        String middlename = personUpdate.getMiddlename();
        if (middlename == null || middlename.equals(person.getMiddlename())) {
            personUpdate.setFirstname(person.getMiddlename());
        }
        person = repository.save(personUpdate);
        log.debug("Updated Person with ID {}.", id);

        return mapper.toDto(person);
    }

    @Override
    public PersonDto get(Long id) {
        Person person = repository.findById(id).orElseThrow(
                () -> new CrudApplicationNotFoundException(format(PERSON_WITH_ID_NOT_FOUND, id))
        );
        log.debug("Got person with ID {}.", id);

        return mapper.toDto(person);
    }

    @Override
    public PersonDto getByParams(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Person person = repository.findById(id).orElseThrow(
                () -> new CrudApplicationNotFoundException(format(PERSON_WITH_ID_NOT_FOUND, id))
        );
        repository.delete(person);
        log.debug("Deleted person with ID {}", id);
    }
}
