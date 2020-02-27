package com.ouhamza.webflux.dao;

import com.ouhamza.webflux.entities.Teams;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author Lhouceine OUHAMZA
 */

public interface TeamsRepo extends ReactiveMongoRepository<Teams,String> {

    public Flux<Teams> findByOrderByPointsDesc();
}
