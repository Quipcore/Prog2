package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pin {

    private static int currentClicked = 0;

    private boolean isClicked;

    private long timestamp;
    private final Circle circle;

    private final Color offColor = Color.BLUE;
    private final Color onColor = Color.RED;

    private String name;

    //---------------------------------------------------------------------

    public Pin(double radius, double xPos, double yPos) {
        circle = new Circle();
        circle.setRadius(radius);
        circle.setCenterX(xPos);
        circle.setCenterY(yPos);
        circle.setFill(offColor);
        this.name = "";
        this.timestamp = Long.MIN_VALUE;
    }

    public Pin(double xPos, double yPos) {
        this(10, xPos, yPos);
    }

    public Pin(double xPos, double yPos, String name) {
        this(xPos, yPos);
        this.name = name;
    }

    //---------------------------------------------------------------------

    public Circle getCircle() {
        return circle;
    }

    //---------------------------------------------------------------------

    public void click() {
        if (isClicked) {
            if (currentClicked > 0) {
                currentClicked--;
            }
            isClicked = false;
            circle.setFill(offColor);
            timestamp = Long.MIN_VALUE;
        } else if (!isClicked && currentClicked < 2) {
            currentClicked++;
            isClicked = true;
            circle.setFill(onColor);
            timestamp = System.nanoTime();
        }
    }

    //---------------------------------------------------------------------

    public boolean isClicked() {
        return isClicked;
    }

    //---------------------------------------------------------------------

    public long getTimestamp() {
        return timestamp;
    }

    //---------------------------------------------------------------------

    /**
     * @return name of the pin
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Used to for saving to file
     *
     * @return String in format: name;centerX;centerY
     */
    public String getLongString() {
        return name + ";" + circle.getCenterX() + ";" + circle.getCenterY();
    }

}
