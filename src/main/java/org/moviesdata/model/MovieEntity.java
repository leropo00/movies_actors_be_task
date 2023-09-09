package org.moviesdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "Movie")
@Table(name = "movies")
@Data
public class MovieEntity {

    @Id
    @Column(name = "imdbID")
    private String imdbID;
}
