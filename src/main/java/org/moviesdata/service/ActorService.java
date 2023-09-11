package org.moviesdata.service;

import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.moviesdata.domain.Actor;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.repository.ActorRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;
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

    public List<Actor> listAllActors(Page page) {
        return actorRepository.findAll()
                .page(page)
                .list().stream().map(Actor::fromEntity).collect(Collectors.toList());
    }

    public Optional<Actor> findActorById(String actorId) {
        Optional<ActorEntity> actorEntity = actorRepository.findByIdOptional(actorId);
        if(actorEntity.isEmpty()) return Optional.empty();
        return Optional.of(Actor.fromEntity(actorEntity.get()));
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
    public boolean deleteActorById(String actorId) {
        return actorRepository.deleteById(actorId);
    }
}
