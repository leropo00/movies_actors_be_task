package org.moviesdata.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.moviesdata.domain.Movie;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieResponse {
    private List<Movie> data;
    private ResponseMetadata metadata;
}
