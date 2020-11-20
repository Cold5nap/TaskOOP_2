package chineseCheckers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//клетка на игровой доске
public class Tile extends Circle {

    private Piece piece;
    //x,y- счетчики плиток по оси OX и OY
    private double x;
    private double y;
    private TileType type;

    public Tile(double x, double y) {
        this.x = x;
        this.y = y;
        setRadius(ChineseCheckersApp.TILE_SIZE / 2);
        relocate(x * ChineseCheckersApp.TILE_SIZE, y * ChineseCheckersApp.TILE_SIZE);
        setFill(Color.DARKGREY);
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
            case RED -> setFill(Color.valueOf("#c40003"));
            case BLUE -> setFill(Color.valueOf("#7CB9E8"));
            case BEIGE -> setFill(Color.valueOf("#F5F59A"));
            case GREEN -> setFill(Color.valueOf("#B0BF1A"));
            case ORANGE -> setFill(Color.valueOf("#C46210"));
            case PINK -> setFill(Color.valueOf("#F19CBB"));

        }
    }

    public boolean equals(Tile tile) {
        if (Math.abs(x - tile.getX()) < 0.0001 && Math.abs(y - tile.getY()) < 0.0001) {
            return true;
        } else {
            return false;
        }
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
            case RED -> this.type = TileType.RED;
            case BLUE -> this.type = TileType.BLUE;
            case BEIGE -> this.type = TileType.BEIGE;
            case GREEN -> this.type = TileType.GREEN;
            case ORANGE -> this.type = TileType.ORANGE;
            case PINK -> this.type = TileType.PINK;

        }
    }

    public void setTileType(TileType type) {
        this.type = type;
    }

    public void printСoordinate() {
        System.out.print(" X= " + x + " Y= " + y);
    }
}
