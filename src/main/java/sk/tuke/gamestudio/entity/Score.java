package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Score {
    private String login;
    private int levelsCompleted;
    private Date playedOn;

    public Score(String login, int levelsCompleted, Date playedOn) {
        this.login = login;
        this.levelsCompleted = levelsCompleted;
        this.playedOn = playedOn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }
}
