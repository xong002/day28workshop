package practice.day28workshop.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import practice.day28workshop.model.Game;

@Repository
public class GameRepository {

    private static final String C_GAMES = "games";
    private static final String C_COMMENTS = "comments";

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.games.aggregate([
     * {
     * $match: { gid: 2}
     * },
     * {
     * $lookup: {
     * from: 'comments',
     * foreignField: 'gid',
     * localField: 'gid',
     * as: 'reviews'
     * }
     * }
     * ])
     */
    public List<Document> getGameByGameId(int id) {
        MatchOperation matchOp = Aggregation.match(Criteria.where("gid").is(id));
        LookupOperation lookupOp = Aggregation.lookup(C_COMMENTS, "gid", "gid", "reviews");
        Aggregation pipeline = Aggregation.newAggregation(matchOp, lookupOp);
        AggregationResults<Document> aggregate = mongoTemplate.aggregate(pipeline, C_GAMES, Document.class);
        return aggregate.getMappedResults();
    }

    public List<Game> getGameList(){
        Query query = new Query();

        query.fields()
        .include("name","url")
        .exclude("_id");

        List<Document> result = mongoTemplate.find(query, Document.class, C_GAMES);

        List<Game> gameList = new ArrayList<>();
        for (Document d : result){
            Game game = new Game(d.getString("name"), d.getString("url"));
            gameList.add(game);
        }

        return gameList;
    }

}
