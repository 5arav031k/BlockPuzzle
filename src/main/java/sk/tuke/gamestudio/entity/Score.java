package sk.tuke.gamestudio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery(name = "Score.addCompletedLevel",
        query = "UPDATE Score s SET s.levelsCompleted = :levelsCompleted, " +
                "s.completedAt = :completedAt WHERE s.login = :login")
@NamedQuery(name = "Score.getScore",
        query = "SELECT s FROM Score s WHERE s.login = :login")
@NamedQuery(name = "Score.getTopScores",
        query = "SELECT s FROM Score s ORDER BY s.levelsCompleted DESC, s.completedAt")
@NamedQuery(name = "Score.reset",
        query = "DELETE FROM Score")

@Setter
@Getter
@NoArgsConstructor
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "levels_completed", nullable = false)
    private int levelsCompleted = 0;

    @Column(name = "completed_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date completedAt;

    public Score(@NonNull String login, int levelsCompleted, @NonNull Date completedAt) {
        this.login = login;
        this.levelsCompleted = levelsCompleted;
        this.completedAt = completedAt;
    }
}
