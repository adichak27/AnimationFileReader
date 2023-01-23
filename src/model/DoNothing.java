package model;



/**
 * The DoNothing class is used to fill the gaps of the animation when nothing is happening to
 * a shape.
 */
public class DoNothing extends AbstractTransformation {


  /**
   * Constructor for a DoNothing transformation, doesnt take in any extra parameters since nothing
   *  is changing.
   * @param shape the shape the transformation is happening to.
   * @param interval the interval the transformation is happening over.
   */
  public DoNothing(IShape shape, Interval interval) {
    super(shape, TransformationType.DoNothing, interval);
  }

  @Override
  public IShape execute(IShape shape, int tick) {
    return shape.copyShape();
  }

  /**
   * Returns the String representation of this transformation.
   * @return string representation of doNothing transformation.
   */
  @Override
  public String toString() {
    //removed tick portion of string in assignment 5 and added to methods in model and view.
    return this.shape.getNameOfShape() + " does nothing";
  }

  @Override
  public String svgTag(double tempo) {
    //dont need to say that nothing is being done.
    return "";
  }
}
