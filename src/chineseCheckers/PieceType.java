package chineseCheckers;
//типы шашек
//в китайских шашках 6 игроков  соответственно 6 цветов
public enum PieceType {
    RED(1), WHITE(-1);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
