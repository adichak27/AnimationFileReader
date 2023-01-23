package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;



/**
 * Interface for IShape. An IShape is a shape that is used in the model to represent shapes
 * that are being animated.
 */
public interface IShape {

  /**
   * determines the color of this shape.
   * @return the color of the shape
   */
  Color getColor();

  /**
   * changes the color of this shape.
   * @param c the color that this shape is being changed to
   */
  void changeColor(Color c);

  /**
   * gets the coordinate location of this shape.
   * @return the coordinate location
   */
  Coordinate getLocation();

  /**
   * moves the shape to the given coordinate.
   * @param c the coordinate the shape is being moved to
   */
  void moveShape(Coordinate c);

  /**
   * Changes the size of the shape to the given size. Can change the length height or both.
   * @param s the size that we want the shape to change to.
   */
  void changeSize(Size s);

  /**
   * returns the height of this shape.
   * @return the height of the shape.
   */
  double getHeight();

  /**
   * returns the width of this shape.
   * @return the width of this shape
   */
  double getWidth();

  /**
   * the type of shape this is.
   * @return the name of the shape as a string.
   */
  String getNameOfShape();

  /**
   * Returns the TreeMap of all intervals and their commands specified for this shape.
   * @return Treemap with intervals and the commands to be done over them.
   */
  TreeMap<Interval, ArrayList<Transformation>> getTransformationsOverInterval();

  /**
   * will create a copy of this shape, and then
   * use the TransformationsOverInterval field in order to advance the shapes state to the tick
   * that is entered. Will use linear interpolation in a later assignment. There is a stub in
   * the AShape class for this method.
   * @param tick represents a unit of time
   * @return the state of the shape at the passed in tick. To be used in Animator
   */
  IShape getShapeAtTick(int tick);

  /**
   * Removes the given transformation from the transformationsOverInterval treemap.
   * @param t the transformation to be removed
   */
  void removeTransformation(Transformation t);

  /**
   * Returns a list of all the transformation that were done on the shape, by combing through the
   * transformationsOverInterval treeMap.
   * @return List of all transformations
   */
  ArrayList<Transformation> getAllTransformations();

  /**
   * Returns time at which the shape appears.
   * @return the startOfLife field of the shape
   */
  int getStartOfLife();

  /**
   * Returns time at which the shape disappears.
   * @return the endOfLife field of the shape
   */
  int getEndOfLife();

  /**
   * Ensures that this transformation does not teleport the shape by checking the positions of the
   * shape at the beginning of the transformation
   * and when it finished it's last move transformation.
   * @param t the transformation we are checking for adjacency
   * @throws IllegalArgumentException if the new transformation causes teleportation
   */
  void checkAdjacency(Transformation t) throws IllegalArgumentException;


  //added assignement 5 for copying shapes
  /**
   * This method creates a copy of the shape which has all the same attributes and transformations.
   * @return returns a copy of the shape
   */
  public IShape copyShape();


  //added assignment 5
  /**
   * Returns a string in an svg format that represents the svg tag for a shape and.
   * @param tempo ticks per second
   * @return a string in SVG format
   */
  public String svgTagForShape(double tempo);

  //added assignment 5
  /**
   * Gives us a string in svg format which is used to depict the color of the shape.
   * @return a string in svg format depicting the color of the shape
   */
  public String shapeColorToString();

  //added assignemtn 5
  /**
   * Set the treeMap that keeps track of all the transformation the given treemap that is provided.
   * This is mainly used when we are copying shapes so we don't lose their transformations.
   * @param t the treeMap that contains transformations
   */
  public void setTransformationsOverInterval(TreeMap<Interval, ArrayList<Transformation>> t);
}
