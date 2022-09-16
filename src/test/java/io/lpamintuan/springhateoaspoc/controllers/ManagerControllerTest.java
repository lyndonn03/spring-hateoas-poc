package io.lpamintuan.springhateoaspoc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.lpamintuan.springhateoaspoc.controllers.assemblers.ManagerModelAssembler;
import io.lpamintuan.springhateoaspoc.controllers.assemblers.OfficerModelAssembler;
import io.lpamintuan.springhateoaspoc.models.Manager;
import io.lpamintuan.springhateoaspoc.models.Officer;
import io.lpamintuan.springhateoaspoc.repositories.ManagerRepository;
import io.lpamintuan.springhateoaspoc.repositories.OfficerRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(ManagerController.class)
@Import({ManagerModelAssembler.class, OfficerModelAssembler.class})
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerRepository managerRepository;

    @MockBean
    private OfficerRepository officerRepository;

    @Test
    public void getManagerReturnsManagerDetailSuccessfully() throws Exception {
        UUID testId = UUID.randomUUID();
        Manager manager = new Manager(testId, "Manager name", LocalDate.now(), null);

        given(managerRepository.findById(any()))
                .willReturn(Optional.of(manager));

        mockMvc.perform(get("/managers/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost/managers/" + testId.toString()))
                .andExpect(jsonPath("$._links.collection.href")
                        .value("http://localhost/managers"))
                .andExpect(jsonPath("$._links.officers.href")
                        .value("http://localhost/managers/" + testId.toString() + "/officers"))
                .andExpect(jsonPath("$.officers").doesNotExist());

    }

    @Test
    public void getManagersReturnsListOfManagerSuccessfully() throws Exception {

        UUID testId1 = UUID.randomUUID();
        Manager manager1 = new Manager(testId1, "Manager name 1", LocalDate.now(), null);
        UUID testId2 = UUID.randomUUID();
        Manager manager2 = new Manager(testId2, "Manager name 2", LocalDate.now(), null);

        given(managerRepository.findAll())
                .willReturn(List.of(manager1, manager2));

        mockMvc.perform(get("/managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost/managers"))
                .andExpect(jsonPath("$._embedded.managers[0].name").value("Manager name 1"));

    }

    @Test
    public void getOfficersByManagerReturnsListOfOfficersSuccessfull() throws Exception {
        Manager manager1 = new Manager(UUID.randomUUID(), "Manager name 1", LocalDate.now(), null);
        Officer officer1 = new Officer(UUID.randomUUID(), "Officer1", LocalDate.now(), manager1);
        Officer officer2 = new Officer(UUID.randomUUID(), "Officer2", LocalDate.now(), manager1);

        given(officerRepository.findAllByCurrentManager(any()))
                .willReturn(List.of(officer1, officer2));

        mockMvc.perform(get("/managers/{id}/officers", manager1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.manager.href")
                        .value("http://localhost/managers/" + manager1.getId().toString()))
                .andExpect(jsonPath("$._embedded.officers[0].name").value("Officer1"));

    }

}
