package com.ouhamza.webflux.dao;

import com.ouhamza.webflux.entities.Teams;
import com.ouhamza.webflux.entities.Matches;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author Lhouceine OUHAMZA
 */

public interface MatchesRepo extends ReactiveMongoRepository<Matches,String> {

    public Flux<Matches> findByTeams(Teams teams);
}
