package chineseCheckers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChineseCheckersApp extends Application {

    public static final int TILE_SIZE = 35;
    public static final double WIDTH = 17;
    public static final double HEIGHT = 17;
    public static final double CENTER = 8;//0-17

    private GameField gBoard = new GameField();

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Tile[] verticesStar = new Tile[6];//крайние вершины звезды


    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        createTiles();
        createAdj();
        createPieces();
        System.out.println("TILE"+gBoard.getTile(10.5,13));
        return root;
    }

    /**
     * Располагает плитки в виде шестиконечной звезды.
     * (Запоминает плитки лежащие в углах концов звезды)
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
                    if (y == 0) {
                        verticesStar[0] = tile;
                    }
                    if (y == HEIGHT - 5 && x == leftLim1) {
                        verticesStar[1] = tile;
                    }
                    if (y == HEIGHT - 5) {
                        verticesStar[2] = tile;
                    }
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
                    if (y == HEIGHT - 1) {
                        verticesStar[3] = tile;
                    }
                    if (y == 4 && x == leftLim2) {
                        verticesStar[4] = tile;
                    }
                    if (y == 4) {
                        verticesStar[5] = tile;
                    }
                    if (!gBoard.containTile(tile)) {
                        gBoard.addTile(tile);
                        tileGroup.getChildren().add(tile);
                    }
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
        for (Tile tile : list) {
            for (Tile tile1 : list) {
                if (Math.abs(tile.getX() - tile1.getX()) < 1.1
                        && Math.abs(tile.getY() - tile1.getY()) < 1.1
                        && !tile.equals(tile1)) {
                    gBoard.addAdj(tile, tile1);
                }
            }
        }
    }

    /**
     * Располагает по лучям звезеды шашки,
     * основываясь на смежности вершин данного графа.
     * (Можно расположить было в зависимости от координат плитки,
     * но в данном примере шашки распологаются в зависимости от наличия смежных плиток)
     */
    private void createPieces() {
        for (Tile vertex : verticesStar) {
            Piece piece = makePiece(PieceType.RED, vertex.getX(), vertex.getY());
            vertex.setPiece(piece);
            pieceGroup.getChildren().add(piece);
            Tile tile = vertex;
            List<Tile> adjacentTiles = gBoard.getAdjVertices(tile);
            addPieces(adjacentTiles, 0);
        }
    }

    /**
     * Рекурсивное заполнение от самой вершины, не считая ее,
     * лучиков(концов в виде треугольников) звезды шашками.
     * Располагает шашки по уровням как в пирамиде.
     * (сначала заполняет уровень, потом последующий)
     *
     * @param adjacentTiles - лист из смежных плиток самой вершины звезды
     * @param count         =0 - поумолчанию ноль
     */
    private void addPieces(List<Tile> adjacentTiles, int count) {
        List<Tile> finalList = new ArrayList<>();
        for (Tile tile : adjacentTiles) {
            Piece piece;
            if (!tile.hasPiece()) {
                piece = makePiece(PieceType.RED, tile.getX(), tile.getY());
                tile.setPiece(piece);
                pieceGroup.getChildren().add(piece);
                finalList.addAll(gBoard.getAdjVertices(tile));
            }
        }
        if (count < 2) {
            addPieces(finalList, ++count);
        }
    }

    /**
     * размещение на доске
     *
     * @param pixel - нынешнее расположение
     * @return
     */
    private double toBoard(double pixel, boolean isHalf) {
        pixel = (pixel + (double) TILE_SIZE / 2) / TILE_SIZE;
        if (isHalf) {
            return roundHalfDouble(pixel);
        } else {
            return Math.round(pixel);
        }
    }

    private MoveResult tryMove(Piece piece, double newX, double newY) {
        //переход на плитку, где есть шашка(исключаем возможность перейти на плитку с шашкой)

        if (gBoard.getTile(newX, newY).hasPiece()) {
            return new MoveResult(MoveType.NONE);
        }
        double x0;
        double y0 = toBoard(piece.getOldY(), false);
        if (y0 % 2 == 0) {
            x0 = toBoard(piece.getOldX(), false);
        } else {
            x0 = toBoard(piece.getOldX(), true);
        }


        Tile newTile = gBoard.getTile(newX, newY);
        List<Tile> adjTileList = gBoard.getAdjVertices(gBoard.getTile(x0, y0));

        //передвижение на ближайшую плитку, где нет шашки
        if (adjTileList.contains(newTile)) {
            return new MoveResult(MoveType.NORMAL);
        } else {

            //прыжок через шашку на плитку, где нет шашки
            for (Tile adjTile : adjTileList) {
                if (gBoard.getAdjVertices(adjTile).contains(newTile)) {
                    double x1 = x0 + (newX - x0) / 2;
                    double y1 = y0 + (newY - y0) / 2;
                    System.out.println(" x1= "+x1+" y1= "+ y1);
                    if (gBoard.getTile(x1, y1).hasPiece()) {
                        return new MoveResult(MoveType.JIMPOVER, gBoard.getTile(x1, y1).getPiece());
                    }
                    break;
                }
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    //Действие шашки на доске
    private Piece makePiece(PieceType type, double x, double y) {
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {

            double newX;
            double newY = toBoard(piece.getLayoutY(), false);
            if (newY % 2 == 0) {
                newX = toBoard(piece.getLayoutX(), false);
            } else {
                newX = toBoard(piece.getLayoutX(), true);
            }
            System.out.println(piece.getLayoutX() + " X= " + newX +
                    " LAYOUT " + piece.getLayoutY() + " Y= " + newY);

            MoveResult result;
            //выходит ли за границы
            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            double x0;
            double y0 = toBoard(piece.getOldY(), false);
            if (y0 % 2 == 0) {
                x0 = toBoard(piece.getOldX(), false);
            } else {
                x0 = toBoard(piece.getOldX(), true);
            }
            switch (result.getType()){
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    gBoard.getTile(x0, y0).setPiece(null);
                    gBoard.getTile(newX, newY).setPiece(piece);
                    break;
                case JIMPOVER:
                    piece.move(newX, newY);
                    gBoard.getTile(x0, y0).setPiece(null);
                    gBoard.getTile(newX, newY).setPiece(piece);
                    break;
            }
        });
        return piece;
    }

    private double roundHalfDouble(double half) {
        double intPart = Double.parseDouble(String.valueOf(half).split("\\.")[0]);
        //double fractPart = Double.parseDouble("0." + String.valueOf(half).split("\\.")[1]);
        half = intPart + 0.5;
        return half;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
