package org.moviesdata.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.moviesdata.domain.Actor;
import java.util.List;

@Data
@AllArgsConstructor
public class ActorResponse {
    private List<Actor> data;
    private ResponseMetadata metadata;
}
