package io.lpamintuan.springhateoaspoc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.lpamintuan.springhateoaspoc.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    
}
