package chineseCheckers;

import javafx.scene.control.Label;

public class Order {
    private int number;
    private int numberOfPlayers;
    private boolean isPrevOrderJUMPOVER = false;
    private final Label pieceTypeLabel;
    private Piece piece = null;

    public Order(int number, int numberOfPlayers) {
        this.number = number;
        this.numberOfPlayers = numberOfPlayers - 1;
        this.pieceTypeLabel = new Label("Ход игрока: " + getPieceType());
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers + 1;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers-1;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setStringToLabel(String data) {
        pieceTypeLabel.setText(data);
    }

    public String getStringOfLabel() {
        return pieceTypeLabel.getText();
    }

    public Label getPieceTypeLabel() {
        return pieceTypeLabel;
    }

    public int getNumber() {
        return number;
    }

    public boolean isPrevOrderJUMPOVER() {
        return isPrevOrderJUMPOVER;
    }

    public void setPrevOrderJUMPOVER(boolean prevOrderJUMPOVER) {
        isPrevOrderJUMPOVER = prevOrderJUMPOVER;
    }

    public PieceType getPieceType() {
        return PieceType.getPieceType(number);
    }

    public void nextOrder() {
        if (number == numberOfPlayers) {
            number = 0;
        } else {
            number++;
        }
        setPrevOrderJUMPOVER(false);
        setStringToLabel("Ход игрока: " + getPieceType());
    }


}
