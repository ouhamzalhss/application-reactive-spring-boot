package com.ouhamza.webflux;

import com.ouhamza.webflux.dao.TeamsRepo;
import com.ouhamza.webflux.dao.MatchesRepo;
import com.ouhamza.webflux.entities.Teams;
import com.ouhamza.webflux.entities.Matches;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class WebFluxApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebFluxApplication.class, args);
    }


    @Bean
    CommandLineRunner start(TeamsRepo teamsRepo, MatchesRepo matchesRepo){
        return args->{

            teamsRepo.deleteAll().subscribe(null,null,()->{
                Stream.of("Barcelona","Real Madrid","Sevile","Atleticho").forEach(s->{
                    teamsRepo.save(new Teams(s,"FC "+s,new Random().nextInt(100-10) + 10))
                            .subscribe(team -> {
                                System.out.println(team.toString());

                                for(int i=0; i<10; i++){
                                    Matches match = new Matches();
                                    match.setInstant(Instant.now());
                                    match.setTeams(team);
                                    match.setPoints(team.getPoints() * ( 1+(Math.random()*12 - 6) / 100));
                                    matchesRepo.save(match).subscribe(t->{
                                        System.out.println(t.toString());
                                    });

                                }

                            });
                });
            });


        };
    }


}
