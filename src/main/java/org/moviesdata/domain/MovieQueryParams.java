package org.moviesdata.domain;

import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class MovieQueryParams {
    private Optional<String> title;

    private Optional<String> description;

    private Optional<Integer> releaseYear;

    private Optional<Page> page;

    public MovieQueryParams() {
        this.title = Optional.empty();
        this.description = Optional.empty();
        this.releaseYear = Optional.empty();
        this.page = Optional.empty();
    }

    public boolean anyParameterPresent() {
        return title.isPresent() || description.isPresent() || releaseYear.isPresent();
    }
}
