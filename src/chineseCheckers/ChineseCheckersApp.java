package chineseCheckers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        return root;
    }

    private void showGameOverAlert(PieceType pieceType){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Игра окончена.\n"+"Победил игрок:"+pieceType);
        alert.showAndWait();
    }

    private void gameOver(TileType tileType, PieceType pieceType) {
        if (TileType.NEUTRAL == tileType
                || tileType.getNumber() == pieceType.getNumber()) return;

        Tile tileVertex = verticesStar[tileType.getNumber()];
        List<Tile> adjacentTiles = gBoard.getAdjTiles(tileVertex);

        List<Tile> endTiles = TilesOfVertexStar(adjacentTiles, 0);
        int count = 0;
        for (Tile tile : endTiles) {
            if (tile.hasPiece() && tile.getPiece().getType() == pieceType)
                count++;
        }
        if (count == 10) showGameOverAlert(pieceType);
    }

    /**
     * Возращает лист из 10 ячеек (tiles) лучика звезды. Это поле одного из игроков.
     * Рекурсивный обход лучиков звезды.
     * count следует передать ноль(т.к. рекурсия, будет увеличиваться с каждым разом).
     * adjacentTiles должен содержать при передаче две смежные клетки вершины звезды (одну из 6 в нашем случае)
     *
     * @param adjacentTiles - лист смежных клеток вершины звезды
     * @param count         - счетчик выполнения рекурсий
     * @return
     */
    private List<Tile> TilesOfVertexStar(List<Tile> adjacentTiles, int count) {
        List<Tile> finalList = new ArrayList<>(adjacentTiles);

        for (Tile tile : adjacentTiles) {
            for (Tile tile1 : gBoard.getAdjTiles(tile))
                if (!finalList.contains(tile1))
                    finalList.add(tile1);
        }

        if (count < 1) {
            return TilesOfVertexStar(finalList, ++count);
        } else
            return finalList;
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
                    tile.setTileType(TileType.NEUTRAL);
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

        for (int nVert = 0; nVert < verticesStar.length; nVert++) {
            Tile vertex = verticesStar[nVert];
            PieceType pieceType = PieceType.getPieceType(nVert);
            Piece piece = makePiece(pieceType, vertex.getX(), vertex.getY());
            vertex.setColor(pieceType);

            vertex.setPiece(piece);
            pieceGroup.getChildren().add(piece);

            Tile tile = vertex;
            List<Tile> adjacentTiles = gBoard.getAdjTiles(tile);
            addPieces(adjacentTiles, 0, pieceType);
        }
    }

    /**
     * Продолжение функции сreatePieces.
     * Рекурсивное заполнение от самой вершины, не считая ее,
     * лучиков(концов в виде треугольников) звезды шашками.
     * Располагает шашки по уровням как в пирамиде.
     * (сначала заполняет уровень, потом последующий)
     *
     * @param adjacentTiles - лист из смежных плиток самой вершины звезды
     * @param count         =0 - поумолчанию ноль
     */
    private void addPieces(List<Tile> adjacentTiles, int count, PieceType pieceType) {
        List<Tile> finalList = new ArrayList<>();
        for (Tile tile : adjacentTiles) {
            Piece piece;
            if (!tile.hasPiece()) {
                piece = makePiece(pieceType, tile.getX(), tile.getY());
                tile.setPiece(piece);
                tile.setColor(pieceType);
                pieceGroup.getChildren().add(piece);
                finalList.addAll(gBoard.getAdjTiles(tile));
            }
        }
        if (count < 2) {
            addPieces(finalList, ++count, pieceType);
        }
    }

    /**
     * Возращает вещественный номер клетки,
     * в зависимости от того на каком уровне находится клетка.
     * Если клетка находится на уровне, где идет смещение Tile на 0.5 по оси ОХ,
     * то возвращает округление до ближайшей половины(5.7->5.5,6.1->6.5),
     * иначе берется стандартное округление числа.
     *
     * @param pixel - нынешнее расположение
     * @return
     */
    private double toBoard(double pixel, boolean isHalf) {
        pixel = pixel / TILE_SIZE;
        if (isHalf) {
            return roundHalfDouble(pixel);
        } else {
            return Math.round(pixel);
        }
    }

    private MoveResult tryMove(Piece piece, double newX, double newY) {

        Tile newTile = gBoard.getTile(newX, newY);
        //переход на плитку, где есть шашка(исключаем возможность перейти на плитку с шашкой)
        if (newTile == null) {
            return new MoveResult(MoveType.NONE);
        }
        if (newTile.hasPiece()) {
            return new MoveResult(MoveType.NONE);
        }

        double x0;
        double y0 = toBoard(piece.getOldY(), false);
        if (y0 % 2 == 0) {
            x0 = toBoard(piece.getOldX(), false);
        } else {
            x0 = toBoard(piece.getOldX(), true);
        }

        List<Tile> adjTileList0 = gBoard.getAdjTiles(gBoard.getTile(x0, y0));
        if (adjTileList0.contains(newTile)) {
            //передвижение на самую близкую плитку, где нет шашки
            return new MoveResult(MoveType.NORMAL);
        } else {
            //прыжок через шашку на плитку, где нет шашки
            List<Tile> adjTileListNew = gBoard.getAdjTiles(gBoard.getTile(newX, newY));
            int countCommonCells = 0, countPieceInCommonCells = 0;

            for (Tile adjTile0 : adjTileList0) {
                for (Tile adjTileNew : adjTileListNew) {
                    if (adjTile0.equals(adjTileNew)) {
                        countCommonCells++;
                        //ниже исключается случай перехода через клетку с шашкой,
                        // где клетки разных цветов находятся рядом друг с другом
                        if (gBoard.getValue(adjTile0).size() == 5
                                && adjTileList0.size() == 4
                                && adjTileListNew.size() == 4) {
                            countCommonCells++;
                        }
                    }

                    if (adjTile0.hasPiece() && adjTile0.equals(adjTileNew))
                        countPieceInCommonCells++;

                }
            }

            if (countCommonCells == 1 && countPieceInCommonCells == 1
                //&& adjTileList0.size()!=4 && adjTileList0
            ) {
                return new MoveResult(MoveType.JIMPOVER);
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
                case JIMPOVER:
                    piece.move(newX, newY);
                    gBoard.getTile(x0, y0).setPiece(null);
                    gBoard.getTile(newX, newY).setPiece(piece);
                    gameOver(gBoard.getTile(newX, newY).getTileType(), piece.getType());
                    break;
            }
        });
        return piece;
    }

    private double roundHalfDouble(double half) {
        double intPart = Double.parseDouble(String.valueOf(half).split("\\.")[0]);
        half = intPart + 0.5;
        return half;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setFill(Color.WHITE);
        primaryStage.setTitle("ChineseCheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
