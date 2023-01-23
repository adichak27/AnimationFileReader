package controller;


import java.util.ArrayList;

import javax.swing.Timer;

import model.AnimatorListener;
import model.Interval;
import view.IView;
import view.InteractiveView;

/**
 * Class for the Interactive Controller that implements IController as well as ActionListener.
 * this Controller implements action listener in order to be used as such for the Interactive views
 * buttons. Also has a timer to render the animation. Introduces additional functionality to the
 * Visual view, including playing, pausing, increasing and decreasing the speed of animation, as
 * well as setting the animation to restart or loop.
 */
public class InteractiveController implements InteractiveControllerFeatures {
  //should this be taking in an IView
  private final IView view;
  private Timer timer;
  /**
   * Constructor for the InteractiveController that uses specifically an InteractiveView view.
   * Throws error if the View is null. Also Initializes a new timer using the views tempo.
   * @param view the Interactive View used in this controller to render the animation.
   */
  public InteractiveController(IView view) {
    if (view == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }

    if (! (view instanceof InteractiveView)) {
      throw new IllegalArgumentException("view must be interactive");
    }


    this.view = view;
    //this makes sure that the interactive views controller is not null when ran.
    view.setInteractiveController(this);
    this.timer = new Timer((int) Math.round((1 / view.getTempo()) * 1000), null);
  }

  //constructor for when timer is passed in
  public InteractiveController(IView view, Timer timer) {
    this(view);
    this.timer = timer;
  }


  @Override
  public void run() {
    view.makeVisible();
    AnimatorListener listener = new AnimatorListener(timer, view);
    timer.addActionListener(listener);
  }




  //resets the timers listeners tick, and starts the timer.
  private void restartTimer() {
    AnimatorListener listener = (AnimatorListener) timer.getActionListeners()[0];
    listener.resetTick();
    timer.start();
  }

  @Override
  public void start() {
    timer.start();
  }

  @Override
  public void play() {
    if (!timer.isRunning()) {
      timer.start();
    }
  }

  @Override
  public void pause() {
    if (timer.isRunning()) {
      timer.stop();
    }
  }

  @Override
  public void restart() {
    timer.stop();
    restartTimer();
  }

  @Override
  public void increaseSpeed() {
    // if increasing the speed any more will cause the animation to end instantaneously, then
    // speed cannot be increased.
    if (timer.getDelay() > 50) {
      timer.setDelay(timer.getDelay() - 50);
    }
  }

  @Override
  public void decreaseSpeed() {
    //can be decreased as much as wanted.
    timer.setDelay((timer.getDelay() + 50));
  }

  @Override
  public void loop() {
    view.flipLoop();
  }


  @Override
  public void discrete() {
    view.flipDiscrete();
    restart();
  }
}
