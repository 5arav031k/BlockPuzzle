package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery(name = "Score.addCompletedLevel",
        query = "UPDATE Score s SET s.levelsCompleted = :levelsCompleted, " +
                "s.completedAt = :completedAt WHERE s.login = :login")
@NamedNativeQuery(name = "Score.addScore",
        query = "INSERT INTO Score (login, levels_completed, completed_at) VALUES (:login, DEFAULT, :completedAt)")
@NamedQuery(name = "Score.getScore",
        query = "SELECT s.levelsCompleted, s.completedAt FROM Score s WHERE s.login = :login")
@NamedNativeQuery(name = "Score.getTopScores",
        query = "SELECT * FROM Score s ORDER BY s.levels_completed DESC, s.completed_at LIMIT 5")
@NamedQuery(name = "Score.reset",
        query = "DELETE FROM Score")

public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    @Column(name = "login")
    private String login;
    @Column(name = "levels_completed")
    private int levelsCompleted;
    @Column(name = "completed_at")
    private Date completedAt;

    public Score(String login, int levelsCompleted, Date completedAt) {
        this.login = login;
        this.levelsCompleted = levelsCompleted;
        this.completedAt = completedAt;
    }

    public Score() {
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
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

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
}
