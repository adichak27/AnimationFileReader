package model;

import java.util.Objects;

/**
 * A Coordinate class that represents a location on a coordinate plane, with x and y values.
 * This class will be used to specify the location of shapes in an animator.
 */
public class Coordinate {
  //these fields are final to prevent any accidental mutations.
  // when necessary, new coordinates will be created instead of mutated.
  private int x;
  private int y;

  /**
   * Constructs a coordinate at the given points.
   * @param x the x value of this coordinate
   * @param y the y value of this coordinate
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }


  /**
   * Returns the x value of this coordinate.
   * @return x value
   */
  public int getX() {
    return this.x;
  }

  /**
   * returns the y value of this coordinate.
   * @return y value
   */

  public int getY() {
    return this.y;
  }

  /**
   * sets the x value of this coordinate to the given value.
   * @param x the x value being set.
   */

  public void setX(int x) {
    this.x = x;
  }

  /**
   * sets the y value of this coordinate to the given value.
   * @param y the y value being set.
   */

  public void setY(int y) {
    this.y = y;
  }

  /**
   * overrides the equals method. A Coordinate is equal to another if their x and y values are.
   * @param o the object that is being passed in.
   * @return true if the 2 coordinates are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("object is null");
    }
    if (o instanceof Coordinate) {
      Coordinate that = (Coordinate) o;
      return this.getX() == that.getX() && this.getY() == that.getY();
    }
    return false;
  }

  /**
   * overrides the hashcode method since equals was overridden.
   * @return the hashCode of this coordinate.
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * Returns the string representation of this Coordinate.
   * @return string representation of coordinate.
   */
  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }
}
