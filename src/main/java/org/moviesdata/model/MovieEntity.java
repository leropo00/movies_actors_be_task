package org.moviesdata.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
}
