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

import io.lpamintuan.springhateoaspoc.controllers.assemblers.OfficerModelAssembler;
import io.lpamintuan.springhateoaspoc.models.Officer;
import io.lpamintuan.springhateoaspoc.repositories.OfficerRepository;

@RestController
public class OfficerController {

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private OfficerModelAssembler assembler;

    @GetMapping("/officers/{id}")
    public ResponseEntity<EntityModel<Officer>> getOfficer(@PathVariable("id") UUID id) {
        Optional<Officer> officer = officerRepository.findById(id);
        EntityModel<Officer> officerModel = assembler.toModel(officer.get());
        return new ResponseEntity<>(officerModel, HttpStatus.OK);
    }

    @GetMapping("/officers")
    public ResponseEntity<CollectionModel<EntityModel<Officer>>> getOfficers() {

        List<Officer> officers = officerRepository.findAll();
        CollectionModel<EntityModel<Officer>> officerCollection = assembler.toCollectionModel(officers);
        
        return new ResponseEntity<>(officerCollection, HttpStatus.OK);
    }
    
}
