package org.moviesdata.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.moviesdata.domain.Movie;
import org.moviesdata.domain.MovieQueryParams;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;
import org.moviesdata.repository.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieService {
    @Inject
    ActorService actorService;

    @Inject
    MovieRepository movieRepository;

    @Inject
    EntityManager em;

    public List<Movie> listAllMovies() {
       return movieRepository.findAll()
                .list().stream().map(Movie::fromEntity).collect(Collectors.toList());
    }

    public List<Movie> listAllMovies(boolean includeActors, Optional<Page> page) {
        PanacheQuery<MovieEntity> query = includeActors ? moviesWithActors() : movieRepository.findAll();
        if(page.isPresent()) {
            query.page(page.get());
        }
        if(includeActors) {
            return query.list().stream().map(Movie::fromEntityWithActors).collect(Collectors.toList());
        }
        else {
            return query.list().stream().map(Movie::fromEntity).collect(Collectors.toList());
        }
    }

    public Optional<Movie> findMovieById(String movieId, boolean includeActors) {
        Optional<MovieEntity> movieEntity = includeActors ? findMovieEntityWithActors(movieId) : movieRepository.findByIdOptional(movieId);
        if(movieEntity.isEmpty()) return Optional.empty();
        return Optional.of(Movie.fromEntity(movieEntity.get(), includeActors));
    }

    public Optional<Movie> findMovieById(String movieId) {
        return findMovieById(movieId, false);
    }

    public int allMoviesCount() {
        // for simplicity purposes long is cast to int here
        // this approach should allow max 2147483647 results
        return Math.toIntExact(movieRepository.count());
    }

    public int searchMoviesCount(MovieQueryParams inputParameters) {
        StringBuilder queryString = new StringBuilder("SELECT count(m) from Movie m ");
        Parameters queryParameters = new Parameters();
        prepareSearchQuery(inputParameters, queryString, queryParameters);

        Query query = em.createQuery(queryString.toString());
        Map<String, Object> params = queryParameters.map();
        for(String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        // for simplicity purposes long is cast to int here
        return Math.toIntExact((long) query.getSingleResult());
    }

    public List<Movie> searchMovies(MovieQueryParams inputParameters) {
        StringBuilder queryString = new StringBuilder("SELECT m from Movie m ");
        if(inputParameters.isIncludeActors()) queryString.append(" LEFT JOIN FETCH m.actors ");
        Parameters queryParameters = new Parameters();
        prepareSearchQuery(inputParameters, queryString, queryParameters);
        PanacheQuery<MovieEntity> query = movieRepository.find(queryString.toString(), queryParameters);
        if(inputParameters.getPage().isPresent()) {
            query.page(inputParameters.getPage().get());
        }

        if(inputParameters.isIncludeActors()) {
            return query.list().
                    stream().map(Movie::fromEntityWithActors).collect(Collectors.toList());
        }
        return query.list().
                stream().map(Movie::fromEntity).collect(Collectors.toList());
    }

    public Optional<MovieEntity> findMovieEntityWithActors(String id) {
        TypedQuery<MovieEntity> query = em.createNamedQuery("Movie.findMovieWithActors", MovieEntity.class);
        query.setParameter("id", id);
        return Optional.ofNullable( query.getResultList()
                .stream().findFirst().orElse(null));
    }

    @Transactional
    public void createMovie(Movie data) {
        MovieEntity entity = MovieEntity.fromDomain(data, true);
        setMovieActors(data, entity);
        movieRepository.persistAndFlush(entity);
    }

    @Transactional
    public void updateMovie(Movie data, String movieId) {
        MovieEntity entity = findMovieEntityWithActors(movieId).get();
        entity.setTitle(data.getTitle());
        entity.setDescription(data.getDescription());
        entity.setReleaseYear(data.getReleaseYear());
        entity.setUpdatedDate(new Date());
        setMovieActors(data, entity);
        movieRepository.persistAndFlush(entity);
    }

    @Transactional
    public boolean deleteMovieById(String movieId) {
        return movieRepository.deleteById(movieId);
    }

    private void setMovieActors(Movie data, MovieEntity entity) {
        // not setting actors means they will not be changed
        if(data.getActors() == null) return;

        // empty list removes all the actors from movie
        if(data.getActors().isEmpty()) {
            entity.setActors(new HashSet<>());
        }
        else {
            Set<ActorEntity> actors = actorService.findActorEntities(data.getActors()).stream().collect(Collectors.toSet());
            entity.setActors(actors);
        }
    }

    private void prepareSearchQuery(MovieQueryParams inputParameters, StringBuilder queryString, Parameters queryParameters) {
        if(!inputParameters.anyParameterPresent()) return;

        List<String> statements = new ArrayList<>();
        queryString.append(" where ");
        if (inputParameters.getTitle().isPresent()) {
            statements.add("lower(m.title) like lower(concat(:title ,'%'))");
            queryParameters.and("title", inputParameters.getTitle().get());
        }
        // currently implemented with case insensitive like search
        // would be better implented with fulltext, if possible in H2
        if (inputParameters.getDescription().isPresent()) {
            statements.add("lower(m.description) like lower(concat('%',:description,'%'))");
            queryParameters.and("description", inputParameters.getDescription().get());
        }
        if (inputParameters.getReleaseYear().isPresent()) {
            statements.add("m.releaseYear = :release_year");
            queryParameters.and("release_year", inputParameters.getReleaseYear().get());
        }
        queryString.append(String.join(" and ", statements));
    }

    private PanacheQuery<MovieEntity> moviesWithActors() {
        return movieRepository.find("SELECT m from Movie m LEFT JOIN FETCH m.actors");
    }
}
