package org.moviesdata.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "Movie")
@Table(name = "movies")
@Data
public class MovieEntity {

    @Id
    @Column(name = "imdbID")
    private String imdbID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
