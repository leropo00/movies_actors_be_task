package org.moviesdata.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.moviesdata.model.MovieEntity;

@Data
public class Movie {

    @JsonProperty("id")
    private String imdbID;

    private String title;

    private String description;

    @JsonProperty("release_year")
    private Integer releaseYear;

    public static Movie fromEntity(MovieEntity entity) {
        Movie movie = new Movie();
        movie.setImdbID(entity.getImdbID());
        movie.setTitle(entity.getTitle());
        movie.setDescription(entity.getDescription());
        movie.setReleaseYear(entity.getReleaseYear());
        return movie;
    }
}