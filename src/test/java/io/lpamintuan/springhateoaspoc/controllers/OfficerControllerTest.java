package io.lpamintuan.springhateoaspoc.controllers;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.lpamintuan.springhateoaspoc.controllers.assemblers.OfficerModelAssembler;
import io.lpamintuan.springhateoaspoc.models.Officer;
import io.lpamintuan.springhateoaspoc.repositories.OfficerRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(OfficerController.class)
@Import(OfficerModelAssembler.class)
public class OfficerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfficerRepository officerRepository;

    @Test
    public void getOfficerWillReturnOfficerDetails() throws Exception {
        UUID testId = UUID.randomUUID();
        Officer officer = new Officer(testId, "Officer test name", null, null);
        given(officerRepository.findById(any()))
            .willReturn(Optional.of(officer));

        mockMvc.perform(get("/officers/{id}", testId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Officer test name"))
            .andExpect(jsonPath("$._links.self.href")
                .value("http://localhost/officers/" + testId))
            .andExpect(jsonPath("$._links.collection.href")
                .value("http://localhost/officers"));

        verify(officerRepository).findById(any());

    }

    @Test
    public void getAllOfficersWillReturnListOfOfficers() throws Exception {

        UUID testId1 = UUID.randomUUID();
        Officer officer1 = new Officer(testId1, "Officer test name", null, null);

        UUID testId2 = UUID.randomUUID();
        Officer officer2 = new Officer(testId2, "Officer test name", null, null);

        given(officerRepository.findAll())
            .willReturn(List.of(officer1, officer2));

        mockMvc.perform(get("/officers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.officers").isArray())
            .andExpect(jsonPath("$._links.self.href")
                .value("http://localhost/officers"));

        verify(officerRepository).findAll();

    }

}
