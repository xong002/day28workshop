package practice.day28workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import practice.day28workshop.model.Game;
import practice.day28workshop.service.GameService;

@RestController
@RequestMapping("/game")
public class GameRestController {
    
    @Autowired
    private GameService gameService;

    @GetMapping("/{gameId}/reviews")
    public ResponseEntity<Game> getGameByGameId(@PathVariable int gameId){
        Optional<Game> result = gameService.findGameByGameId(gameId);
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(result.get());
    }
}
