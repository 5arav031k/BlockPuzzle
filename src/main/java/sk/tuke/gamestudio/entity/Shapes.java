package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedNativeQuery(name = "Shapes.getShapes",
        query = "SELECT * FROM shapes s WHERE s.level_id = :level_id ORDER BY s.shape_id")

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

    public Shapes(int ident, int shapeNumber, String shapeColor, int shapeWidth, int shapeHeight) {
        this.ident = ident;
        this.shapeNumber = shapeNumber;
        this.shapeColor = shapeColor;
        this.shapeWidth = shapeWidth;
        this.shapeHeight = shapeHeight;
    }

    public Shapes() {
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public int getShapeNumber() {
        return shapeNumber;
    }

    public void setShapeNumber(int shapeNumber) {
        this.shapeNumber = shapeNumber;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public int getShapeWidth() {
        return shapeWidth;
    }

    public void setShapeWidth(int shapeWidth) {
        this.shapeWidth = shapeWidth;
    }

    public int getShapeHeight() {
        return shapeHeight;
    }

    public void setShapeHeight(int shapeHeight) {
        this.shapeHeight = shapeHeight;
    }
}
