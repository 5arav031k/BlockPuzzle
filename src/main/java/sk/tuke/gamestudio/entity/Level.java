package sk.tuke.gamestudio.entity;

import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.game.block_puzzle.core.Shape;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "levels")
@NamedNativeQuery(name = "Level.getShapeCount",
        query = "SELECT l.shapes_count FROM levels l WHERE l.level_id = :level_id")
@NamedNativeQuery(name = "Level.getShapes",
        query = "SELECT * FROM shapes s WHERE s.level_id = :level_id ORDER BY s.shape_id")
@NamedNativeQuery(name = "Level.getTileId",
        query = "SELECT st.tile_id FROM shapes_tiles st WHERE st.shape_id = :shape_id")
@NamedNativeQuery(name = "Level.getTile",
        query = "SELECT t.tile_pos_x, t.tile_pos_y FROM tiles t WHERE t.tile_id = :tile_id ORDER BY t.tile_id")

public class Level implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    @Transient
    private List<Shape> shapes;
    @Transient
    private Field field;
    @Column(name = "shapes_count")
    private int shapeCount;

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
}
