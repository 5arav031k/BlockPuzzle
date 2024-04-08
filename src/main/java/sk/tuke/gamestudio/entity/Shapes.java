package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@Setter
@Getter
public class Shapes implements Serializable {
    @Id
    @Column(name = "shape_id")
    private int ident;

    @Column(name = "shape_number")
    private int shapeNumber;

    @Column(name = "shape_color")
    private String shapeColor;

    @Column(name = "shape_width")
    private int shapeWidth;

    @Column(name = "shape_height")
    private int shapeHeight;

    @Column(name = "level_id")
    private int levelId;

    public Shapes(int ident, int shapeNumber, String shapeColor, int shapeWidth, int shapeHeight) {
        this.ident = ident;
        this.shapeNumber = shapeNumber;
        this.shapeColor = shapeColor;
        this.shapeWidth = shapeWidth;
        this.shapeHeight = shapeHeight;
    }

    public Shapes() {
    }
}
