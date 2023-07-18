package practice.day28workshop.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import practice.day28workshop.model.Game;
import practice.day28workshop.model.GameWithRating;
import practice.day28workshop.model.RatingDisplay;
import practice.day28workshop.service.GameService;

@RestController
@RequestMapping("/")
public class GameRestController {
    
    @Autowired
    private GameService gameService;

    @GetMapping("/game/{gameId}/reviews")
    public ResponseEntity<Game> getGameByGameId(@PathVariable int gameId){
        Optional<Game> result = gameService.getGameByGameId(gameId);
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(result.get());
    }

    @GetMapping("/games/{sortedBy}")
    public ResponseEntity<RatingDisplay> getGamesSortedByRating(@PathVariable String sortedBy){
        Boolean sort;
        if (sortedBy.equals("highest")){
            sort = true;
        } else if (sortedBy.equals("lowest")){
            sort = false;
        } else return ResponseEntity.badRequest().build();  

        List<GameWithRating> result = gameService.getGamesSortedByRating(sort);
        RatingDisplay r = new RatingDisplay(sortedBy, result, new Date());

        // JsonObject result = gameService.getGamesSortedByRating(sort);
        return ResponseEntity.ok().body(r);

    }
}
