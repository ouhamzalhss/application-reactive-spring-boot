package com.ouhamza.webflux.web;

import com.ouhamza.webflux.dao.TeamsRepo;
import com.ouhamza.webflux.dao.MatchesRepo;
import com.ouhamza.webflux.entities.Teams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

/**
 * @author Lhouceine OUHAMZA
 */
@Controller
public class WebController {

    private MatchesRepo matchesRepo;
    private TeamsRepo teamsRepo;

    public WebController(MatchesRepo matchesRepo, TeamsRepo teamsRepo) {
        this.matchesRepo = matchesRepo;
        this.teamsRepo = teamsRepo;
    }

    @GetMapping("/index")
    public String index(Model model){
        Flux<Teams> teams = teamsRepo.findByOrderByPointsDesc();
        model.addAttribute("teams", teams);
        return "index";

    }

}
