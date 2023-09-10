package org.moviesdata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class MovieQueryParams {
    private Optional<String> title;

    private Optional<String> description;

    private Optional<Integer> releaseYear;

}
