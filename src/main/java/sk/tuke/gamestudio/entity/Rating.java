package sk.tuke.gamestudio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedNativeQuery(name = "Rating.setRating",
        query = "INSERT INTO Rating (login, rating, rated_on) VALUES (:login, :rating, :ratedOn) " +
                "ON CONFLICT (login) DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on")
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) AS average_rating FROM Rating r")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT r.rating from Rating r WHERE r.login = :login")
@NamedQuery(name = "Rating.reset",
        query = "DELETE FROM Rating")

@Setter
@Getter
@NoArgsConstructor
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "rated_on", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date ratedOn;

    public Rating(@NonNull String login, int rating, @NonNull Date ratedOn) {
        this.login = login;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }
}
