package model;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Transformation that changes the color of the shape.
 */
public class ChangeColor extends AbstractTransformation {

  private final int redStart;
  private final int blueStart;
  private final int greenStart;
  private final int redEnd;
  private final int blueEnd;
  private final int greenEnd;

  /**
   * takes in all parameters that the Abstract transformation does, as well as parameters for
   * the start and ending colors as RGB values.
   * @param shape the shape the transformation belongs to.
   * @param interval the interval the transformation occurs over
   * @param redStart the starting r value
   * @param greenStart the starting g value
   * @param blueStart the starting b value
   * @param redEnd the ending r value
   * @param greenEnd the ending g value
   * @param blueEnd the ending b value
   */
  public ChangeColor(IShape shape, Interval interval, int redStart, int greenStart, int blueStart,
                     int redEnd, int greenEnd, int blueEnd) {
    super(shape, TransformationType.ChangeColor, interval);
    if (! isValidColor(redEnd, blueEnd, greenEnd)) {
      throw new IllegalArgumentException("not a valid color");
    }
    this.redStart = redStart;
    this.greenStart = greenStart;
    this.blueStart = blueStart;
    this.redEnd = redEnd;
    this.blueEnd = blueEnd;
    this.greenEnd = greenEnd;
  }


  @Override
 public IShape execute(IShape shape, int tick) {
    if (tick > interval.getEnding()) {
      tick = interval.getEnding();
    }
    IShape copy = shape.copyShape();
    int newRed = (int) Math.round(interpolate(redStart, redEnd, tick));
    int newGreen = (int) Math.round(interpolate(greenStart, greenEnd, tick));
    int newBlue = (int) Math.round(interpolate(blueStart, blueEnd, tick));
    copy.changeColor(new Color(newRed, newGreen, newBlue));
    return copy;

  }


  //checks that the color is valid
  private boolean isValidColor(int r, int g, int b) {
    return ((r >= 0 && r <= 255) &&
            (g >= 0 && g <= 255) &&
            (b >= 0 && b <= 255));
  }

  public ArrayList<Integer> getBeginningColor() {
    return new ArrayList<>(Arrays.asList(redStart, greenStart, blueStart));
  }

  public ArrayList<Integer> getEndingColor() {
    return new ArrayList<>(Arrays.asList(redEnd, greenEnd, blueEnd));
  }


  /**
   * returns the string representation of this transformation.
   * @return the string representation of this transformation.
   */
  @Override
  public String toString() {
    //removed tick portion to instead be added in other methods.
    return this.shape.getNameOfShape() + " Changes color from RGB values " + "(" +
            this.redStart + ", " + this.greenStart + ", " + this.blueStart + ")" +
            " to " + "(" +
            this.redEnd + ", " + this.greenEnd + ", " + this.blueEnd + ")";
  }

  @Override
  public String svgTag(double tempo) {
    String begColor = colorToString(redStart, greenStart, blueStart);
    String endColor = colorToString(redEnd, greenEnd, blueEnd);
    return  "<animate attributeType=\"xml\" " + interval.intervalTag(tempo)
              + "attributeName=\"fill\" from=" +
              begColor + " to=" + endColor + " fill=\"freeze\" />\n";

  }


  static private String colorToString(int r, int g, int b) {
    return "\"rgb(" + r + "," + g + "," + b + ")\"";

  }
}






































