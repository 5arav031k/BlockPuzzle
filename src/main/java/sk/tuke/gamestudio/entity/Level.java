package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "levels")
@NamedQuery(name = "Level.getLevel",
        query = "SELECT l FROM Level l WHERE l.ident = :level_id")

@Setter
@Getter
public class Level implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private int ident;

    @Column(name = "shapes_count")
    private int shapeCount;

    @Transient
    private List<Shape> shapes;

    public Level() {
        shapes = new ArrayList<>();
    }
}
