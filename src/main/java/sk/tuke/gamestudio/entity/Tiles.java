package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@Setter
@Getter
public class Tiles implements Serializable {
    @Id
    @Column(name = "tile_id")
    private int ident;

    @Column(name = "tile_pos_x")
    private int posX;

    @Column(name = "tile_pos_y")
    private int posY;

    public Tiles(int ident, int posX, int posY) {
        this.ident = ident;
        this.posX = posX;
        this.posY = posY;
    }

    public Tiles() {
    }
}
