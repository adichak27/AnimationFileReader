package model;

import java.awt.Color;

/**
 * Implments the TweenBuilder Interface to help read from file. Methods are implemented using our
 * models methods. Serves as a bridge between our methods and the class given ones.
 */
public class BuilderImpl implements TweenModelBuilder<Animator> {
  private final Animator model = new EasyAnimator();



  @Override
  public TweenModelBuilder<Animator> setBounds(int width, int height) {
    //first 2 values are default
    model.setCanvasDimensions(0, 0, width, height);
    return this;
  }

  @Override
  public TweenModelBuilder<Animator> addOval(String name, float cx, float cy, float xRadius,
                                             float yRadius, float red, float green, float blue,
                                             int startOfLife, int endOfLife) {
    int x = Math.round(cx);
    int y = Math.round(cy);
    int r = convertColorToInt(red);
    int g = convertColorToInt(green);
    int b = convertColorToInt(blue);
    IShape oval = new Oval(name, new Coordinate(x, y), new Color(r, g, b),
            new Size(xRadius, yRadius),
            startOfLife, endOfLife);
    model.addShape(oval);
    return this;

  }

  @Override
  public TweenModelBuilder<Animator> addRectangle(String name, float lx, float ly, float width,
                                                  float height, float red, float green, float blue,
                                                  int startOfLife, int endOfLife) {
    int x = Math.round(lx);
    int y = Math.round(ly);
    int r = convertColorToInt(red);
    int g = convertColorToInt(green);
    int b = convertColorToInt(blue);
    IShape rect = new Rectangle(name, new Coordinate(x, y), new Color(r, g, b),
            new Size(width, height),
            startOfLife, endOfLife);
    model.addShape(rect);
    return this;
  }

  @Override
  public TweenModelBuilder<Animator> addPlus(String name, float x, float y, float width,
                                             float height, float red, float green, float blue,
                                             int startOfLife, int endOfLife) {
    int newX = Math.round(x);
    int newY = Math.round(y);
    int r = convertColorToInt(red);
    int g = convertColorToInt(green);
    int b = convertColorToInt(blue);
    IShape rect = new Plus(name, new Size(width, height), new Coordinate(newX, newY),
            new Color(r, g, b),
            startOfLife, endOfLife);
    model.addShape(rect);
    return this;
  }

  @Override
  public TweenModelBuilder<Animator> addMove(String name, float moveFromX,
                                             float moveFromY, float moveToX, float moveToY,
                                             int startTime, int endTime) {
    //if shape doesnt exist, null is passed into new move, and that will throw an exception
    Interval interval = new Interval(startTime, endTime);
    Coordinate startC = new Coordinate(Math.round(moveFromX), Math.round(moveFromY));
    Coordinate endC = new Coordinate(Math.round(moveToX), Math.round(moveToY));
    IShape newShape = model.getShapeWithName(name);
    Transformation move = new Move(newShape, interval,  startC, endC);
    model.addTransformation(move);
    return this;
  }

  @Override
  public TweenModelBuilder<Animator> addColorChange(String name, float oldR,
                                                    float oldG, float oldB, float newR,
                                                    float newG, float newB, int startTime,
                                                    int endTime) {
    Interval interval = new Interval(startTime, endTime);
    int oldRed = convertColorToInt(oldR);
    int oldGreen = convertColorToInt(oldG);
    int oldBlue = convertColorToInt(oldB);
    int newRed = convertColorToInt(newR);
    int newGreen = convertColorToInt(newG);
    int newBlue = convertColorToInt(newB);
    IShape newShape = model.getShapeWithName(name);
    Transformation changeColor = new ChangeColor(newShape, interval, oldRed, oldGreen, oldBlue,
            newRed, newGreen, newBlue);
    model.addTransformation(changeColor);
    return this;
  }

  @Override
  public TweenModelBuilder<Animator> addScaleToChange(String name, float fromSx, float fromSy,
                                                      float toSx, float toSy, int startTime,
                                                      int endTime) {
    Interval interval = new Interval(startTime, endTime);
    int fromWidth = Math.round(fromSx);
    int fromHeight = Math.round(fromSy);
    int toWidth = Math.round(toSx);
    int toHeight = Math.round(toSy);
    IShape newShape = model.getShapeWithName(name);
    Transformation changeSize = new ChangeSize(newShape, interval, new Size(fromWidth, fromHeight),
            new Size(toWidth, toHeight));
    model.addTransformation(changeSize);
    return this;
  }

  @Override
  public Animator build() {
    return model;
  }

  private int convertColorToInt(float val) {
    return (int) Math.round(val * 255);
  }
}
