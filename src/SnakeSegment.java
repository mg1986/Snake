import java.awt.Color;

public class SnakeSegment extends Square {

    private int previousX;
    private int previousY;
    private String segmentDirection;

    //------------------------------------------------------------------------------------------------------------------
    public SnakeSegment (int segmentX, int segmentY, Color color) {
       this.currentX = segmentX;
       this.currentY = segmentY;
       this.segmentColor = color;
       this.segmentDirection = "UP";
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getPreviousX() { return this.previousX; }

    //------------------------------------------------------------------------------------------------------------------
    public void setPreviousX(int x) { this.previousX = x; }

    //------------------------------------------------------------------------------------------------------------------
    public int getPreviousY() { return this.previousY; }

    //------------------------------------------------------------------------------------------------------------------
    public void setPreviousY(int y) { this.previousY = y; }

    //------------------------------------------------------------------------------------------------------------------
    public String getDirection() { return this.segmentDirection; }

    //------------------------------------------------------------------------------------------------------------------
    public void setDirection(String direction) { this.segmentDirection = direction; }

}
