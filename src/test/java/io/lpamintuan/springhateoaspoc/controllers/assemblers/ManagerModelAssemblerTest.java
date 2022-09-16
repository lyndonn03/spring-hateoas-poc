package io.lpamintuan.springhateoaspoc.controllers.assemblers;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import io.lpamintuan.springhateoaspoc.models.Manager;

import static org.assertj.core.api.Assertions.*;

public class ManagerModelAssemblerTest {

    private ManagerModelAssembler assembler;

    @BeforeEach
    public void setUpEachTest() {
        this.assembler = new ManagerModelAssembler();
    }

    @Test
    public void toModelReturnsEntityModelOfManagerWithSelfLink() {

        Manager manager = new Manager();
        manager.setName("Manager Name Test");
        manager.setDateJoined(LocalDate.now());

        EntityModel<Manager> result = assembler.toModel(manager);
        
        assertThat(result.hasLink("self")).isTrue();
    }

    @Test
    public void toModelReturnsEntityModelOfManagerWithBaseCollectionLink() {

        Manager manager = new Manager();
        manager.setName("Manager Name Test");
        manager.setDateJoined(LocalDate.now());

        EntityModel<Manager> result = assembler.toModel(manager);
        
        assertThat(result.hasLink("collection")).isTrue();

    }

    @Test
    public void toModelReturnsEntityModelOfManagerWithOfficersLink() {

        Manager manager = new Manager();
        manager.setName("Manager Name Test");
        manager.setDateJoined(LocalDate.now());

        EntityModel<Manager> result = assembler.toModel(manager);
        
        assertThat(result.hasLink("officers")).isTrue();

    }

    @Test
    public void toCollectionModelReturnsCollectionOfManagerModelWithSelfRel() {

        Manager manager1 = new Manager(null, "Manager name 1", LocalDate.now(), null);
        Manager manager2 = new Manager(null, "Manager name 2", LocalDate.now(), null);

        CollectionModel<EntityModel<Manager>> managerCollection = 
            assembler.toCollectionModel(List.of(manager1, manager2));

        assertThat(managerCollection.hasLink("self")).isTrue();


    }
}
