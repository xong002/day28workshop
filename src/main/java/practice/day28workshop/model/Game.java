package practice.day28workshop.model;

import java.util.Date;
import java.util.List;

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

}
