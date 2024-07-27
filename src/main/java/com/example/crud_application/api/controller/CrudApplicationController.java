package com.example.crud_application.api.controller;

import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;
import com.example.crud_application.api.service.CrudApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
@Tag(name = "Control")
public class CrudApplicationController {
    private final CrudApplicationService service;

    @PostMapping
    @Operation(summary = "Create new person")
    public PersonDto create(@RequestBody @Valid PersonNewDto personNewDto) {
        log.debug("Create Person.");

        return service.create(personNewDto);
    }

    @PatchMapping
    @Operation(summary = "Update person")
    public PersonDto update(@RequestBody PersonUpdateDto personUpdateDto) {
        log.debug("Update Person with ID {}.", personUpdateDto.getId());

        return service.update(personUpdateDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person")
    public PersonDto get(@PathVariable @Positive Long id) {
        log.debug("Get person with ID {}.", id);

        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete person")
    public void delete(@PathVariable @Positive Long id) {
        log.debug("Delete person with ID {}.", id);
        service.delete(id);
    }
}
