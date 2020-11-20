package chineseCheckers;

public enum TileType {
    RED(0),
    ORANGE(1),
    GREEN(2),
    BLUE(3),
    PINK(4),
    BEIGE(5),
    NEUTRAL(6);

    private int edgeNumber ;

    TileType(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }

    public int getNumber() {
        return edgeNumber;
    }

    public static TileType getPieceType(int edgeNumber){
        switch (edgeNumber){
            case 0 -> {
                return RED;
            }
            case 1 -> {
                return ORANGE;
            }case 2 -> {
                return GREEN;
            }case 3 -> {
                return BLUE;
            }case 4 -> {
                return PINK;
            }case 5 -> {
                return BEIGE;
            }
        }
        return NEUTRAL;
    }
}
