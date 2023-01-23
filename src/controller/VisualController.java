package controller;

import javax.swing.Timer;

import model.AnimatorListener;
import view.IView;
import view.InteractiveView;
import view.VisualView;


/**
 * class for Visual Controller that implements IController and holds both a visual View and a timer,
 * the timer is used to render the animation.
 */
public class VisualController implements IController {
  private final IView view;
  private Timer timer;


  /**
   * Constructor for a Visual Controller. Passed in an IView that cannot be null, and will be an
   * instance of a Visual View. Initializes a new timer using the views tempo, and a null listener.
   * @param view the visual view that will be used to render the animation.
   */
  public VisualController(IView view) {
    if (view == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    if (! (view instanceof VisualView) || (view instanceof InteractiveView)) {
      throw new IllegalArgumentException("View cannot be anything but visual");
    }
    this.view = view;
    //delay is 1/ tempo * 1000
    this.timer = new Timer((int) Math.round((1 / view.getTempo()) * 1000), null);
  }

  @Override
  public void run() {
    view.makeVisible();
    AnimatorListener listener = new AnimatorListener(timer, view);
    timer.addActionListener(listener);
    timer.start();

  }
}
