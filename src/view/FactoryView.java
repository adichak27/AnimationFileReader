package view;

import model.Animator;

/**
 * Used to create a view given string inputs and model.
 */
public class FactoryView {

  /**
   * Constructor for factory view which returns a view given a string type of the view.
   * @param type type of view to return
   * @param model the easy animator model being attached to the view
   * @param tempo the ticks per second
   * @return a View based on the string type of input
   */
  public IView createView(String type, Animator model, double tempo) {
    switch (type) {
      case "visual":
        return new VisualView(model, tempo);
      case "text":
        return new TextView(model, tempo);
      case "svg":
        return new SVGView(model, tempo);
        //added Assignment 6 for new type of view
      case "interactive":
        return new InteractiveView(model, tempo);
      default: throw new IllegalArgumentException("This is not a valid view type.");
    }

  }
}
