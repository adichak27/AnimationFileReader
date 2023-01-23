package controller;

import view.IView;
import view.SVGView;
import view.TextView;

/**
 * class for Both the Text Controller and the
 * SVG Controller that implements IController and holds either an SVG or a
 * Text view as well as the name
 * of the output file that the animation will be written to.
 */
public class TextualController implements IController {
  private final IView view;
  private final String output;

  /**
   * Constructs a Textual controller, throws exception if view is null, but not output.
   * @param view the view that is being rendered with this Textual Controller. View is an SVG View
   *             or a Text view
   * @param output the name of the file that the animation will be written to, if null then
   *               the animation will be written to the system.
   */
  public TextualController(IView view, String output) {
    if (view == null) {
      throw new IllegalArgumentException("view cannot be null");
    }
    if ((! (view instanceof TextView)) && (! (view instanceof SVGView))) {
      throw new IllegalArgumentException("view must be either SVG or Text");
    }
    this.view = view;
    //fine if output is null, will then write to the system
    this.output = output;
  }

  @Override
  public void run() {
    view.render(1);
    view.writeOut(output);
  }
}
