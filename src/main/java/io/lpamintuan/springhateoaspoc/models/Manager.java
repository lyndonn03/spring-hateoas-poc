package io.lpamintuan.springhateoaspoc.models;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false, unique = false, length = 255)
    private String name;

    @Column(nullable = true, unique = false)
    private LocalDate dateJoined;

    @OneToMany(mappedBy = "currentManager", fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<Officer> officers;

}
