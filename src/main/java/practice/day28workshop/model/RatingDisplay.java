package practice.day28workshop.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDisplay {
    private String rating;
    private List<GameWithRating> games;
    private Date timestamp;
}
