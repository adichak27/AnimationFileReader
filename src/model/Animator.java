package model;

import java.util.List;

/**
 * Represents the Interface for an Animator. An Animator allows a user to
 */
public interface Animator {

  /**
   * Outputs the state of this Animator as a string. The state includes the starting state of each
   * shape, followed by an ordered listing of their transformations,
   * how they alter the shape, and the intervals that they occur over.
   * @return A String that describes the transformations that occur on states over an animation.
   */

  public String outputState();

  /**
   * adds a shape to this Animator if the shape does not already exist.
   * @param s the shape that is being added
   */
  public void addShape(IShape s);


  /**
   * Returns a list of all of the shapes in this Animator.
   * @return a list of the shapes in this animator
   */
  public List<IShape> getShapes();

  /**
   * Removes the given shape from this animator, and removes all of the transformations associated
   * with the given shape from the animator.
   * @param s the shape that is being removed from the animator.
   */
  public void removeShape(IShape s);

  /**
   * Adds a transformation to this animator, as well as to the shape that the transformation
   * will be performed on, only if the transfomration is able to be added.
   * The transformation can only be added if the transformation isnt both of a conflicting type,
   * and an overlapping interval, and if it does not affect the adjacency of transformations. Also
   * transformations must be added in order.
   * @param t the transformation that is attempting to be added
   */
  public void addTransformation(Transformation t);

  /**
   * Removes the given Transformation from this animators list of transformations, as well
   * as from the list of transformations that the shape stores.
   * @param t The transformation that is being removed
   */
  public void removeTransformation(Transformation t);

  /**
   * gets all of the transformations that will occur over the animation.
   * @return a list of all transformations.
   */
  public List<Transformation> getTransformations();


  /**
   * gets the last tick of this animator. the last tick is determined by looking through each shapes
   * transformationOverInterval field and determining the highest ending interval value. After,
   * each shapes highest value is compared, and that is the last tick.
   * @return the last tick of this animator.
   */
  public int getLastTick();

  /**
   * returns a copy of a list of shapes contained in this animator, with the states of the shapes
   * reflecting the state that the shape will be in at the specified tick. will use the
   * getShapeAtTick() method that will be implemented later. There is a stub located in the
   * EasyAnimator class for this method. With this method, the state of all shapes in the animator
   * can be retrieved at any tick, making this extremely useful for the view and controller.
   * If the shapes starting time is before the specified tick, it is not added to the list
   * and therefore not drawn.
   * @param tick The tick that the states are specified for.
   * @return a List of the copy of every shape in the animator, with each shapes state correctly
   *         reflecting the state at the given tick.
   */
  public List<IShape> getShapesAtTick(int tick);


  //added assignment 5
  /**
   * This method sets the dimensions of the canvas.
   * @param x coordinate of the canvas
   * @param y coordinate of the canvas
   * @param width this is the width of the canvas
   * @param height this is the height of the canvas
   */
  public void setCanvasDimensions(int x, int y, int width, int height);

  //added assignment 5
  /**
   * Looks through the hashmap and returns the corresponding IShape based on the name ID passed in.
   * @param name the name of the shape that we want to return from the hashMap
   * @return the corresponding IShape based on the name ID passed in.
   */
  public IShape getShapeWithName(String name);


  //added assignment 5
  /**
   * Returns an array of size 4 which represents the 4 Canvas Dimensions (x, y, width, height).
   * @return This method returns an array of size 4 which represents the 4 Canvas Dimensions
   */
  public int[] getCanvasDimensions();
}
