package practice.day28workshop.model;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameWithRating {
    private int id;
    private String name;
    private int rating;
    private String user;
    private String comment;
    private String reviewId;

    public void toGameWithRating(Document doc){
        this.id = doc.getInteger("_id");
        this.name = doc.getList("game", Document.class).get(0).getString("name");
        this.rating = doc.getInteger("rating");
        this.user = doc.getString("user");
        this.comment = doc.getString("comment");
        this.reviewId = doc.getString("reviewId");
    }
}
