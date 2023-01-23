package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;



/**
 * Implementation of an Animator. This EasyAnimator allows users to first create shapes, and then
 * input Transformations to happen to a shape over an interval of ticks. The user can also remove
 * shapes, and remove transformations. In order to add a transformation to this implementation,
 * there are some constraints. First, the transformation being added must belong to a shape
 * that exists in this animator. also, the transformation must not be of the same type as another
 * transformation happening to the same shape over an interval. Adjacency must also be protected,
 * meaning that a transformation cannot be added if the ending position of the previous doesnt line
 *  up with the starting of that transformation. Finally, transformation must be added in order,
 *  meaning that after an interval from 10 to 20 is added, one from 5 to 15 cannot be. Also,
 *  the animator automatically fills any gaps of intervals with do Nothing transformations. The user
 *  can retrieve the state of the Animator using the outputState method. During Assignment 5, we
 *  changed this classes fields quite a lot. The shapesMap field is now a linkedhashmap, so that
 *  it is sorted, we got rid of the transformations field because we did not need it, and
 *  we added canvasDimensions for the builder and view.
 */
public class EasyAnimator implements Animator {
  //each shape within the animator, kept in insertion order so that the shapes appear correctly
  private final LinkedHashMap<String, IShape> shapesMap;
  //each transformation that will happen over this animator.
  private final ArrayList<Transformation> transformations;
  //added during assignment 5 to hold dimensions of the canvas.
  private int[] canvasDimensions;

  /**
   * Constructor for an EasyAnimator. Takes in no parameters, and initializes the shapesMap and
   * transformations field to empty types.
   */
  public EasyAnimator() {
    this.shapesMap = new LinkedHashMap<>();
    this.transformations = new ArrayList<>();
    //this is the default canvas setting.
    //first 2 ints represent top left corner of canvas
    // 3rd is the width of canvas
    // 4th is the height of the canvas
    this.canvasDimensions = new int[] {0, 0, 500, 500};
  }


  @Override
  public String outputState() {
    StringBuilder str = new StringBuilder();
    for (IShape s : getShapes()) {
      String createString = "Create: " + s.toString() + "\n";
      str.append(createString);
      ArrayList<Transformation> transformations = s.getAllTransformations();
      for (Transformation t : transformations) {
        //each transformation occurs over a tick interval, so this portion of string
        //represents that
        str.append("From tick "  + t.getInterval().getStarting() + " to " +
                t.getInterval().getEnding() +  ", ");
        str.append(t.toString() + "\n");
      }
      str.append("\n");
    }
    return str.toString();
  }

  @Override
  public void addShape(IShape s) {
    ensureNotNull(s);
    if (shapesMap.containsKey(s.getNameOfShape())) {
      throw new IllegalArgumentException("Cannot add shape");
    }
    shapesMap.put(s.getNameOfShape(), s);
  }

  //adds do nothing from the start of animation to when the first transformation happens
  // for the shape added.
  private void fillBeginningGap(IShape s) {
    Transformation firstT = s.getAllTransformations().get(1);
    int begTimeFirstT = firstT.getInterval().getStarting();
    if (begTimeFirstT > 0) {
      addTransformation(new DoNothing(s, new Interval(0, begTimeFirstT)));
    }
  }

  @Override
  public List<IShape> getShapes() {
    ArrayList<IShape> copyShapes = new ArrayList<>();
    for (String key : shapesMap.keySet())  {
      copyShapes.add(shapesMap.get(key).copyShape());
    }
    return copyShapes;
  }

  @Override
  public void removeShape(IShape s) {
    ensureNotNull(s);
    if (! shapesMap.containsKey(s.getNameOfShape())) {
      throw new IllegalArgumentException("cannot remove shape that is not in animator");
    }
    shapesMap.remove(s.getNameOfShape());
    for (Transformation t : transformations) {
      if (t.getShape().equals(s)) {
        removeTransformation(t);
      }
    }
  }



  @Override
  public void addTransformation(Transformation t) {
    ensureNotNull(t);
    if (! canAddTransformation(t)) {
      throw new IllegalArgumentException("Cannot add transformation");
    } else {
      //adds transformation to list in animator
      t.getShape().checkAdjacency(t);
      TreeMap<Interval, ArrayList<Transformation>> commandsForShape =
              t.getShape().getTransformationsOverInterval();
      if (commandsForShape.containsKey(t.getInterval())) {
        //if the interval of the command already exists in shape, add the command to the interval
        commandsForShape.get(t.getInterval()).add(t);
        transformations.add(t);
      } else {
        //get the key previous to the one being added
        Interval previousKey = commandsForShape.floorKey(t.getInterval());
        if ((previousKey != null) && previousKey.getEnding() < t.getInterval().getStarting()) {
          //if this is not the first key, and there is a gap between, create a new command
          // to fill gap
          Interval gapInterval = new
                  Interval(previousKey.getEnding(), t.getInterval().getStarting());
          commandsForShape.put(gapInterval,
                  new ArrayList<Transformation>(Arrays.asList(new DoNothing(t.getShape(),
                          gapInterval))));
          transformations.add(new DoNothing(t.getShape(), gapInterval));
        }
        //added assignment 6 to fill gap
        /*
        int BegTimeFirstT = t.getInterval().getStarting();
        if (previousKey == null && BegTimeFirstT > t.getShape().getStartOfLife()) {
          transformations.add(new DoNothing(t.getShape(),
                  new Interval(t.getShape().getStartOfLife(), BegTimeFirstT)));

        }

         */
        //puts new interval and its command into shape
        transformations.add(t);
        commandsForShape.put(t.getInterval(), new ArrayList<Transformation>(Arrays.asList(t)));
      }
    }
  }

  //command can only be added if it is not the same type of overlapping command,
  // and also and also that the shape is adjacent to its prev
  //state.
  private boolean canAddTransformation(Transformation t) {
    //check that the transformation occurs over a shape that exists in this model.
    if ((! shapesMap.containsKey(t.getShape().getNameOfShape()))) {
      //  || (transformationStartsBeforePrev(t))
      return false;
    }
    t.validStartAndEndLife();
    //check that if there is overlap, the types of transformations dont also overlap
    TreeMap<Interval, ArrayList<Transformation>> transformationsForShape =
            t.getShape().getTransformationsOverInterval();
    Set<Interval> intervalsSet = transformationsForShape.keySet();
    for (Interval i : intervalsSet) {
      ArrayList<Transformation> transformationsOverInterval = transformationsForShape.get(i);
      if (i.checkOverlap(t.getInterval())) {
        for (Transformation transformationInInterval : transformationsOverInterval) {
          if (t.getType() == transformationInInterval.getType()) {
            return false;
          }
        }
      }
    }
    return true;
  }


  //A constraint on our model is that the user inputs transformation in order,
  // meaning that if the prev
  // transformation starts at tick 5, the next transformation must start at at least 5, no sooner.
  private boolean transformationStartsBeforePrev(Transformation t) {
    ensureNotNull(t);
    Interval transformationAfterT =
            t.getShape().getTransformationsOverInterval().higherKey(t.getInterval());
    //if there is an interval higher than the one in this transformation, that means that
    // the starting interval of this transformation starts before the previous transformation
    // and this implementation does not allow this.
    return transformationAfterT != null;
  }

  @Override
  public void removeTransformation(Transformation t) {
    ensureNotNull(t);
    t.getShape().removeTransformation(t);
    transformations.remove(t);

  }

  @Override
  public List<Transformation> getTransformations() {
    return this.transformations;
  }

  @Override
  //Change in assignemnt 5, the last tick of the model will now be determined
  // by the latest ending life of the shapes.
  public int getLastTick() {
    Collection<IShape> allShapes = shapesMap.values();
    int greatestTick = 0;
    for (IShape s: allShapes) {
      if (s.getEndOfLife() > greatestTick) {
        greatestTick = s.getEndOfLife();
      }
    }
    return greatestTick;
  }

  @Override
  public List<IShape> getShapesAtTick(int tick) {
    List<IShape> list = new ArrayList<>();
    Collection<String> keySet = shapesMap.keySet();
    for (String key : keySet) {
      IShape shape = shapesMap.get(key).copyShape();
      if (shape.getStartOfLife() <= tick) {
        list.add(shape.getShapeAtTick(tick));
      }
    }
    return list;
  }

  @Override
  public void setCanvasDimensions(int x, int y, int width, int height) {
    if ((width < 0) || (height < 0)) {
      throw new IllegalArgumentException("Cannot set dimensions that are negative");
    } else {
      this.canvasDimensions = new int[] {x, y, width, height};
    }
  }

  public int[] getCanvasDimensions() {
    return new int[] {canvasDimensions[0], canvasDimensions[1], canvasDimensions[2],
            canvasDimensions[3]};
  }


  //determines if object passed in is null
  private void ensureNotNull(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("Thing cannot be null");
    }
  }

  @Override
  public IShape getShapeWithName(String name) {
    //will return null if shape doesnt exist in model
    IShape copy = shapesMap.get(name).copyShape();
    return copy;
  }

}
