package chineseCheckers;

public enum TileType {
    RED(0),
    ORANGE(1),
    GREEN(2),
    BLUE(3),
    PINK(4),
    BEIGE(5),
    NEUTRAL(6);

    private final int edgeNumber ;

    TileType(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }

    public int getNumber() {
        return edgeNumber;
    }

}
