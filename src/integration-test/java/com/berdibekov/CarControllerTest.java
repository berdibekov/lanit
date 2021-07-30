package com.berdibekov;

import com.berdibekov.api.controller.CarController;
import com.berdibekov.domain.dto.CarDto;
import com.berdibekov.dto.error.ErrorDetail;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest(classes = {PersonCarApplication.class, CarController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestPropertySource(locations = "classpath:application-default.properties")
public class CarControllerTest {
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
    void saveCar_shouldResponseOk200_whenOwnerExists() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setId("546546546");
        carDto.setOwnerId("1");
        carDto.setModel("BMW-EQV 300 Extra-long");
        carDto.setHoursPower("10");
        mockMvc.perform(post("/api/car").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(carDto))).andExpect(status().isOk()).andReturn();
    }

    @Test
    void saveCar_shouldResponseNotFound_whenPersonWithIdNotExists() throws Exception {
        String expected = "Owner with id : 454333 not found.";
        CarDto carDto = new CarDto();
        carDto.setId("10");
        carDto.setOwnerId("454333");
        carDto.setModel("BMW-X5");
        carDto.setHoursPower("10");
        MvcResult result = mockMvc.perform(post("/api/car").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(carDto))).andExpect(status().isNotFound()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(expected ,error.getDetail());
    }

    @Test
    void saveCar_shouldResponseBadRequestWithAllFieldIssueMessages_whenPersonWithIdNotExists() throws Exception {
        String expectedMessage = "Input validation failed";
        Set<String> expectedErrorCodes = new HashSet<>(List.of("id", "hoursPower", "model", "ownerId"));
        CarDto carDto = new CarDto();
        carDto.setId("");
        carDto.setOwnerId(null);
        carDto.setModel("BMW-");
        carDto.setHoursPower("-1");
        MvcResult result = mockMvc.perform(post("/api/car").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(carDto))).andExpect(status().isBadRequest()).andReturn();
        ErrorDetail error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetail.class);
        assertEquals(expectedMessage, error.getDetail());
        Set<String> actualMessagesCodes = error.getErrors().keySet();

        assertEquals(expectedErrorCodes, actualMessagesCodes);
    }
}
