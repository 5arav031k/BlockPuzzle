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
@NamedQuery(name = "Comment.getComments",
            query = "SELECT c from Comment c ORDER BY c.commentedOn DESC")
@NamedQuery(name = "Comment.reset",
            query = "DELETE FROM Comment")

@Table(uniqueConstraints = @UniqueConstraint(name = "UniqueLoginAndCommentedOn", columnNames = {"login", "commented_on"}))

@Getter
@Setter
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ident;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "commented_on", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date commentedOn;

    public Comment(@NonNull String login, @NonNull String comment, @NonNull Date commentedOn) {
        this.login = login;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }
}
