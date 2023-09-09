package org.moviesdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "Actor")
@Table(name = "actors")
@Data
public class ActorEntity {

    @Id
    @Column(name = "imdbID")
    private String imdbID;
}
