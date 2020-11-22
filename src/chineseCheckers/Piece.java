package chineseCheckers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static chineseCheckers.ChineseCheckersApp.TILE_SIZE;

//класс самой шашки
public class Piece extends StackPane {

    private final PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;

    //сама шашка (графика) и действия с ней
    //x,y - координата клетки, а не pixel
    public Piece(PieceType type, double x, double y) {
        this.type = type;

        move(x, y);
        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 6);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 6 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);

        ellipse.setFill(
                type == PieceType.RED ? Color.valueOf("#c40003") :
                        type==PieceType.BEIGE? Color.valueOf("#F5F59A"):
                                type==PieceType.BLUE? Color.valueOf("#7CB9E8"):
                                        type==PieceType.GREEN? Color.valueOf("#B0BF1A"):
                                                type==PieceType.ORANGE? Color.ORCHID:
                                                        Color.valueOf("#F19CBB"));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 6);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 6);

        getChildren().addAll(bg, ellipse);
//на нажатии
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
//на перетаскивании
        setOnMouseDragged(e ->
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY));
    }

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    //расположение по х и у
    public void move(double x, double y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
