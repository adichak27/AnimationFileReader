package model;


/**
 * Interface for a transformation, which is used in the Animator in order to change the state of
 * the shape the transformation was created for.
 */
public interface Transformation {

  /**
   * Executes the transformation on a copy of the shape in the command, updating the state of the
   * copy to the correct state at that tick, using linear interpulation. Will be implemented later.
   * @param tick the tick that the transformation is executing on.
   * @return A copy of the shape in the correct state at the tick.
   */
  IShape execute(IShape shape, int tick);

  /**
   * gets the shape that this Transformation is being executed on.
   * @return the shape this transformation is happening.
   */
  IShape getShape();

  /**
   * gets the interval that this transformation is happening over.
   * @return the interval of this transformation.
   */
  Interval getInterval();

  /**
   * gets the type of this transformation.
   * @return
   */
  TransformationType getType();

  /**
   * converts this transformation into a string. The string describes what shape the transformation
   * is acting on, as well as over what interval, and what is being changed.
   * @return A string description of the transformation.
   */
  String toString();

  /**
   * This method checks if this transformation occurs between the appearence and disappearence time
   * of the shape.
   */
  void validStartAndEndLife();

  /**
   * This method takes in two attribute values (color,size,position) and interpolates to find the
   * value at any time during the transformation.
   * @param begVal beggining value of attribute
   * @param endVal end value of attribute
   * @param tick the tick at which we want to find the value
   * @return the correct value of the attribute at the given tick
   */
  double interpolate(double begVal, double endVal, int tick);

  /**
   * This method will return a string in svg format depicting the transformation that will take
   * place on the shape.
   * @param tempo represents the ticks per second
   * @return a string in SVG format
   */
  String svgTag(double tempo);
}
