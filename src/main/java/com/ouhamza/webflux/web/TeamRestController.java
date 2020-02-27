package com.ouhamza.webflux.web;

import com.ouhamza.webflux.dao.TeamsRepo;
import com.ouhamza.webflux.entities.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lhouceine OUHAMZA
 */

@RestController
public class TeamRestController {

    @Autowired
    private TeamsRepo teamsRepo;

    @RequestMapping("/teams")
    public Flux<Teams> getTeams(){
        return teamsRepo.findAll();
    }

    @RequestMapping("/teams/{id}")
    public Mono<Teams> getTeam(@PathVariable String id){
        return teamsRepo.findById(id);
    }

    @PostMapping("/teams")
    public Mono<Teams> saveTeam(@RequestBody Teams soc){
        return teamsRepo.save(soc);
    }

    @DeleteMapping("/teams/{id}")
    public Mono<Void> deleteTeam(@PathVariable String id){
        return teamsRepo.deleteById(id);
    }

    @PutMapping("/teams/{id}")
    public Mono<Teams> deleteTeam(@RequestBody Teams team, @PathVariable String id){
        team.setId(id);
        return teamsRepo.save(team);
    }
}
