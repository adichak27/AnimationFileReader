package controller;

import java.util.ArrayList;

import model.Interval;

/**
 * This interface holds all of the functionality offered by the Interactive view, that will be
 * executed on the controller. The methods correspond to the buttons that are held in the
 * Interactive Views button panel.
 */
public interface InteractiveControllerFeatures extends IController {


  /**
   * starts the animation when called.
   */

  public void start();

  /**
   * plays the animation when called. Only does anything if the animation isnt already playing.
   */
  public void play();

  /**
   * pauses the animation. Only does anything if the animation is playing.
   */
  public void pause();

  /**
   * Restarts the animation from the first tick by resetting timer in this controller.
   */
  public void restart();

  /**
   * Increase the speed of the animation by a certain amount of milliseconds every time this
   * method is called.
   */

  public void increaseSpeed();

  /**
   * decreases the speed of the animation by a certain amount of milliseconds every time this method
   * is called.
   */
  public void decreaseSpeed();

  /**
   * Flips the interactive views looping field when called. If the looping field is true it will set
   * it to false and vice versa.
   */
  public void loop();

  /**
   * Flips the interactive views discrete field when called. This will in turn restart the
   * animation and play either fully or discretely.
   */
  public void discrete();
}
