package chineseCheckers;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//клетка на игровой доске
public class Tile extends Circle {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    //x,y- счетчики плиток по оси OX и OY
    private double x;
    private double y;

    public Tile( double x, double y) {
        this.x = x;
        this.y = y;
        setRadius(ChineseCheckersApp.TILE_SIZE/2);
        relocate(x * ChineseCheckersApp.TILE_SIZE, y * ChineseCheckersApp.TILE_SIZE);
        setFill(Color.DARKGREY);
    }


    public boolean equals(Tile tile) {
        if (Math.abs(x - tile.getX())<0.0001 && Math.abs(y - tile.getY())<0.0001) {
            return true;
        }else {
            return false;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void printСoordinate(){
        System.out.print(" X= " + x + " Y= " + y);
    }
}
