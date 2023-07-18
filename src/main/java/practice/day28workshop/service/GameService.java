package practice.day28workshop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import practice.day28workshop.model.Game;
import practice.day28workshop.model.GameWithRating;
import practice.day28workshop.repository.CommentRepository;
import practice.day28workshop.repository.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private CommentRepository commentRepo;

    public Optional<Game> getGameByGameId(int id){
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

    public List<GameWithRating> getGamesSortedByRating(Boolean sort){
        List<Document> result = commentRepo.getGamesSortedByRating(sort);
        List<GameWithRating> gameList = new ArrayList<>();
        for(Document d: result){
            GameWithRating gameWithRating = new GameWithRating();
            gameWithRating.toGameWithRating(d);
            gameList.add(gameWithRating);
        }
        
        return gameList;


        //JsonObject method
        // List<JsonObject> jsonObjects = new ArrayList<>();
        // for(Document d : result){
        //     JsonObject jsonObj = Json.createObjectBuilder()
        //         .add("_id", d.getInteger("_id"))
        //         .add("name", d.getList("game", Document.class).get(0).getString("name"))
        //         .add("rating", d.getInteger("rating"))
        //         .add("user", d.getString("user"))
        //         .add("comment", d.getString("comment"))
        //         .add("review_id", d.getString("reviewId"))
        //         .build();
        //     jsonObjects.add(jsonObj);
        // }

        // JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        // for(JsonObject o : jsonObjects){
        //     arrBuilder.add(o);
        // }

        // return Json.createObjectBuilder()
        // .add("rating", sort)
        // .add("games", arrBuilder)
        // .add("timestamp", new Date().toString())
        // .build();
        
    }


}
