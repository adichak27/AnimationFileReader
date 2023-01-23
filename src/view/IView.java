package view;



import java.util.ArrayList;

import controller.InteractiveControllerFeatures;


/**
 * This represents the view used in our design. As of now there are a total of three views. It
 * also renders the model and displays it.
 */
public interface IView {

  /**
   * Renders the state of the animator model at a given tick, or all at once, depending on the type
   * of view.
   * @param tick the tick that the view will render the animator at. Only matters for visual
   *             and interactive views.
   */
  public void render(int tick);

  /**
   * gets the tempo that this view is using.
   * @return the tempo as an integer
   */
  public double getTempo();

  /**
   * This method will write a file depending on the type of view (either a txt
   * file or a svg file).
   * @param fileName name of the txt/svg file that will be written
   */
  public void writeOut(String fileName);

  /**
   * This method will set the JPanel and JFrame visibility to true. This funciton is only supported
   * in the VisualView class.
   */
  public void makeVisible();



  /**
   * Gets the string output of the view. If the view has been rendered, then the output
   * will contain either a textual or svg description for the animator.
   * @return a string representing the view output
   */
  public String getOutput();

  /**
   * gets the last tick of this view by using the model.
   * @return the last tick that this view will be animated on.
   */
  public int getFinalTick();

  /**
   * flips the looping setting in this Interactive view. If the variable is true
   * the loop will be set to false, and the animation will stop after it is done. If
   * the loop was false, the opposite happens.
   */


  //these methods were added to the IView interface so that the Interactive view can use these
  //methods. Will only be implemented in the Interactive View, will throw unsupported argument for
  // the rest of the views.

  public void flipLoop();





  /**
   * Returns the value for the looping field. If the view should loop, returns true.
   * This will be used in controller to restart animation if it should.
   * @return if the View should loop or not.
   */
  public boolean getLooping();


  /**
   * Sets the controller of this IView to be the InteractiveControllerFeatures object that
   * was passed in. Only operates on InteractiveView, the rest it will throw a
   * unsupportedOperationException.
   * @param controller The controller that his view is being set to hold.
   */
  public void setInteractiveController(InteractiveControllerFeatures controller);


  /**
   * Gathers every tick where a transformation either starts or ends. After getting all ticks
   * the method sorts the arraylist in ascending order, and then all duplicates are removed from
   * the list.
   * @return a list of all starting and ending ticks of transformations, with duplicates removed
   */
  public ArrayList<Integer> gatherAllDiscreteFrames();

  /**
   * Gets the discrete boolean from this view.
   * @return the boolean value for the discrete field.
   */
  public boolean getDiscrete();


  /**
   * flips the discrete field boolean to the opposite of whatever it was.
   */
  public void flipDiscrete();
}
