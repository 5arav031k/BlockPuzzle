package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Rating {
    private String login;
    private int rating;
    private Date ratedOn;

    public Rating(String login, int rating, Date ratedOn) {
        this.login = login;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }
}
