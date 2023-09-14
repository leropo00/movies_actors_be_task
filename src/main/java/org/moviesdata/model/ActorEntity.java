package org.moviesdata.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.moviesdata.constants.GenderEnum;
import org.moviesdata.domain.Actor;

import java.util.*;

@Entity(name = "Actor")
@Table(name = "actors")
@Setter
@Getter
@NamedQuery(name = "Actor.findActorsById", query = "Select a from Actor a  where a.imdbID in ( :ids )")
@NamedQuery(name = "Actor.findActorWithMovies", query = "SELECT a FROM Actor a LEFT JOIN FETCH a.movies WHERE a.imdbID = :id")
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
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

    public void addMovie(MovieEntity movie) {
        this.movies.add(movie);
        movie.getActors().add(this);
    }

    public void removeMovie(MovieEntity movie) {
        this.movies.remove(movie);
        movie.getActors().remove(this);
    }

    public static ActorEntity fromDomain(Actor actor, boolean addDate) {
        ActorEntity entity = new ActorEntity();
        entity.setImdbID(actor.getImdbID());
        entity.setFirstName(actor.getFirstName());
        entity.setLastName(actor.getLastName());
        entity.setGender(actor.getGenderEnum());
        entity.setBirthDate(actor.getBirthDate());
        if(addDate) {
            final Date createdDate = new Date();
            entity.setCreateDate(createdDate);
            entity.setUpdatedDate(createdDate);
        }
        return entity;
    }
}
