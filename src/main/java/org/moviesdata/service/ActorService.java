package org.moviesdata.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.moviesdata.domain.Actor;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;
import org.moviesdata.repository.ActorRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActorService {
    @Inject
    EntityManager em;

    @Inject
    ActorRepository actorRepository;

    public List<Actor> listAllActors() {
        return actorRepository.findAll()
                .list().stream().map(Actor::fromEntity).collect(Collectors.toList());
    }

    public List<Actor> listAllActors(boolean includeMovies, Optional<Page> page) {
        PanacheQuery<ActorEntity> query = includeMovies ? actorsWithMovies() : actorRepository.findAll();
        if(page.isPresent()) {
            query.page(page.get());
        }
        if(includeMovies) {
            return query.list().stream().map(Actor::fromEntityWithMovies).collect(Collectors.toList());
        }
        else {
            return query.list().stream().map(Actor::fromEntity).collect(Collectors.toList());
        }
    }

    public Optional<Actor> findActorById(String actorId, boolean includeMovies) {
        Optional<ActorEntity> actorEntity = includeMovies ? findActorEntityWithMovies(actorId) : actorRepository.findByIdOptional(actorId);
        if(actorEntity.isEmpty()) return Optional.empty();
        return Optional.of(Actor.fromEntity(actorEntity.get(), includeMovies));
    }

    public Optional<Actor> findActorById(String actorId) {
        return findActorById(actorId, false);
    }

    public int allActorsCount() {
        // for simplicity purposes long is cast to int here
        // this approach should allow max 2147483647 results
        return Math.toIntExact(actorRepository.count());
    }

    public List<ActorEntity> findActorEntities(List<String> ids) {
                TypedQuery<ActorEntity> query =
                                em.createQuery("Select a from Actor a " +
                        " where a.imdbID in ( :ids )", ActorEntity.class);
                query.setParameter("ids", ids);
                return query.getResultList();
    }

    public List<String> findNonExistingActorEntities(List<String> ids) {
        Set<String> uniqueActorIds = ids.stream()
                .collect(Collectors.toSet());
        Set<String> presentActor = findActorEntities(ids)
                .stream().map(ActorEntity::getImdbID).collect(Collectors.toSet());
        uniqueActorIds.removeAll(presentActor);
        return uniqueActorIds.stream().toList();
    }

    public Optional<ActorEntity> findActorEntityWithMovies(String id) {
        Parameters parameters = new Parameters();
        parameters.and("id", id);
        PanacheQuery<ActorEntity> query = actorRepository.find("SELECT a FROM Actor a " +
                " LEFT JOIN FETCH a.movies WHERE a.imdbID = :id", parameters);

        return Optional.ofNullable( query.list()
                .stream().findFirst().orElse(null));
    }


    @Transactional
    public void createActor(Actor actor) {
        ActorEntity entity = ActorEntity.fromDomain(actor, true);
        actorRepository.persistAndFlush(entity);
    }

    @Transactional
    public void updateActor(Actor data, String actorId) {
        ActorEntity entity = actorRepository.findById(actorId);
        entity.setGender(data.getGenderEnum());
        entity.setFirstName(data.getFirstName());
        entity.setLastName(data.getLastName());
        entity.setLastName(data.getLastName());
        entity.setBirthDate(data.getBirthDate());
        actorRepository.persistAndFlush(entity);
    }

    @Transactional
    public void deleteActorById(String actorId) {
        ActorEntity actor = findActorEntityWithMovies(actorId).get();

        // a copy of the movies is created, to avoid ConcurentModificationException
        // this could be inefficient if actor is present in many movies
        Set<MovieEntity> moviesReference = new HashSet<>(actor.getMovies());
        for (MovieEntity movie : moviesReference) {
            movie.removeActor(actor);
        }
        actorRepository.delete(actor);
    }

    private PanacheQuery<ActorEntity> actorsWithMovies() {
        return actorRepository.find("SELECT a FROM Actor a LEFT JOIN FETCH a.movies");
    }
}
