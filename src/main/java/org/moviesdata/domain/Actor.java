package org.moviesdata.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.moviesdata.model.ActorEntity;

@Data
public class Actor {

    @JsonProperty("id")
    private String imdbID;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String gender;

    public static Actor fromEntity(ActorEntity entity) {
        Actor actor = new Actor();
        actor.setImdbID(entity.getImdbID());
        actor.setFirstName(entity.getFirstName());
        actor.setLastName(entity.getLastName());
        actor.setGender(entity.getGender().name().toLowerCase());
        return actor;
    }

}
