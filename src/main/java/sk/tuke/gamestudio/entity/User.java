package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedNativeQuery(name = "User.addUser",
        query = "INSERT INTO users (user_id, login, password) VALUES (DEFAULT, :login, :password)")
@NamedQuery(name = "User.logIn",
        query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password")
@NamedQuery(name = "User.deleteUser",
        query = "DELETE FROM User u WHERE u.login = :login AND u.password = :password")

@Setter
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int ident;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }
}