package practice.day28workshop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import practice.day28workshop.model.Game;
import practice.day28workshop.repository.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepo;

    public Optional<Game> findGameByGameId(int id){
        List<Document> result = gameRepo.getGameByGameId(id);
        if (result.isEmpty()){
            return Optional.empty();
        }

        Document doc = result.get(0);
        Game game = new Game();
        game.toGame(doc);
        
        List<Document> reviewList = doc.getList("reviews", Document.class);
        for (Document d : reviewList){
            game.getReviews().add("/review/" + d.getString("c_id"));
        }

        return Optional.ofNullable(game);

    }

}
