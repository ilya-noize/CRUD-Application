package com.example.crud_application.api.service;

import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;

public interface CrudApplicationService {
    PersonDto create(PersonNewDto personNewDto);

    PersonDto update(PersonUpdateDto personUpdateDto);

    PersonDto get(Long id);

    void delete(Long id);
}
