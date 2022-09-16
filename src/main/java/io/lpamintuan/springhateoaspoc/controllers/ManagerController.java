package io.lpamintuan.springhateoaspoc.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.lpamintuan.springhateoaspoc.controllers.assemblers.ManagerModelAssembler;
import io.lpamintuan.springhateoaspoc.controllers.assemblers.OfficerModelAssembler;
import io.lpamintuan.springhateoaspoc.models.Manager;
import io.lpamintuan.springhateoaspoc.models.Officer;
import io.lpamintuan.springhateoaspoc.repositories.ManagerRepository;
import io.lpamintuan.springhateoaspoc.repositories.OfficerRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ManagerController {

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    OfficerRepository officerRepository;

    @Autowired
    ManagerModelAssembler managerModelAssembler;

    @Autowired
    OfficerModelAssembler officerModelAssembler;

    @GetMapping("/managers/{id}")
    public ResponseEntity<EntityModel<Manager>> getManager(@PathVariable("id") UUID id) {

        Optional<Manager> manager = managerRepository.findById(id);
        EntityModel<Manager> managerModel = managerModelAssembler.toModel(manager.get());

        return new ResponseEntity<>(managerModel, HttpStatus.OK);
    }

    @GetMapping("/managers")
    public ResponseEntity<CollectionModel<EntityModel<Manager>>> getManagers() {
        List<Manager> managers = managerRepository.findAll();
        CollectionModel<EntityModel<Manager>> managerCollectionModel = managerModelAssembler.toCollectionModel(managers);
        return new ResponseEntity<>(managerCollectionModel, HttpStatus.OK);
    }

    @GetMapping("/managers/{id}/officers")
    public ResponseEntity<CollectionModel<EntityModel<Officer>>> getOfficersByManager(@PathVariable("id") UUID id) {
        List<Officer> officers = officerRepository.findAllByCurrentManager(id);
        CollectionModel<EntityModel<Officer>> officerCollection = officerModelAssembler.toCollectionModel(officers);
        officerCollection.add(linkTo(methodOn(ManagerController.class).getManager(id)).withRel("manager"));
        return new ResponseEntity<>(officerCollection, HttpStatus.OK);
    }
    
}
