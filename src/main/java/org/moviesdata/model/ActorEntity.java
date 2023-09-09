package org.moviesdata.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.moviesdata.constants.GenderEnum;

import java.util.*;

@Entity(name = "Actor")
@Table(name = "actors")
@Setter
@Getter
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

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "actors")
    private Set<MovieEntity> movies = new HashSet<>();

    public ActorEntity() {}

    public ActorEntity(String id) {
        this.imdbID = id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.imdbID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActorEntity other = (ActorEntity) obj;
        return Objects.equals(this.imdbID, other.getImdbID());
    }
}
