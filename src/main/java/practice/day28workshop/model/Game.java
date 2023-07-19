package practice.day28workshop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private int id;
    private String name;
    private int year;
    private int rank;
    private int usersRated;
    private String url;
    private String thumbnail;
    private List<String> reviews;
    private Date timestamp;

    

    public Game(String name, String url) {
        this.name = name;
        this.url = url;
    }



    public void toGame(Document doc) {
        this.id = doc.getInteger("gid");
        this.name = doc.getString("name");
        this.year = doc.getInteger("year");
        this.rank = doc.getInteger("ranking");
        this.usersRated = doc.getInteger("users_rated");
        this.url = doc.getString("url");
        this.thumbnail = doc.getString("image");
        this.reviews = new ArrayList<>();
        this.timestamp = new Date();
    }

}
