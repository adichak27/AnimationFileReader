package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.awt.Color;




/**
 * Abstract class for IShape. Defines most methods for subclasses, besides toString.
 * For now the fields are mutable, but that design might change next assignment.
 */
public abstract class AShape implements IShape {
  protected Color color;
  protected Coordinate position;
  protected Size size;
  protected String name;
  //The commands that take place over an interval for this shape
  protected  TreeMap<Interval, ArrayList<Transformation>> transformationsOverInterval;
  protected final int startOfLife;
  protected final int endOfLife;

  /**
   * Constructs an Ashape, using the paramaters passed in.
   *
   * @param name     the name of this shape.
   * @param size     the size of this shape.
   * @param position the position of this shape.
   * @param color    the color of this shape.
   */
  protected AShape(String name, Size size, Coordinate position, Color color, int
                   startOfLife, int endOfLife) {
    checkNotNull(name);
    checkNotNull(size);
    checkNotNull(position);
    checkNotNull(color);
    this.name = name;
    this.size = size;
    this.position = position;
    this.color = color;
    this.transformationsOverInterval = new TreeMap<>(new IntervalComparator());
    this.startOfLife = startOfLife;
    this.endOfLife = endOfLife;
  }

  @Override
  abstract public IShape copyShape();

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public TreeMap<Interval, ArrayList<Transformation>> getTransformationsOverInterval() {
    return this.transformationsOverInterval;
  }

  public void setTransformationsOverInterval(TreeMap<Interval, ArrayList<Transformation>> t) {
    transformationsOverInterval = t;
  }



  @Override
  public void changeColor(Color c) {
    checkNotNull(c);
    this.color = c;
  }

  @Override
  public Coordinate getLocation() {
    return this.position;
  }

  @Override
  public void moveShape(Coordinate c) {
    checkNotNull(c);
    this.position.setX(c.getX());
    this.position.setY(c.getY());
  }

  @Override
  public void changeSize(Size thatSize) {
    checkNotNull(thatSize);
    this.size.setSize(thatSize);
  }

  @Override
  public double getWidth() {
    return this.size.getWidth();
  }

  @Override
  public double getHeight() {
    return this.size.getHeight();
  }


  /**
   * Returns the state of the shape at the given tick. This will be implemented through linear
   * interpulation for a later assignment. If the tick is prior to the starting time of the shape
   * this method wuill just return the starting state of the shape.
   * @param tick represents a unit of time
   * @return state of the IShape at given tick
   */
  public IShape getShapeAtTick(int tick) {
    IShape copy = this.copyShape();
    List<Transformation> completeTransformations = getTransformationsBeforeTick(tick);
    for (Transformation t : completeTransformations) {
      copy = t.execute(copy, tick);
    }
    List<Transformation> partialTransformations = getPartialTransformationsAtTick(tick);
    for (Transformation t : partialTransformations) {
      copy = t.execute(copy, tick);
    }
    return copy;

  }

  //gets a list of all transformations that have been fully applied to the shape
  // up to the given tick.
  private List<Transformation> getTransformationsBeforeTick(int tick) {
    List<Transformation> transformations = new ArrayList<>();
    Collection<Interval> intervals = transformationsOverInterval.keySet();
    for (Interval i : intervals) {
      if (i.getEnding() <= tick) {
        transformations.addAll(transformationsOverInterval.get(i));
      }
    }

    return transformations;
  }

  private List<Transformation> getPartialTransformationsAtTick(int tick) {
    List<Transformation> partialTransformations = new ArrayList<>();
    Collection<Interval> intervals = transformationsOverInterval.keySet();
    for (Interval i : intervals) {
      if ((i.getStarting() <= tick) && (i.getEnding() > tick)) {
        partialTransformations.addAll(transformationsOverInterval.get(i));
      }
    }

    return partialTransformations;
  }

  /**
   * Checks if the given Object is a Shape with the same name and type as this.
   *
   * @return true if {@code o} is an instance of Ishape and has the same name.
   */
  @Override
  public boolean equals(Object o) {
    checkNotNull(o);
    if (!(o instanceof IShape)) {
      return false;
    }

    IShape that = (IShape) o;
    return this.name.equals(that.getNameOfShape());
  }

  /**
   * Gives the hashcode of this shape in terms of its size, color, position, and visibility.
   *
   * @return the hashcode of this shape's properties.
   */
  @Override
  public int hashCode() {

    return Objects.hash(name, size, position, color);
  }

  @Override
  public void checkAdjacency(Transformation t) {
    if (transformationsOverInterval.containsKey(t.getInterval())) {
      transformationsOverInterval.get(t.getInterval()).add(t);
      //if the interval of the passed in transformation exists, add the transformation to the list
      try {
        //check that move transformations are still adjacent
        checkMoveTransformations();
        //remove it from this spot in the list for now.
        transformationsOverInterval.get(t.getInterval()).remove(t);
      } catch (IllegalArgumentException iae) {
        transformationsOverInterval.get(t.getInterval()).remove(t);
        throw new IllegalArgumentException("Transformation does not maintain adjacency");
      }
    } else {
      //add a new interval and transformation to the treemap
      transformationsOverInterval.put(t.getInterval(),
              new ArrayList<Transformation>(Arrays.asList(t)));
      try {
        //check that adjacency is maintained
        checkMoveTransformations();
        transformationsOverInterval.remove(t.getInterval());
        //remove transformation for now
      } catch (IllegalArgumentException iae) {
        transformationsOverInterval.remove(t.getInterval());
        throw new IllegalArgumentException("Transformation does not maintain adjacency");
      }
    }

  }


  //removes this transformation from the list of transformations, and from the shapes treemap
  // if it was the last transformation over the specified interval that is being removed,
  // the transformation is replaced with a do nothing command.

  /**
   * Removes this transformation from the list of transformations, and from the shapes treemap if
   * if was the last transformation over the specified interval that is being removed, the
   * transformation is replaced with a do nothing command.
   * @param t the transformation to be removed
   */
  public void removeTransformation(Transformation t) {
    checkNotNull(t);
    if (transformationsOverInterval.get(t.getInterval()).size() == 1) {
      transformationsOverInterval.remove(t.getInterval());
      ArrayList<Transformation> transformations = new ArrayList<>();
      transformations.add(new DoNothing(t.getShape(), t.getInterval()));
      transformationsOverInterval.put(t.getInterval(), transformations);
    } else {
      transformationsOverInterval.get(t.getInterval()).remove(t);
    }
  }


  public String getNameOfShape() {
    return this.name;
  }


  //This method checks that the adjacency constraint is enforced. All of the moves
  // start where the previous move ended.
  protected void checkMoveTransformations() {
    ArrayList<Transformation> allTransformations = getAllTransformations();
    ArrayList<Move> moveTransformations = new ArrayList<>();
    for (Transformation t : allTransformations) {
      if (t.getType() == TransformationType.Move) {
        moveTransformations.add((Move) t);
      }
    }
    for (int i = 1; i < moveTransformations.size(); i++) {
      //will always be of type move
      Move t = moveTransformations.get(i);
      Move prevTransformation = moveTransformations.get(i - 1);
      if (!sameEnding(t, prevTransformation)) {
        throw new IllegalArgumentException("Move commands are not adjacent");
      }
    }
  }

  // both transformations are of type move, enforced by parent method
  protected boolean sameEnding(Move t, Move prevTransformation) {
    checkNotNull(t);
    checkNotNull(prevTransformation);
    return t.getStart().equals(prevTransformation.getEnd());
  }


  /**
   * This method will cycle through the treemap that contains all the transformations by interval
   * and put all the transformation into a single list.
   * @return a list of all transformations for this shape
   */
  public ArrayList<Transformation> getAllTransformations() {
    ArrayList<Transformation> allTransformations = new ArrayList<Transformation>();
    Collection<ArrayList<Transformation>> listOfTransformations =
            transformationsOverInterval.values();
    for (ArrayList<Transformation> t : listOfTransformations) {
      allTransformations.addAll(t);
    }
    return allTransformations;
  }

  private void checkNotNull(Object o) {
    try {
      Objects.requireNonNull(o);
    } catch (NullPointerException npe) {
      throw new IllegalArgumentException("Argument cannot be null");
    }
  }

  public int getStartOfLife() {
    return this.startOfLife;
  }

  public int getEndOfLife() {
    return this.endOfLife;
  }


  abstract public String svgTagForShape(double tempo);

  public String shapeColorToString() {
    return "\"rgb(" + color.getRed() + "," + color.getGreen()
            + "," + color.getBlue() + ")\"";
  }

  protected StringBuilder buildTransformationTags(StringBuilder out, double tempo) {
    Collection<Interval> keys = transformationsOverInterval.keySet();
    for (Interval key : keys) {
      ArrayList<Transformation> transformations = transformationsOverInterval.get(key);
      for (Transformation t : transformations) {
        out.append(t.svgTag(tempo));
      }
    }
    return out;
  }


}
