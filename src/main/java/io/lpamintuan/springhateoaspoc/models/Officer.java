package io.lpamintuan.springhateoaspoc.models;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "officers")
public class Officer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;
    
    @Column(nullable = false, unique = false, length = 255)
    private String name;

    @Column(nullable = true, unique = false)
    private LocalDate dateJoined;

    @ManyToOne
    @JoinColumn(name = "current_manager")
    private Manager currentManager;
    
}
