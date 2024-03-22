package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Comment{
    private String login;
    private String comment;
    private Date commentedOn;

    public Comment(String login, String comment, Date commentedOn) {
        this.login = login;
        this.comment = comment;
        this.commentedOn = commentedOn;
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
