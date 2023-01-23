package model;

import java.awt.*;
import java.lang.reflect.Array;
import java.rmi.activation.ActivationGroup_Stub;
import java.util.ArrayList;
import java.util.Arrays;

public class Plus extends AShape {
  /**
   * Constructs an Plus shape, using the paramaters passed in.
   *
   * @param name        the name of this shape.
   * @param size        the size of this shape.
   * @param position    the position of this shape.
   * @param color       the color of this shape.
   * @param startOfLife when this shape will appear
   * @param endOfLife   when this shape will disappear
   */
  public Plus(String name, Size size, Coordinate position, Color color, int startOfLife,
              int endOfLife) {
    super(name, size, position, color, startOfLife, endOfLife);
    if (size.getHeight() != size.getWidth()) {
      throw new IllegalArgumentException("Plus shape must have the same height and width");
    }
  }

  @Override
  public IShape copyShape() {
    IShape Plus = new Plus(name, size, new Coordinate(position.getX(), position.getY()),
            color, startOfLife, endOfLife);
    Plus.setTransformationsOverInterval(this.getTransformationsOverInterval());
    return Plus;
  }

  @Override
  public String svgTagForShape(double tempo) {
    StringBuilder out = new StringBuilder();

    // need to use 2 rectangles to make up the plus shape for the svg.
    Rectangle horizontal = new Rectangle(this.name + 1, new Coordinate(position.getX(),
            (int) (position.getY() + (getHeight() / 4))), this.getColor(), new Size(size.getWidth(),
            (size.getHeight() / 2)), this.startOfLife, this.endOfLife);
    Rectangle vertical = new Rectangle(this.name + 2, new Coordinate((int) (position.getX() +
            (getHeight() / 4)), position.getY()), this.getColor(),
            new Size(size.getWidth() / 2, size.getHeight()), this.startOfLife, this.endOfLife);
    addTransfosToRects(horizontal, vertical);
    out.append(horizontal.svgTagForShape(tempo));
    out.append(vertical.svgTagForShape(tempo));
    return out.toString();


  }

  //adds the necessary transformations to the 2 rects that make up the plus sign for the
  // svg view.
  private void addTransfosToRects(Rectangle horizontal, Rectangle vertical) {
    for (Transformation t : getAllTransformations()) {
      if (t instanceof Move) {
        //gathers the transformations to add to the horizontal and vert rectangles
        ArrayList<Transformation> moves = getMovesForPlusRects(horizontal, vertical, (Move) t);
        //adds the transformations to the 2 rectangles.
        addMoveOrColorToRects(horizontal, vertical, moves.get(0), moves.get(1));
      } else if (t instanceof ChangeSize) {
        // same as above comments.
        ArrayList<Transformation> sizes = getSizeChangesForPlusRects(horizontal, vertical,
                (ChangeSize) t);
        addSizeToRects(horizontal, vertical, sizes.get(0), sizes.get(1), sizes.get(2),
                sizes.get(3));

      } else if (t instanceof ChangeColor) {
        // same as above comments.
        ArrayList<Transformation> colors =
                getColorChangesForPlusRects(horizontal, vertical, (ChangeColor) t);
        addMoveOrColorToRects(horizontal, vertical, colors.get(0), colors.get(1));
      }
    }
  }

  //adds the move or color change to the 2 rectangles for the plus.
  private void addMoveOrColorToRects(IShape horizontal, IShape vertical, Transformation horiT,
                                     Transformation vertT) {
    if (horizontal.getTransformationsOverInterval().containsKey(horiT.getInterval())) {
      //if the interval of the command already exists in shape, add the command to the interval
      horizontal.getTransformationsOverInterval().get(horiT.getInterval()).add(horiT);
      vertical.getTransformationsOverInterval().get(vertT.getInterval()).add(vertT);
    } else {
      horizontal.getTransformationsOverInterval().put(horiT.getInterval(),
              new ArrayList<>(Arrays.asList(horiT)));
      vertical.getTransformationsOverInterval().put(vertT.getInterval(),
              new ArrayList<>(Arrays.asList(vertT)));
    }
  }

  //adds the change size and additional moves to the 2 rectangles for the plus.
  private void addSizeToRects(IShape horizontal, IShape vertical, Transformation horiSize,
                              Transformation vertSize, Transformation horiMove,
                              Transformation vertMove) {
    if (horizontal.getTransformationsOverInterval().containsKey(horiSize.getInterval())) {
      //if the interval of the command already exists in shape, add the command to the interval
      horizontal.getTransformationsOverInterval().get(horiSize.getInterval()).add(horiSize);
      vertical.getTransformationsOverInterval().get(vertSize.getInterval()).add(vertSize);
      horizontal.getTransformationsOverInterval().get(horiMove.getInterval()).add(horiMove);
      vertical.getTransformationsOverInterval().get(vertMove.getInterval()).add(vertMove);
    } else {
      horizontal.getTransformationsOverInterval().put(horiSize.getInterval(),
              new ArrayList<>(Arrays.asList(horiSize)));
      vertical.getTransformationsOverInterval().put(vertSize.getInterval(),
              new ArrayList<>(Arrays.asList(vertSize)));
      horizontal.getTransformationsOverInterval().get(horiMove.getInterval()).add(horiMove);
      vertical.getTransformationsOverInterval().get(vertMove.getInterval()).add(vertMove);
    }
  }

  // gets the Move transformations necessary on the 2 rects for the svg
  private ArrayList<Transformation> getMovesForPlusRects(IShape horizontal, IShape vertical,
                                                         Move t) {
    ArrayList<Transformation> transfos = new ArrayList<>();
    Coordinate horiStartCoord = new Coordinate(t.getStart().getX(),
            t.getStart().getY() + ((int) getHeight() / 4));
    Coordinate horiEndCoord = new Coordinate(t.getEnd().getX(),
            t.getEnd().getY() + ((int) getHeight() / 4));
    Coordinate vertStartCoord = new Coordinate((t.getStart().getX() +
            ((int) getHeight() / 4)),
            t.getStart().getY());
    Coordinate vertEndCoord = new Coordinate((t.getEnd().getX() +
            ((int) getHeight() / 4)),
            t.getEnd().getY());
    Transformation horiMove = new Move(horizontal, t.getInterval(), horiStartCoord,
            horiEndCoord);
    Transformation vertMove = new Move(vertical, t.getInterval(), vertStartCoord, vertEndCoord);
    transfos.add(horiMove);
    transfos.add(vertMove);
    return transfos;
  }

  // gets the change color transformations necessary on the 2 rects for svg
  private ArrayList<Transformation> getColorChangesForPlusRects(IShape horizontal, IShape vertical,
                                                                ChangeColor t) {
    ArrayList<Transformation> colorChanges = new ArrayList<>();
    ArrayList<Integer> startingColor = t.getBeginningColor();
    ArrayList<Integer> endingcolor = t.getEndingColor();
    Transformation horiColor = new ChangeColor(horizontal, t.getInterval(), startingColor.get(0),
            startingColor.get(1), startingColor.get(2), endingcolor.get(0), endingcolor.get(1),
            endingcolor.get(2));
    Transformation vertColor = new ChangeColor(vertical, t.getInterval(), startingColor.get(0),
            startingColor.get(1), startingColor.get(2), endingcolor.get(0), endingcolor.get(1),
            endingcolor.get(2));
    colorChanges.add(horiColor);
    colorChanges.add(vertColor);
    return colorChanges;
  }

  //gets the 4 total transformations necessary on the 2 rects for the svg.
  // also adds moves in order to keep 2 rects in plus shape form.
  private ArrayList<Transformation> getSizeChangesForPlusRects(IShape horizontal, IShape vertical,
                                                               ChangeSize t) {
    ArrayList<Transformation> sizesAndMoves = new ArrayList<>();
    Transformation horiSize = new ChangeSize(horizontal, t.getInterval(),
            new Size(t.getBegSize().getWidth(),
                    (t.getBegSize().getHeight() / 2)),
            new Size(t.getEndSize().getWidth(),
                    (t.getEndSize().getHeight() / 2)));
    Transformation vertSize = new ChangeSize(vertical, t.getInterval(),
            new Size(t.getBegSize().getWidth() / 2,
                    (t.getBegSize().getHeight())),
            new Size(t.getEndSize().getWidth() / 2,
                    (t.getEndSize().getHeight())));
    //need to add moves as well in order to keep 2 rectangles in plus form
    Coordinate topLeftPlus = this.getShapeAtTick(t.getInterval()
            .getStarting()).getLocation();
    Coordinate horiStartingCoord = new Coordinate(topLeftPlus.getX(),
            (int) (topLeftPlus.getY() + (t.getBegSize().getHeight() / 4)));
    Coordinate vertStartingCoord = new Coordinate((int) (topLeftPlus.getX()
            + (t.getBegSize().getHeight() / 4)),
            topLeftPlus.getY());
    Coordinate horiEndingCoord = new Coordinate(topLeftPlus.getX(),
            (int) (topLeftPlus.getY() + (t.getEndSize().getHeight() / 4)));
    Coordinate vertEndingCoord = new Coordinate((int) (topLeftPlus.getX()
            + (t.getEndSize().getHeight() / 4)), topLeftPlus.getY());
    Transformation horiMove = new Move(horizontal, t.getInterval(), horiStartingCoord,
            horiEndingCoord);
    Transformation vertMove = new Move(vertical, t.getInterval(), vertStartingCoord,
            vertEndingCoord);
    sizesAndMoves.add(horiSize);
    sizesAndMoves.add(vertSize);
    sizesAndMoves.add(horiMove);
    sizesAndMoves.add(vertMove);
    return sizesAndMoves;
  }


  /**
   * Overrides the toString method on a shape in order to output a string that describes all of the
   * information about the state of an oval.
   *
   * @return
   */
  @Override
  public String toString() {
    return "Plus " + this.name + " x:" + this.position.getX() + " y:" + this.position.getY()
            + " w:" +
            this.size.getWidth() + " h:" + this.size.getHeight() + " r:" +
            this.color.getRed() + " g:" + this.color.getGreen() + " b:" +
            this.color.getBlue();
  }
}
