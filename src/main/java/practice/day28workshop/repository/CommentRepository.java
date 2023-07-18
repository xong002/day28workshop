package practice.day28workshop.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    private static final String C_GAMES = "games";
    private static final String C_COMMENTS = "comments";

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.comments.aggregate([
     * { $sort: {"rating": -1}},
     * {
     * $group: {
     * _id: "$gid",
     * rating: { $first: "$rating"},
     * user: { $first: "$user"},
     * comment: { $first: "$c_text"},
     * reviewId: { $first: "$c_id"}
     * },
     * {
     * $lookup: {
     * from: 'games',
     * foreignField: 'gid',
     * localField: '_id',
     * as: 'game'
     * }
     * }])
     */
    public List<Document> getGamesSortedByRating(Boolean sort) {
        
        Direction direction;
        if (sort) {
            direction = Direction.DESC;
        } else direction = Direction.ASC;

        SortOperation sortOp = Aggregation.sort(Sort.by(direction, "rating"));

        GroupOperation groupOp = Aggregation.group("gid")
        .first("rating").as("rating")
        .first("user").as("user")
        .first("c_text").as("comment")
        .first("c_id").as("reviewId");

        LookupOperation lookupOp = Aggregation.lookup(C_GAMES, "_id", "gid", "game");

        LimitOperation limitOperation = Aggregation.limit(10);

        Aggregation pipeline = Aggregation.newAggregation(sortOp, groupOp, lookupOp, limitOperation);

        AggregationResults<Document> result = mongoTemplate.aggregate(pipeline, C_COMMENTS, Document.class);

        return result.getMappedResults();
    }
}
