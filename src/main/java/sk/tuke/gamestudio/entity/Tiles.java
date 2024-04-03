package sk.tuke.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.io.Serializable;

@Entity
@NamedNativeQuery(name = "Tiles.getTiles",
        query = "SELECT t.tile_pos_x, t.tile_pos_y FROM tiles t JOIN shapes_tiles st on t.tile_id = st.tile_id ORDER BY t.tile_id")

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

    public int getIdent() {
        return ident;
    }
    public void setIdent(int ident) {
        this.ident = ident;
    }
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
}
