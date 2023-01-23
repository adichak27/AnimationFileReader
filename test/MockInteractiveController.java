import controller.InteractiveControllerFeatures;
import view.InteractiveView;

/**
 * Mock interactive controller for testing purposes. Holds a log that keeps track of what
 * is done.
 */
public class MockInteractiveController  implements InteractiveControllerFeatures {
  StringBuilder log;
  InteractiveView view;

  /**
   * Creates a mock controller that holds a view and a log that keeps track of what methods are
   * called.
   * @param view the View that this controller would render.
   */
  public MockInteractiveController(InteractiveView view) {
    log = new StringBuilder();
    this.view = view;
    view.setInteractiveController(this);
  }

  @Override
  public void run() {
    log.append("controller is ran");
  }

  @Override
  public void start() {
    log.append("Controllers timer and view are started");
  }

  @Override
  public void play() {
    log.append("Animation is played");
  }

  @Override
  public void pause() {
    log.append("Animation is paused");
  }

  @Override
  public void restart() {
    log.append("Animation is restarted");
  }

  @Override
  public void increaseSpeed() {
    log.append("Speed is increased");
  }

  @Override
  public void decreaseSpeed() {
    log.append("Speed is decreased");
  }

  @Override
  public void loop() {
    log.append("Views loop is flipped");
  }

  @Override
  public void discrete() {
    log.append("Views discrete field is flipped");
  }

  /**
   * returns the log of this mock, for testing.
   * @return the log of this mock.
   */
  public StringBuilder returnLog() {
    return log;
  }
}
