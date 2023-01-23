package model;

import java.util.Objects;

/**
 * Represents an inveral over 2 ticks, a starting and ending tick.
 */
public class Interval {
  private final int starting;
  private final int ending;

  /**
   * A constructor that sets the starting tick and the ending tick.
   *
   * @param starting starting point of the interval
   * @param ending   ending point of the interval
   */
  public Interval(int starting, int ending) {
    if (validInterval(starting, ending)) {
      this.starting = starting;
      this.ending = ending;
    } else {
      throw new IllegalArgumentException("Not a valid interval");
    }
  }

  //checks that the interval trying to be created is valid.
  private boolean validInterval(int start, int end) {
    return start >= 0 && end >= 0 && start < end;
  }

  /**
   * Gets the starting time of this Interval.
   *
   * @return this Interval's starting time.
   */
  public int getStarting() {
    return this.starting;
  }

  /**
   * Gets the ending time of this Interval.
   *
   * @return this Interval's ending time.
   */
  public int getEnding() {
    return this.ending;
  }

  /**
   * checks to see if 2 intervals are overlapping. An overlap occurs when the given intervals
   * starting time is higher than this intervals, but is lower than this intervals ending time.
   * @param other the interval checking for overlap
   * @return true if the given interval overlaps this one
   */

  public boolean checkOverlap(Interval other) {
    if (other == null) {
      throw new IllegalArgumentException("interval cannot be null");
    }
    return starting <= other.starting &&
            ending > other.starting;
  }



  /**
   * Overrides the equals method and makes it so that if an interval has the same start and end,
   * it is the same interval.
   * @param o the object being compared.
   * @return true if they equal
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    if (!(o instanceof Interval)) {
      return false;
    }
    Interval that = (Interval) o;
    return this.starting == that.starting &&
            this.ending == that.ending;
  }

  /**
   * Overrides hashcode.
   * @return hashcode of interval
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.starting, this.ending);
  }


  /**
   * Returns a string in an SVG file format which depics the start and end times of the
   * transformation.
   * @param tempo the tick per second
   * @return string representation of the interval in SVG format
   */
  public String intervalTag(double tempo) {
    int duration = ending - starting;
    double begSecond = (double) starting / tempo;
    double durSecond = (double) duration / tempo;

    return "begin=\"" + begSecond + "s\" dur=\"" + durSecond + "s\" ";
  }
}
