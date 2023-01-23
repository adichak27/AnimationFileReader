package model;

import java.awt.Color;




/**
 * Represents an Oval shape, extends AShape.
 */
public class Oval extends AShape {

  /**
   * Uses same constructor as the AShape class, there are no new fields for an Oval.
   * @param name the name of the shape
   * @param pos the starting position of the shape
   * @param color the starting color of the shape
   * @param size the starting size of the shape.
   */
  public Oval(String name, Coordinate pos, Color color, Size size,
              int startOfLife, int endOfLife) {
    super(name, size, pos, color, startOfLife, endOfLife);

  }

  /**
   * Overrides the toString method on a shape in order to output a string that describes all of the
   * information about the state of an oval.
   * @return
   */
  @Override
  public String toString() {
    return "Oval " + this.name + " x:" + this.position.getX() + " y:" + this.position.getY()
            + " w:" +
            this.size.getWidth() + " h:" + this.size.getHeight() + " r:" +
            this.color.getRed() + " g:" + this.color.getGreen() + " b:" +
            this.color.getBlue();
  }

  @Override
  public IShape copyShape() {
    IShape oval = new Oval(name, new Coordinate(position.getX(), position.getY()),
            color, size, startOfLife, endOfLife);
    oval.setTransformationsOverInterval(this.getTransformationsOverInterval());
    return oval;
  }


  @Override
  public String svgTagForShape(double tempo) {
    StringBuilder out = new StringBuilder();
    out.append("<ellipse id=\"" + this.name + "\" cx=\"" + position.getX() + "\" cy= \"" +
            position.getY() + "\" rx=\"" + size.getWidth() + "\" ry=\"" + size.getHeight() +
            "\" fill=" + shapeColorToString() + " visibility=\"hidden\" >\n");
    out.append("<set attributeName=\"visibility\" attributeType='XML' begin=\"" +
            startOfLife / tempo + "\" dur=\"" + (endOfLife - startOfLife) / tempo
            + "\" to=\"visible\" />\n");
    out = buildTransformationTags(out, tempo);
    out.append("<set attributeName=\"visibility\" attributeType='XML' begin=\"" +
            endOfLife / tempo + "\" to=\"hidden\" />\n");
    out.append("</ellipse>\n");
    return out.toString();
  }
}
