package org.moviesdata.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Actor {

    @JsonProperty("id")
    private String imdbID;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private Date birthDate;

    private String gender;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> movies;

    public static Actor fromEntity(ActorEntity entity) {
        Actor actor = new Actor();
        actor.setImdbID(entity.getImdbID());
        actor.setFirstName(entity.getFirstName());
        actor.setLastName(entity.getLastName());
        actor.setGender(entity.getGender().name().toLowerCase());
        actor.setBirthDate(entity.getBirthDate());
        if(entity.getMovies() != null) {
            actor.setMovies(entity.getMovies().stream().map(MovieEntity::getImdbID).collect(Collectors.toList()));
        }
        return actor;
    }

}
