package model;


/**
 * transformation that changes the size of its shape.
 */
public class ChangeSize extends AbstractTransformation {
  private final Size begSize;
  private final Size endSize;

  /**
   * Constructs a changeSize transformation, if the beg or end sizes are null, it does not allow.
   * @param shape the shape that this transformation is happening on.
   * @param interval the interval that this transformation is happening over.
   * @param begSize the beginning size of this transformation
   * @param endSize the ending size of this transformation.
   */
  public ChangeSize(IShape shape, Interval interval, Size begSize, Size endSize) {
    super(shape, TransformationType.ChangeSize, interval);
    if ((begSize == null) || (endSize == null)) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    if ((shape instanceof Plus) && ( endSize.getHeight() != endSize.getWidth())) {
      throw new
              IllegalArgumentException("Size must be maintained as same width and height for plus");
    }
    this.begSize = begSize;
    this.endSize = endSize;
  }


  @Override
  public IShape execute(IShape shape, int tick) {
    if (tick > interval.getEnding()) {
      tick = interval.getEnding();
    }
    IShape copy = shape.copyShape();
    copy.changeSize(new Size(interpolate(begSize.getWidth(), endSize.getWidth(), tick),
            interpolate(begSize.getHeight(), endSize.getHeight(), tick)));
    return copy;
  }

  /**
   * gets the beginning size of this change size.
   * @return the beginning size of this transformation.
   */
  public Size getBegSize() {
    return begSize;
  }

  /**
   * gets the ending size of this change size.
   * @return the ending size of this transformation.
   */
  public Size getEndSize() {
    return endSize;
  }


  /**
   * Returns the string representation of this transformation.
   * @return the string representation of this tranformation.
   */
  @Override
  public String toString() {
    //ASSIGNMENT 5 REMOVED FROM TICK TO TICK PORTION OF STRING, this part will be created
    //in other methods, depending on if a tick or second is necessary for timing.
    return this.shape.getNameOfShape() + " " + "Changes size from " + this.begSize.getWidth() +
            "x" + this.begSize.getHeight() + " to size " + this.endSize.getWidth() +
            "x" + this.endSize.getHeight();
  }

  @Override
  public String svgTag(double tempo) {
    StringBuilder stringB = new StringBuilder();
    String attributeName = "width";
    double from = begSize.getWidth();
    double to = endSize.getWidth();
    for (int i = 1; i <= 2; i = i + 1) {
      stringB.append( "<animate attributeType=\"xml\" " + interval.intervalTag(tempo)
              + "attributeName=\"" + attributeName + "\" from=\"" +
              from + "\" to=\"" + to + "\" fill=\"freeze\" />\n");
      attributeName = "height";
      from = begSize.getHeight();
      to = endSize.getHeight();
    }
    return stringB.toString();
  }



}
