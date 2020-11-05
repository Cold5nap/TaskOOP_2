package chineseCheckers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;

public class ChineseCheckersApp extends Application {

    public static final int TILE_SIZE = 35;
    public static final double WIDTH = 17;
    public static final double HEIGHT = 17;
    public static final double CENTER = 8;//0-17
    //вместо матрицы нужно сделать граф
    //private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private GameField gBoard = new GameField();

    private Group tileGroup = new Group();//плитка или клетка
    private Group pieceGroup = new Group();//шашка

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        createTiles();
        createAdj();

        return root;

        /*  for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile( x, y);
                //вместо board[x][y] к графу будет добавляться вершина
                //board[x][y] = tile;
                GBoard.addTile(tile);

                tileGroup.getChildren().add(tile);
                //размещение шашек
                Piece piece = null;
                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y);
                }
                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }
                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

       */


    }

    /**
     * Add in GBoard tile with coordiante
     */
    private void createTiles() {
        final double offset = 0.5;//смещение x в каждой строчке
        double rightLim1 = CENTER;
        double leftLim1 = CENTER;
        double rightLim2 = WIDTH - 2;
        double leftLim2 = 2;
        for (int y = 0; y < HEIGHT; y++) {

            if (y < HEIGHT - 4) {
                for (double x = leftLim1; x < rightLim1 + 1; x++) {
                    Tile tile = new Tile(x, y);
                    if (!gBoard.containTile(tile)) {
                        gBoard.addTile(tile);
                        tileGroup.getChildren().add(tile);
                    }
                }
                rightLim1 += offset;
                leftLim1 -= offset;
            }
            if (y > 3) {
                for (double x = leftLim2; x < rightLim2; x++) {
                    Tile tile = new Tile(x, y);
                    if (!gBoard.containTile(tile)) {
                        gBoard.addTile(tile);
                        tileGroup.getChildren().add(tile);
                    }
                }
                rightLim2 -= offset;
                leftLim2 += offset;
            }
        }
    }

    /**
     * Создает ребра между вершинами.
     * В случае китайских шашек это ближайшие вершины по расположению
     * (максмум может быть 6 смежных вершин).
     */
    private void createAdj() {
        List<Tile> list = gBoard.getTiles();
        int count =0;
        for (Tile tile : list) {
            System.out.println("x1 "+tile.getX()+" y1 "+tile.getY());
            for (Tile tile1 : list) {
                if (Math.abs(tile.getX() - tile1.getX()) < 1.1
                        && Math.abs(tile.getY() - tile1.getY()) < 1.1
                        && !tile.equals(tile1)) {
                    //System.out.println("x2 "+tile1.getX()+" y2 "+tile1.getY());
                    //count++;

                }
            }
            Iterator iterator = gBoard.getKeyIterator();
            while (iterator.hasNext()){
                if (gBoard.defaultContain(tile)) {
                    //System.out.println(true);
                    count++;
                }
            }
        }
        System.out.println(count);
       /* Iterator iterator = list.iterator();
        Iterator iterator1 = iterator;
        while (iterator.hasNext()) {
            Tile tile = (Tile) iterator.next();
            while (iterator1.hasNext()) {
                Tile tile1 = (Tile) iterator1.next();
                if (Math.abs(tile.getX() - tile1.getX()) < 1.1
                        && Math.abs(tile.getY() - tile1.getY()) < 1.1) {
                    System.out.println("X:" + tile.getX() + " " + tile1.getX());
                    System.out.println("Y:" + tile.getY() + " " + tile1.getY());
                }
            }
        }*/
        //gBoard.addAdj()
    }
/*

    private MoveResult tryMove(Piece piece, int newX, int newY) {
        //реализовать метод наличия шашки у графа
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }

        return new MoveResult(MoveType.NONE);
    }
*/

    //размещение на доске
    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
/*
    //Действие шашки на доске
    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);
        // перемещение шакшки на доске
        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;
            //выходит ли за границы
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            //вместо логики kill сделать логику перехода через шашку
            switch (result.getType()){
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
        });

        return piece;
    }
    */

}
