import java.awt.Color;

public class Apple extends Square {

    public final Color appleColor = Color.RED;
    public int appleCount;
    private final int appleCountIncrememntValue = 1;

    public Apple (int segmentX, int segmentY) {
        this.currentX = segmentX;
        this.currentY = segmentY;
        this.appleCount = 0;
    }
/*
    //------------------------------------------------------------------------------------------------------------------
    public void placeApple (GameBoard gameBoard) {



    }*/

    //------------------------------------------------------------------------------------------------------------------
    public int getAppleCount() {
        return this.appleCount;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void incrementAppleCount() {
        this.appleCount = this.appleCount + appleCountIncrememntValue;
    }

    //------------------------------------------------------------------------------------------------------------------
    public Color getAppleColor() {
        return this.appleColor;
    }
}
