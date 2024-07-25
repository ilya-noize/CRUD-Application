package com.example.crud_application.api.dto;

import com.example.crud_application.entity.Person;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PersonMapper {
    PersonDto toDto(Person person);

    @Mapping(target = "id", ignore = true)
    Person toEntityFromNewDto(PersonNewDto personNewDto);

    @Mapping(target = "email", ignore = true)
    Person toEntityFromUpdateDto(PersonUpdateDto personUpdateDto);
}
