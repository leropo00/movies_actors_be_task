package org.moviesdata.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.moviesdata.model.MovieEntity;

import java.util.List;
import java.util.stream.Collectors;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.validator.YearInFuture;

@Data
public class Movie {

    @NotEmpty
    @JsonProperty("id")
    private String imdbID;

    @NotEmpty
    @Size(max = 255, message = "title should not be greater than 255")
    private String title;

    @NotEmpty
    @Size(max = 1000, message = "description should not be greater than 1000")
    private String description;

    @NotNull
    @JsonProperty("release_year")
    @Min(1850)     // oldest movie on imdb is from 1888, added several years this as buffer
    @YearInFuture(10)
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