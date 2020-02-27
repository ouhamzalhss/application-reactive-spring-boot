package com.ouhamza.webflux.web;

import com.ouhamza.webflux.dao.TeamsRepo;
import com.ouhamza.webflux.dao.MatchesRepo;
import com.ouhamza.webflux.entities.Teams;
import com.ouhamza.webflux.entities.Matches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * @author Lhouceine OUHAMZA
 */

@RestController
public class MatchesRestController {

    @Autowired
    private MatchesRepo matchesRepo;

    @Autowired
    private TeamsRepo teamsRepo;

    @RequestMapping("/matches")
    public Flux<Matches> getMatches(){
        return matchesRepo.findAll();
    }

    @RequestMapping("/matches/{id}")
    public Mono<Matches> getMatche(@PathVariable String id){
        return matchesRepo.findById(id);
    }

    @PostMapping("/matches")
    public Mono<Matches> save(@RequestBody Matches matches){
        return matchesRepo.save(matches);
    }

    @DeleteMapping("/matches/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return matchesRepo.deleteById(id);
    }

    @PutMapping("/matches/{id}")
    public Mono<Matches> update(@RequestBody Matches matches, @PathVariable String id){
        matches.setId(id);
        return matchesRepo.save(matches);
    }


    @RequestMapping(path="/StreamMatches", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Matches> StreamOfMatches(){
        return matchesRepo.findAll();
    }


    @RequestMapping("/matchesByTeam/{id}")
    public Flux<Matches> getMatchesByTeam(@PathVariable String id){
        Teams teams = new Teams();
        teams.setId(id);
        return matchesRepo.findByTeams(teams);
    }


    @RequestMapping(value = "/streamMatchesByTeam/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Matches> streamMatchesByTeam(@PathVariable String id){
        return teamsRepo.findById(id).flatMapMany(team->{
            Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
            Flux<Matches> matchesFlux = Flux.fromStream(Stream.generate(()->{
                Matches matche = new Matches();
                matche.setInstant(Instant.now());
                matche.setTeams(team);
                matche.setPoints(team.getPoints() * ( 1+(Math.random()*12 - 6) / 100));
                return matche;
            }));

            return Flux.zip(interval,matchesFlux)
                    .map(data->{
                        return data.getT2();
                    }).share();
        });

    }

    @RequestMapping(value = "/events/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> streamEvents(@PathVariable String id){
        WebClient webClient = WebClient.create("http://localhost:8081/");
       return webClient.get()
                .uri("eventStream/"+id)
                .retrieve()
                .bodyToFlux(Event.class);/*.map(data->{
                    return data.getValue();
               });*/
    }

    @GetMapping("/getThread")
    public String test(){
        return Thread.currentThread().getName();
    }

}




@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Event{

    private Instant instant;
    private Double value;
    private String teamId;

}