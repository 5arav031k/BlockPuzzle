package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedNativeQuery(name = "Rating.setRating",
        query = "INSERT INTO Rating (login, rating, rated_on) VALUES (:login, :rating, :ratedOn) " +
                "ON CONFLICT (login) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on")
@NamedNativeQuery(name = "Rating.getAverageRating",
        query = "SELECT ROUND(AVG(r.rating)) AS average_rating FROM Rating r")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT r.rating from Rating r WHERE r.login = :login")
@NamedQuery(name = "Rating.reset",
        query = "DELETE FROM Rating")

public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    @Column(name = "login")
    private String login;
    @Column(name = "rating")
    private int rating;
    @Column(name = "rated_on")
    private Date ratedOn;

    public Rating(String login, int rating, Date ratedOn) {
        this.login = login;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public Rating() {
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public int getIdent() {
        return ident;
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
