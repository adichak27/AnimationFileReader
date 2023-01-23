package model;

/**
 * Class represents the Move Transformation. This class also extends the AbstractTransformation
 * class and inherits all of the fields from that class such as TrnasformationType and shape. The
 * class also has a toString method which is used to visably document this action to the viewer.
 */
public class Move extends AbstractTransformation {
  private final Coordinate start;
  private final Coordinate end;

  /**
   * Constructor for the move Transformation, takes in extra parameters for the starting
   * and ending coordinates of the move.
   * @param shape the shape that this transformation is happening on.
   * @param interval the interval that this transformation is happening over.
   * @param start the start of position.
   * @param end the end position.
   */
  public Move(IShape shape, Interval interval, Coordinate start,
              Coordinate end) {
    super(shape, TransformationType.Move, interval);
    if ((start == null) || (end == null)) {
      throw new IllegalArgumentException("Start and End cannot be null");
    } else {
      this.start = start;
      this.end = end;
    }

  }

  /**
   * gets the starting position of this move transformation.
   * @return starting pos field
   */
  public Coordinate getStart() {
    return this.start;
  }

  /**
   * gets the ending position of this move Transformation.
   * @return ending pos field.
   */
  public Coordinate getEnd() {
    return this.end;
  }

  @Override
  public IShape execute(IShape shape, int tick) {
    if (tick > interval.getEnding()) {
      tick = interval.getEnding();
    }
    IShape copy = shape.copyShape();
    copy.moveShape(new Coordinate((int) Math.round(interpolate(start.getX(), end.getX(), tick)),
            (int) Math.round(interpolate(start.getY(), end.getY(), tick))));
    return copy;
  }

  /**
   * Overrides the toString in order to create a string representation of the move.
   * @return string description of move
   */
  @Override
  public String toString() {
    //removed tick portion of string in assignment 5, to be added to methods in model and view.
    return this.shape.getNameOfShape() + " Moves from position " + this.start.toString() +
            " to " + this.end.toString();
  }

  @Override
  public String svgTag(double tempo) {
    //might have to fix if one of the coordiante does not move
    StringBuilder stringB = new StringBuilder();
    String attributeName = "x";
    int from = start.getX();
    int to = end.getX();
    for (int i = 1; i <= 2; i = i + 1) {
      stringB.append( "<animate attributeType=\"xml\" " + interval.intervalTag(tempo)
               + "attributeName=\"" + attributeName + "\" from=\"" +
              from + "\" to=\"" + to + "\" fill=\"freeze\" />\n");
      attributeName = "y";
      from = start.getY();
      to = end.getY();
    }
    return stringB.toString();
  }
































}
