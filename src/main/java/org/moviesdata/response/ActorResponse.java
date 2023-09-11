package org.moviesdata.response;

import lombok.Data;
import org.moviesdata.domain.Actor;
import java.util.List;

@Data
public class ActorResponse {
    private List<Actor> data;
    private ResponseMetadata metadata;
}
