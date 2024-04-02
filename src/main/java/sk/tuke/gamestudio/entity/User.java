package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedNativeQuery(name = "User.addUser",
        query = "INSERT INTO users (user_id, login, password) VALUES (DEFAULT, :login, :password)")
@NamedQuery(name = "User.logIn",
        query = "SELECT u.login FROM User u WHERE u.login = :login AND u.password = :password")
@NamedQuery(name = "User.deleteUser",
        query = "DELETE FROM User u WHERE u.login = :login AND u.password = :password")

public class User implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}