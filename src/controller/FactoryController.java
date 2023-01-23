package controller;

import view.IView;

/**
 * Factory class with method to create a new Controller using a view type and a view.
 */
public class FactoryController {


  /**
   * creates a controller Using the specified inputs.
   * @param type The type of both view that should be passed in, as well as controller that should
   *             be created.
   * @param view The view that is passed in to be stored in the controller that is created. Should
   *             correspond to the view type passed in.
   * @param output The name of the output file that should be written to. If null, the text and
   *               svg views will write to system. Output is not used by visual or
   *               interactive views.
   *
   * @return The correct controller depending on the inputs of the viewtype and view.n
   */
  public IController createController(String type, IView view, String output) {
    if ((type == null) || (view == null)) {
      throw new IllegalArgumentException("Cannot have null parameters");
    }
    switch (type) {
      case "text":
      case "svg":
        //both text and svg views will use a textual controller
        return new TextualController(view, output);
      case "visual":
        return new VisualController(view);
      case "interactive":
        return new InteractiveController(view);
      default: throw new IllegalArgumentException("Invalid Controller Type");
    }
  }
}
