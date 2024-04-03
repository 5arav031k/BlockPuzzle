package sk.tuke.gamestudio.entity;

import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "levels")
@NamedNativeQuery(name = "Level.getLevel",
        query = "SELECT * FROM levels l WHERE l.level_id = :level_id")

public class Level implements Serializable {
    @Id
    @Column(name = "level_id")
    private int ident;
    @Column(name = "shapes_count")
    private int shapeCount;
    @Transient
    private List<Shape> shapes;
    @Transient
    private Field field;

    public Level(Field field) {
        this.field = field;
        shapes = new ArrayList<>();
    }

    public Level() {
    }

    public int getIdent() {
        return ident;
    }
    public void setIdent(int ident) {
        this.ident = ident;
    }
    public int getShapeCount() {
        return shapeCount;
    }
    public void setShapesCount(int shapeCount) {
        this.shapeCount = shapeCount;
    }
    public List<Shape> getShapes() {
        return shapes;
    }
    public Field getField() {
        return field;
    }
    public void setField(Field field) {
        this.field = field;
    }
}
