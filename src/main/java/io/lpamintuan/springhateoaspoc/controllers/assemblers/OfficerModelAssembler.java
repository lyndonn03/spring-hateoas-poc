package io.lpamintuan.springhateoaspoc.controllers.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import io.lpamintuan.springhateoaspoc.controllers.OfficerController;
import io.lpamintuan.springhateoaspoc.models.Officer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;

@Component
public class OfficerModelAssembler implements RepresentationModelAssembler<Officer, EntityModel<Officer>> {

    @Override
    public EntityModel<Officer> toModel(Officer entity) {

        EntityModel<Officer> officerModel = EntityModel.of(entity);

        officerModel.add(linkTo(methodOn(OfficerController.class).getOfficer(entity.getId())).withSelfRel());
        officerModel.add(linkTo(methodOn(OfficerController.class).getOfficers()).withRel(IanaLinkRelations.COLLECTION));

        return officerModel;
        
    }

    @Override
    public CollectionModel<EntityModel<Officer>> toCollectionModel(Iterable<? extends Officer> entities) {

        List<EntityModel<Officer>> officersList = new ArrayList<>();
        for(Officer o : entities) {
            EntityModel<Officer> officerModel = EntityModel.of(o);
            officerModel.add(linkTo(methodOn(OfficerController.class).getOfficer(o.getId())).withSelfRel());
            officersList.add(officerModel);
        }

        CollectionModel<EntityModel<Officer>> officerCollection = CollectionModel.of(officersList);
        officerCollection.add(linkTo(methodOn(OfficerController.class).getOfficers()).withSelfRel());
        return officerCollection;
    }

    
    
    
}
