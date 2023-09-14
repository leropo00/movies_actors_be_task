package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.Test;
import org.moviesdata.domain.Movie;
import org.moviesdata.domain.MovieQueryParams;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.repository.ActorRepository;
import org.moviesdata.service.ActorService;
import org.moviesdata.service.MovieService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ActorServiceSearchTest {
    @Inject
    ActorRepository actorRepository;

    @Inject
    ActorService actorService;

    @Test
    public void testMissingActors() {
        List<String> allIds = actorRepository.findAll().stream().map(ActorEntity::getImdbID).collect(Collectors.toList());
        // added both records twice to check that duplicates are handled correctly
        allIds.add("nm0001467");
        allIds.add("nm0000129");

        allIds.add("nm0000129");
        allIds.add("nm0001467");

        List<String> ids = actorService.findNonExistingActorEntities(allIds);
        assertEquals(List.of("nm0001467", "nm0000129"), ids);
    }
}
