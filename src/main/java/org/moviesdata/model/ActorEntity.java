package org.moviesdata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;
import org.moviesdata.constants.GenderEnum;

import java.util.Date;

@Entity(name = "Actor")
@Table(name = "actors")
@Data
public class ActorEntity {

    @Id
    @Column(name = "imdbID")
    private String imdbID;

    @Id
    @Column(name = "first_name")
    private String firstName;

    @Id
    @Column(name = "last_name")
    private String lastName;

    @Id
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
