package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedNativeQuery(name = "User.addUser",
        query = "INSERT INTO users (user_id, login, password) VALUES (DEFAULT, :login, :password)")
@NamedQuery(name = "User.getEncodedPassword",
        query = "SELECT u.password FROM User u WHERE u.login = :login")
@NamedQuery(name = "User.deleteUser",
        query = "DELETE FROM User u WHERE u.login = :login AND u.password = :password")

@Setter
@Getter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int ident;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public User(@NonNull String login, @NonNull String password) {
        this.login = login;
        this.password = password;
    }
}