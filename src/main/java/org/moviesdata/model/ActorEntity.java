package org.moviesdata.model;

import jakarta.persistence.*;

import lombok.Data;
import org.moviesdata.constants.GenderEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Actor")
@Table(name = "actors")
@Data
public class ActorEntity {

    @Id
    @Column(name = "imdbID")
    private String imdbID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "actors")
    private List<MovieEntity> movies = new ArrayList<>();
}
