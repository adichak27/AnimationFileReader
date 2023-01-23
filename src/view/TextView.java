package view;


import java.util.ArrayList;
import java.util.List;

import model.Animator;
import model.IShape;
import model.Transformation;

/**
 * This class represents a text view and allows the user to see the animation as a txt file.
 */
public class TextView extends AView {
  public TextView(Animator model, double tempo) {
    super(model, tempo);
  }


  //pass tick
  @Override
  public void render(int tick) {
    List<IShape> shapes = model.getShapes();
    for (IShape s : shapes) {
      String createString = "Create: " + s.toString() + "; Appears at time " +
              tickToSecond(s.getStartOfLife()) + "\n";
      output.append(createString);
      s.getAllTransformations();
      ArrayList<Transformation> transformations = s.getAllTransformations();
      for (Transformation t : transformations) {
        output.append("From second " + tickToSecond(t.getInterval().getStarting()) + " to " +
                tickToSecond(t.getInterval().getEnding()) + ", ");
        output.append(t.toString() + "\n");
      }
      output.append("Disappears at time " + tickToSecond(s.getEndOfLife()));
      output.append("\n");

    }
  }

  @Override
  public double getTempo() {
    return this.tempo;
  }


  /**
   * converts a given tick to its equal second, depending on the tempo of this view.
   *
   * @param tick the tick that is being converted to seconds
   * @return the given tick in seconds.
   */
  //goes in controller if tempo is in controller and not here
  private double tickToSecond(int tick) {
    return (double) tick / tempo;
  }


  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("cannot make text view visible");
  }

}
