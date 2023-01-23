package model;

import java.awt.Color;


/**
 * Represents a rectangle that extends AShape.
 */
public class Rectangle extends AShape {

  /**
   * Creates a rectangle using the same parameters as the AShape abstract class.
   *
   * @param name  the name of the shape.
   * @param pos   the position of the shape.
   * @param color the color of the shape.
   * @param size  the size of the shape.
   */
  public Rectangle(String name, Coordinate pos, Color color, Size size,
                   int startOfLife, int endOfLife) {
    super(name, size, pos, color, startOfLife, endOfLife);

  }


  /**
   * Overrides the toString method in order to return a textual description of the shape in its
   * state.
   *
   * @return textual description of rectangle.
   */
  @Override
  public String toString() {
    return "Rectangle " + this.name + " x:" + this.position.getX() + " y:" + this.position.getY()
            + " w:" +
            this.size.getWidth() + " h:" + this.size.getHeight() + " r:" +
            this.color.getRed() + " g:" + this.color.getGreen() + " b:" +
            this.color.getBlue();
  }

  @Override
  public IShape copyShape() {
    IShape rect = new Rectangle(name, new Coordinate(position.getX(), position.getY()),
            color, size, startOfLife, endOfLife);
    rect.setTransformationsOverInterval(this.getTransformationsOverInterval());
    return rect;
  }



  @Override
  public String svgTagForShape(double tempo) {
    StringBuilder out = new StringBuilder();
    out.append("<rect id=\"" + this.name + "\" x=\"" + position.getX() + "\" y= \"" +
            position.getY() + "\" width=\"" + size.getWidth() + "\" height=\"" + size.getHeight() +
            "\" fill=" + shapeColorToString() + " visibility=\"hidden\" >\n");
    out.append("<set attributeName=\"visibility\" attributeType='XML' begin=\"" +
                    startOfLife / tempo + "\" dur=\"" + (endOfLife - startOfLife) / tempo
            + "\" to=\"visible\" />\n");
    out = buildTransformationTags(out, tempo);
    out.append("<set attributeName=\"visibility\" attributeType='XML' begin=\"" +
            endOfLife / tempo + "\" to=\"hidden\" />\n");
    //left out dur equals to keep default value as indefinite.
    out.append("</rect>\n");
    return out.toString();
  }
}


