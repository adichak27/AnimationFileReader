package model;


/**
 * Class that represents a size, with a width and height.
 */
public class Size  {
  private double width;
  private double height;

  /**
   * A Size constructor in terms of width of height. values cannot be negative or 0.
   */
  //consider making width and height an int
  public Size(double width, double height) {
    if (width > 0 && height > 0) {
      this.width = width;
      this.height = height;
    } else {
      throw new IllegalArgumentException("height and width are not possible to create a shape");
    }

  }

  /**
   * gets the width of this size.
   * @return width of this size.
   */
  public double getWidth() {
    return this.width;
  }

  /**
   * gets the height of this size.
   * @return the height of this size
   */

  public double getHeight() {
    return this.height;
  }

  /**
   * sets this sizes fields to the fields of the size that is passed in.
   * @param that the size thats values will change this size.
   */
  public void setSize(Size that) {
    if (that == null) {
      throw new IllegalArgumentException("Size cannot be null");
    }
    this.width = that.width;
    this.height = that.height;
  }
}
