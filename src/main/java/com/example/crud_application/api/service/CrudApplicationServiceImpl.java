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

    private static boolean isNullOrEquals(String firstname, String person) {

        return firstname == null || firstname.equals(person);
    }

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
        Person personAfterMapper = mapper.toEntityFromUpdateDto(personUpdateDto);
        personAfterMapper.setEmail(person.getEmail());

        person = repository.save(personUpdateFirstLastMiddleNames(personUpdateDto, personAfterMapper, person));
        log.debug("Updated Person with ID {}.", id);

        return mapper.toDto(person);
    }

    private Person personUpdateFirstLastMiddleNames(
            PersonUpdateDto personUpdateDto,
            Person personAfterMapper,
            Person personOrigin
    ) {
        String firstname = personUpdateDto.getFirstname();
        if (isNullOrEquals(firstname, personOrigin.getFirstname())) {
            personAfterMapper.setFirstname(personOrigin.getFirstname());
        }
        String lastname = personUpdateDto.getLastname();
        if (isNullOrEquals(lastname, personOrigin.getLastname())) {
            personAfterMapper.setLastname(personOrigin.getLastname());
        }
        String middlename = personUpdateDto.getMiddlename();
        if (isNullOrEquals(middlename, personOrigin.getMiddlename())) {
            personAfterMapper.setMiddlename(personOrigin.getMiddlename());
        }

        return personAfterMapper;
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
