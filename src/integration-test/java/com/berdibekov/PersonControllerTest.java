package com.berdibekov;

import com.berdibekov.api.controller.PersonController;
import com.berdibekov.domain.Person;
import com.berdibekov.domain.dto.PersonDto;
import com.berdibekov.dto.error.ErrorDetail;
import com.berdibekov.dto.error.ValidationError;
import com.berdibekov.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest(classes = {PersonCarApplication.class, PersonController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PersonControllerTest {

    @Inject
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void savePerson_shouldResponseBadRequestAlreadyExists_whenPersonWithIdInDateBase() throws Exception {
        PersonRepository personRepository = webApplicationContext.getBean(PersonRepository.class);
        Person personInDatabase = new Person();
        personInDatabase.setId(0L);
        personInDatabase.setName("name");
        personInDatabase.setBirthDate(new Date(1000));
        personRepository.save(personInDatabase);
        PersonDto personDto = new PersonDto();
        personDto.setName("name");
        personDto.setId("0");
        personDto.setBirthDate("12.12.2000");
        MvcResult result = mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(personDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(error.getDetail(), "Person with id : 0 already exists.");
    }

    @Test
    void savePerson_shouldResponseBadRequest_whenIdIsBlank() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName("name");
        personDto.setId("");
        personDto.setBirthDate("12.12.2000");
        MvcResult result = mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(personDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(error.getDetail(), "Input validation failed");
    }

    @Test
    void savePerson_shouldResponseBadRequest_whenIdIsNull() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName("name");
        personDto.setId(null);
        personDto.setBirthDate("12.12.2000");
        MvcResult result = mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(personDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(error.getDetail(), "Input validation failed");
    }

    @Test
    void savePerson_shouldResponseBadRequest_whenBirthDateWrongFormat() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName("name");
        personDto.setId("2");
        personDto.setBirthDate("2020-20-20");
        MvcResult result = mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(personDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(error.getDetail(), "Input validation failed");
    }

    @Test
    void savePerson_shouldResponseBadRequest_whenBirthDateIUnFuture() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName("name");
        personDto.setId("2");
        personDto.setBirthDate("02.07.2060");
        MvcResult result = mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(personDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(error.getDetail(), "Birth date can not be in future.");
    }

    @Test
    void savePerson_shouldResponseBadRequest_whenPersonIsUnderage() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setName("name");
        personDto.setId("2");
        personDto.setBirthDate("02.07.2060");
        MvcResult result = mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(personDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(error.getDetail(), "Birth date can not be in future.");
    }
}
