package model;

import java.util.Comparator;

/**
 * Class represents a Comparator for Intervals, to be used with TreeMap in AShape class.
 */
public class IntervalComparator implements Comparator<Interval> {

  /**
   * An interval is compared to another by their starting values.
   * @param o1 the first interval
   * @param o2 the second interval
   * @return int value that represents equality.
   */
  @Override
  public int compare(Interval o1, Interval o2) {
    if ((o1 == null) || (o2 == null)) {
      throw new IllegalArgumentException("Interval cannot be null");
    }
    return Integer.compare(o1.getStarting(), o2.getStarting());
  }
}
