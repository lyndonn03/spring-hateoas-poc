package io.lpamintuan.springhateoaspoc.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.lpamintuan.springhateoaspoc.models.Officer;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, UUID> {

    List<Officer> findAllByCurrentManager(UUID id);
    
}
