package org.moviesdata.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;
import org.moviesdata.repository.ActorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActorService {

    @Inject
    ActorRepository actorRepository;

    public List<Actor> listAllActors() {

        return actorRepository.findAll()
                .list().stream().map(Actor::fromEntity).collect(Collectors.toList());

    }

    public Optional<Actor> findActorById(String actorId) {
        Optional<ActorEntity> actorEntity = actorRepository.findByIdOptional(actorId);
        if(actorEntity.isEmpty()) return Optional.empty();
        return Optional.of(Actor.fromEntity(actorEntity.get()));
    }
}
