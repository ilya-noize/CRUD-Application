package com.example.crud_application.api.controller;

import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;
import com.example.crud_application.api.service.CrudApplicationServiceImpl;
import com.example.crud_application.exceptions.CrudApplicationEmailExistsException;
import com.example.crud_application.exceptions.CrudApplicationNotFoundException;
import com.example.crud_application.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZonedDateTime;

import static com.example.crud_application.api.service.CrudApplicationServiceImpl.PERSON_WITH_ID_NOT_FOUND;
import static java.lang.String.format;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CrudApplicationController.class)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CrudApplicationControllerTest {
    public static final EasyRandom RANDOM = new EasyRandom();
    public static final long RANDOM_ID = Math.abs(RANDOM.nextLong());

    private final String POST_PATH = "/api/v1/person";
    private final String PUT_PATH = POST_PATH;
    private final String GET_PATH = "/api/v1/person/{id}";
    private final String DELETE_PATH = GET_PATH;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CrudApplicationServiceImpl service;

    private PersonNewDto personNewDto;
    private PersonDto personDto;
    private PersonUpdateDto personUpdateDto;


    @BeforeEach
    void setUp() {
        String email = format("%s@mail.com", RANDOM.nextObject(String.class));
        personNewDto = PersonNewDto.builder()
                .email(email)
                .firstname("firstname")
                .lastname("lastname")
                .middlename("middlename").build();
        personDto = PersonDto.builder()
                .id(RANDOM_ID)
                .firstname(personNewDto.getFirstname())
                .lastname(personNewDto.getLastname())
                .middlename(personNewDto.getMiddlename()).build();
        personUpdateDto = PersonUpdateDto.builder()
                .id(personDto.getId()).build();
    }

    @Test
    void create() throws Exception {
        when(service.create(personNewDto)).thenReturn(personDto);

        RequestBuilder requestBuilder = post(POST_PATH)
                .content(mapper.writeValueAsString(personNewDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        ResultMatcher[] resultMatchers = {
                jsonPath("$.id").value(personDto.getId().toString()),
                jsonPath("$.firstname").value(personDto.getFirstname()),
                jsonPath("$.lastname").value(personDto.getLastname()),
                jsonPath("$.middlename").value(personDto.getMiddlename())
        };

        mvc.perform(requestBuilder)
                .andExpectAll(resultMatchers)
                .andExpect(status().isOk());
    }

    @Test
    void create_400() throws Exception {
        String email = personNewDto.getEmail();
        String errorMessage = format("E-mail %s exists.", email);
        when(service.create(personNewDto)).thenThrow(
                new CrudApplicationEmailExistsException(errorMessage)
        );

        RequestBuilder requestBuilder = post(POST_PATH)
                .content(mapper.writeValueAsString(personNewDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        ZonedDateTime now = ZonedDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), errorMessage, now);
        ResultMatcher[] resultMatchers = {
                jsonPath("$.status").value(errorResponse.status()),
                jsonPath("$.error").value(errorResponse.error()),
                jsonPath("$.dateTime").exists()
        };

        mvc.perform(requestBuilder)
                .andExpectAll(resultMatchers)
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_500() throws Exception {
        String notSupportedContentType = "NOT_SUPPORTED_CONTENT_TYPE";

        RequestBuilder requestBuilder = post(POST_PATH)
                .content(mapper.writeValueAsString(personNewDto))
                .contentType(notSupportedContentType)
                .accept(notSupportedContentType);

        mvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    void update() throws Exception {
        String firstname = "Frost";
        personUpdateDto.setFirstname(firstname);
        personDto.setFirstname(firstname);

        when(service.update(personUpdateDto)).thenReturn(personDto);

        RequestBuilder requestBuilder = patch(PUT_PATH)
                .content(mapper.writeValueAsString(personUpdateDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        ResultMatcher[] resultMatchers = {
                jsonPath("$.id").value(personDto.getId().toString()),
                jsonPath("$.firstname").value(personDto.getFirstname()),
                jsonPath("$.lastname").value(personDto.getLastname()),
                jsonPath("$.middlename").value(personDto.getMiddlename())
        };

        mvc.perform(requestBuilder)
                .andExpectAll(resultMatchers)
                .andExpect(status().isOk());
    }

    @Test
    void update_404() throws Exception {

        String errorMessage = format(PERSON_WITH_ID_NOT_FOUND, RANDOM_ID);
        when(service.update(personUpdateDto)).thenThrow(
                new CrudApplicationNotFoundException(errorMessage)
        );
        RequestBuilder requestBuilder = patch(PUT_PATH)
                .content(mapper.writeValueAsString(personUpdateDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        ZonedDateTime now = ZonedDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND.value(), errorMessage, now);
        ResultMatcher[] resultMatchers = {
                jsonPath("$.status").value(errorResponse.status()),
                jsonPath("$.error").value(errorResponse.error()),
                jsonPath("$.dateTime").exists()
        };

        mvc.perform(requestBuilder)
                .andExpectAll(resultMatchers)
                .andExpect(status().isNotFound());
    }

    @Test
    void get() throws Exception {
        long id = personDto.getId();

        when(service.get(id)).thenReturn(personDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_PATH, id);

        ResultMatcher[] resultMatchers = {
                jsonPath("$.id").value(personDto.getId()),
                jsonPath("$.firstname").value(personDto.getFirstname()),
                jsonPath("$.lastname").value(personDto.getLastname()),
                jsonPath("$.middlename").value(personDto.getMiddlename()),
        };

        mvc.perform(requestBuilder)
                .andExpectAll(resultMatchers)
                .andExpect(status().isOk());
    }

    @Test
    void get_404() throws Exception {

        String errorMessage = format(PERSON_WITH_ID_NOT_FOUND, RANDOM_ID);
        when(service.get(RANDOM_ID)).thenThrow(
                new CrudApplicationNotFoundException(errorMessage)
        );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_PATH, RANDOM_ID);

        ZonedDateTime now = ZonedDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND.value(), errorMessage, now);
        ResultMatcher[] resultMatchers = {
                jsonPath("$.status").value(errorResponse.status()),
                jsonPath("$.error").value(errorResponse.error()),
                jsonPath("$.dateTime").exists()
        };

        mvc.perform(requestBuilder)
                .andExpectAll(resultMatchers)
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(service).delete(RANDOM_ID);

        mvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, RANDOM_ID)).andExpect(status().isOk());

        verify(service, times(1)).delete(RANDOM_ID);
    }
}