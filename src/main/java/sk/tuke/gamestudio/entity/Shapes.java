package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@Setter
@Getter
@NoArgsConstructor
public class Shapes implements Serializable {
    @Id
    @Column(name = "shape_id")
    private int ident;

    @Column(name = "shape_number", nullable = false)
    private int shapeNumber;

    @Column(name = "shape_color", nullable = false)
    private String shapeColor;

    @Column(name = "shape_width", nullable = false)
    private int shapeWidth;

    @Column(name = "shape_height", nullable = false)
    private int shapeHeight;

    @Column(name = "level_id", nullable = false)
    private int levelId;

    public Shapes(int shapeNumber, @NonNull String shapeColor, int shapeWidth, int shapeHeight) {
        this.shapeNumber = shapeNumber;
        this.shapeColor = shapeColor;
        this.shapeWidth = shapeWidth;
        this.shapeHeight = shapeHeight;
    }
}
