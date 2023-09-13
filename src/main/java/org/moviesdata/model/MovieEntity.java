package org.moviesdata.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.moviesdata.domain.Movie;

import java.util.*;

@Entity(name = "Movie")
@Table(name = "movies")
@Setter
@Getter
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movies_cast",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<ActorEntity> actors = new HashSet<>();

    public MovieEntity() {}
    public MovieEntity(String id) {
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
        MovieEntity other = (MovieEntity) obj;
        return Objects.equals(this.imdbID, other.getImdbID());
    }

    public void addActor(ActorEntity actor) {
        this.actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(ActorEntity actor) {
        this.actors.remove(actor);
        actor.getMovies().remove(this);
    }

    public static MovieEntity fromDomain(Movie data, boolean addDate) {
        MovieEntity entity = new MovieEntity();
        entity.setImdbID(data.getImdbID());
        entity.setTitle(data.getTitle());
        entity.setDescription(data.getDescription());
        entity.setReleaseYear(data.getReleaseYear());
        if(addDate) {
            final Date cretedDate = new Date();
            entity.setCreateDate(cretedDate);
            entity.setUpdatedDate(cretedDate);
        }
        return entity;
    }
}
