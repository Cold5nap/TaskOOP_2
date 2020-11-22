package chineseCheckers;

//типы шашек
//в китайских шашках 6 игроков  соответственно 6 цветов
public enum PieceType {
    RED(0),
    ORANGE(1),
    GREEN(2),
    BLUE(3),
    PINK(4),
    BEIGE(5);

    private final int edgeNumber;

    PieceType(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }

    public static PieceType getPieceType(int edgeNumber) {
        switch (edgeNumber){
            case 0: return RED;
            case 1: return ORANGE;
            case 2: return GREEN;
            case 3: return BLUE;
            case 4: return PINK;
            case 5: return BEIGE;
        }
        return null;
    }


    public int getNumber() {
        return edgeNumber;
    }
}
