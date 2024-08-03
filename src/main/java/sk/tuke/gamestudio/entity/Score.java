package sk.tuke.gamestudio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Score {
    private String login;
    private int levelsCompleted;
    private Date playedOn;
}
