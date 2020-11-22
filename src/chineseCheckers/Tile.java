package chineseCheckers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//клетка на игровой доске
public class Tile extends Circle {

    private Piece piece;
    //x,y- счетчики плиток по оси OX и OY
    private final double x;
    private final double y;
    private TileType type;

    public Tile(double x, double y) {
        this.x = x;
        this.y = y;
        setRadius(ChineseCheckersApp.TILE_SIZE / 2.0);
        relocate(x * ChineseCheckersApp.TILE_SIZE, y * ChineseCheckersApp.TILE_SIZE);
        setFill(Color.WHITESMOKE);
        setStroke(Color.DARKGREY);
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setColor(PieceType type) {
        setTileType(type);
        switch (type){
            case RED :{ setFill(Color.valueOf("#c40003"));break;}
            case BLUE : {setFill(Color.valueOf("#7CB9E8"));break;}
            case BEIGE :{ setFill(Color.valueOf("#F5F59A"));break;}
            case GREEN : {setFill(Color.valueOf("#B0BF1A"));break;}
            case ORANGE : {setFill(Color.ORCHID);break;}
            case PINK : {setFill(Color.valueOf("#F19CBB"));break;}

        }
    }

    public boolean equals(Tile tile) {
        return Math.abs(x - tile.getX()) < 0.0001 && Math.abs(y - tile.getY()) < 0.0001;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public TileType getTileType() {
        return type;
    }

    public void setTileType(PieceType pt) {
        switch (pt){
            case RED : {this.type = TileType.RED;break;}
            case BLUE : {this.type = TileType.BLUE;break;}
            case BEIGE : {this.type = TileType.BEIGE;break;}
            case GREEN : {this.type = TileType.GREEN;break;}
            case ORANGE :{ this.type = TileType.ORANGE;break;}
            case PINK : {this.type = TileType.PINK;break;}

        }
    }

    public void setTileType(TileType type) {
        this.type = type;
    }
}
