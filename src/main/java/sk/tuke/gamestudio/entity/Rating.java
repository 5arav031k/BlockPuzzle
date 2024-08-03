package sk.tuke.gamestudio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Rating {
    private String login;
    private int rating;
    private Date ratedOn;
}
