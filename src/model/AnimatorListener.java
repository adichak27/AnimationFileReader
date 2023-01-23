package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;


import javax.swing.Timer;

import controller.InteractiveController;
import view.IView;
import view.InteractiveView;


/**
 * This class implements action listener and renders the view at every tick.
 */
public class AnimatorListener implements ActionListener {
  private final Timer timer;
  private int tick;
  private final IView view;
  private final ArrayList<Integer> discreteFrames;



  /**
   * This method constructor the AnimatorListener class which takes in a timer and view.
   * @param timer represents the timer
   * @param view represents one of three possible views
   */
  public AnimatorListener(Timer timer, IView view) {
    if ((timer == null) || (view == null)) {
      throw new IllegalArgumentException("Cannot have any null arguments");
    }
    this.timer = timer;
    this.view = view;
    this.tick = 0;
    if (view instanceof InteractiveView) {
      discreteFrames = view.gatherAllDiscreteFrames();
    } else discreteFrames = null;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    boolean isInteractive = view instanceof InteractiveView;
    view.render(tick);
    if (isInteractive) {
      boolean endOfDiscrete = (view.getDiscrete() &&
              (tick == discreteFrames.get(discreteFrames.size() - 1)));
      boolean endOfReg = ((! view.getDiscrete()) && (tick == view.getFinalTick()));
      //check if either regualr interactive and over or discrete and over.
      if ((endOfReg|| endOfDiscrete) && view.getLooping()) {
        resetTick();
      } else if ((endOfReg|| endOfDiscrete) && !view.getLooping()) {
        timer.stop();
      } else {
        getNextInteractiveTick();
      }
      //below for visual view
    } else {
      if (tick == view.getFinalTick()) {
        timer.stop();
      } else {
        tick++;
      }
    }
  }


  /**
   * This is a getter method which will return the tick field.
   * @return returns this tick.
   */
  public int getTick() {
    return tick;
  }

  /**
   * resets the listeners tick to 1. Used for when the animation is restarted in Interactive view.
   */
  public void resetTick() {
    if ((view instanceof InteractiveView) && view.getDiscrete()) {
      //if discretely playing, first tick should not be zero, but the starting tick of first
      // transformation.
      try {
        tick = discreteFrames.get(0);
      } catch (IndexOutOfBoundsException index) {
        throw new
                IllegalArgumentException("Cannot play discretely if there are no transformations");
      }
    } else {
      tick = 1;
    }
  }


  // helper method that finds the next tick. If the animation is playing discretely
  // it should play the next tick in the list of discrete frames. If not discretely playing
  // should just increment the tick by 1.
  private void getNextInteractiveTick() {
    if ((view instanceof InteractiveView) && view.getDiscrete()) {
      for (int i = 0; i < discreteFrames.size() - 1; i++) {
        if (discreteFrames.get(i) == tick) {
          tick = discreteFrames.get(i + 1);
          break;
        }
      }
    } else {
      tick++;
    }
  }
}
