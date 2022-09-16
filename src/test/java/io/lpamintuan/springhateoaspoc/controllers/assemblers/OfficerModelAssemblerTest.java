package io.lpamintuan.springhateoaspoc.controllers.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import io.lpamintuan.springhateoaspoc.models.Officer;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

public class OfficerModelAssemblerTest {

    private OfficerModelAssembler assembler;

    @BeforeEach
    public void setupEachTest() {
        this.assembler = new OfficerModelAssembler();
    }

    @Test
    public void toModelReturnsOfficerEntityModelWithSelfLink() {

        Officer officer = new Officer(null, "Officer test name", null, null);

        EntityModel<Officer> officerModel = assembler.toModel(officer);
        
        assertThat(officerModel.hasLink("self")).isTrue();

    }

    @Test
    public void toModelReturnsOfficerEntityModelWithCollectionLink() {

        Officer officer = new Officer(null, "Officer test name", null, null);

        EntityModel<Officer> officerModel = assembler.toModel(officer);
        
        assertThat(officerModel.hasLink("collection")).isTrue();

    }

    @Test
    public void toCollectionModelReturnsListOfOfficerModelWithSelfRel() {

        Officer officer = new Officer(null, "Officer test name", null, null);
        
        CollectionModel<EntityModel<Officer>> officerCollection = assembler.toCollectionModel(List.of(officer));

        assertThat(officerCollection.hasLink("self")).isTrue();

    }


}
