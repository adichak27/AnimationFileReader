package model;



/**
 * Abstract class for a transformation. Has fields for the shape being transformed, the type
 * of transformation, the interval over whichthe transformation happens, and the duration
 * of the transformation.
 */
abstract public class AbstractTransformation implements Transformation {
  protected final IShape shape;
  protected final TransformationType type;
  protected final Interval interval;
  protected final int duration;


  /**
   * sets values of transformation to the fields, and calculates the duration of the transformation.
   * @param shape the shape that the transformation belongs to
   * @param type the type of the transformation
   * @param interval the interval the transformation occurs over.
   */
  public AbstractTransformation(IShape shape, TransformationType type, Interval interval) {
    if ((shape == null) || (interval == null)) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.shape = shape;
    this.type = type;
    this.interval = interval;
    this.duration = interval.getEnding() - interval.getStarting();
  }


  @Override
  public abstract IShape execute(IShape beginning, int tick);

  @Override
  public Interval getInterval() {
    return this.interval;
  }

  @Override
  public IShape getShape() {
    return this.shape;
  }


  @Override
  public double interpolate(double begVal, double endVal, int tick) {
    double rateOfChange = (endVal - begVal) / duration;
    double changeInVal   = (tick - interval.getStarting()) * rateOfChange;
    return Math.round(begVal + changeInVal);

  }


  @Override
  public void validStartAndEndLife() {
    // we will allow this
    if (type == TransformationType.DoNothing) {
      return;
    }
    if (shape.getStartOfLife() <= interval.getStarting() &&
            interval.getStarting() <= shape.getEndOfLife()) {
      return;
    } else {
      throw new IllegalArgumentException("Invalid start and end life");
    }
  }

  @Override
  public TransformationType getType() {
    return this.type;
  }

  @Override
  public abstract String toString();

  @Override
  public abstract String svgTag(double tempo);
}

