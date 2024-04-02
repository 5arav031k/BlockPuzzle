package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Comment.getComments",
            query = "SELECT c from Comment c WHERE c.login = :login ORDER BY c.commentedOn DESC")
@NamedQuery(name = "Comment.reset",
            query = "DELETE FROM Comment")

public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    @Column(name = "login")
    private String login;
    @Column(name = "comment")
    private String comment;
    @Column(name = "commented_on")
    private Date commentedOn;

    public Comment(String login, String comment, Date commentedOn) {
        this.login = login;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public Comment() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }
}
