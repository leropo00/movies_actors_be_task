package org.moviesdata.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.moviesdata.model.MovieEntity;

import java.util.List;
import java.util.stream.Collectors;
import org.moviesdata.model.ActorEntity;

@Data
public class Movie {

    @NotEmpty
    @JsonProperty("id")
    private String imdbID;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    @JsonProperty("release_year")
    private Integer releaseYear;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> actors;

    public static Movie fromEntity(MovieEntity entity) {
        Movie movie = new Movie();
        movie.setImdbID(entity.getImdbID());
        movie.setTitle(entity.getTitle());
        movie.setDescription(entity.getDescription());
        movie.setReleaseYear(entity.getReleaseYear());
        if(entity.getActors() != null) {
            movie.setActors(entity.getActors().stream().map(ActorEntity::getImdbID).collect(Collectors.toList()));
        }
        return movie;
    }
}