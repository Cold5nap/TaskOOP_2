package chineseCheckers;

import javafx.scene.control.Label;

public class Order {
    private int number;
    private int numberOfPlayers;
    private boolean isPrevOrderJUMPOVER = false;
    private Label label;
    private Piece piece = null;

    public Order(int number, int numberOfPlayers) {
        this.number = number;
        this.numberOfPlayers = numberOfPlayers - 1;
        this.label = new Label("Ход игрока: " + getPieceType());
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
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
        label.setText(data);
    }

    public String getStringOfLabel() {
        return label.getText();
    }

    public Label getLabel() {
        return label;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
