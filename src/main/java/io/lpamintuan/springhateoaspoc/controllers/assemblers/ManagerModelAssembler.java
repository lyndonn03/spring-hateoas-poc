package io.lpamintuan.springhateoaspoc.controllers.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import io.lpamintuan.springhateoaspoc.controllers.ManagerController;
import io.lpamintuan.springhateoaspoc.models.Manager;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;

@Component
public class ManagerModelAssembler implements RepresentationModelAssembler<Manager, EntityModel<Manager>> {

    @Override
    public EntityModel<Manager> toModel(Manager entity) {

        EntityModel<Manager> manager = EntityModel.of(entity);

        manager.add(linkTo(methodOn(ManagerController.class).getManager(entity.getId())).withSelfRel());
        manager.add(linkTo(methodOn(ManagerController.class).getManagers()).withRel(IanaLinkRelations.COLLECTION));
        manager.add(linkTo(methodOn(ManagerController.class).getOfficersByManager(entity.getId())).withRel("officers"));

        return manager;
    }

    @Override
    public CollectionModel<EntityModel<Manager>> toCollectionModel(Iterable<? extends Manager> entities) {
        List<EntityModel<Manager>> managerList = new ArrayList<>();

        for(Manager m : entities) {
            EntityModel<Manager> manager = this.toModel(m);
            managerList.add(manager);
        }
        CollectionModel<EntityModel<Manager>> managerCollection = CollectionModel.of(managerList);
        managerCollection.add(linkTo(methodOn(ManagerController.class).getManagers()).withSelfRel());
        return managerCollection;
        
    }

    
    
}
