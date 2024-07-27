package com.example.crud_application.api.controller;

import com.example.crud_application.api.dto.PersonDto;
import com.example.crud_application.api.dto.PersonNewDto;
import com.example.crud_application.api.dto.PersonUpdateDto;
import com.example.crud_application.api.service.CrudApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.lang.String.format;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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


    @BeforeEach
    void setUp() {
        String email = format("%s@mail.com", RANDOM.nextObject(String.class).substring(0, 7));
        personNewDto = PersonNewDto.builder()
                .email(email)
                .firstname("firstname")
                .lastname("lastname")
                .middlename("middlename").build();
        personDto = PersonDto.builder()
                .id(Math.abs(RANDOM.nextLong()))
                .firstname(personNewDto.getFirstname())
                .lastname(personNewDto.getLastname())
                .middlename(personNewDto.getMiddlename()).build();
    }

    @Test
    void create() throws Exception {
        when(service.create(personNewDto)).thenReturn(personDto);

        RequestBuilder requestBuilder = post(POST_PATH)
                .content(mapper.writeValueAsString(personNewDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

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
    void update() throws Exception {
        String firstname = "Frost";
        personDto.setFirstname(firstname);
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .id(personDto.getId())
                .firstname(firstname).build();

        when(service.update(personUpdateDto)).thenReturn(personDto);

        RequestBuilder requestBuilder = patch(PUT_PATH)
                .content(mapper.writeValueAsString(personUpdateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

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
    void delete() throws Exception {
        long id = 1L;
        doNothing().when(service).delete(id);

        mvc.perform(MockMvcRequestBuilders.delete(DELETE_PATH, id)).andExpect(status().isOk());

        verify(service, times(1)).delete(id);
    }
}