package view;

import model.Animator;
import model.IShape;

/**
 * This class represents on of our three views. It allows the user to view the model in an svg
 * format.
 */
public class SVGView extends AView {


  /**
   * Constructs the svg view with the given inputs so the user can see animation in svg format.
   * @param model the animator model
   * @param tempo the ticks per second
   */
  public SVGView(Animator model, double tempo) {
    super(model, tempo);
  }


  @Override
  public void render(int tick) {
    //renders the canvas screen
    output.append("<svg width=\"" + model.getCanvasDimensions()[2] +  "\" height=\""
            + model.getCanvasDimensions()[3] + "\" version = \"1.1\" \n"
            + "   xmlns=\"http://www.w3.org/2000/svg\">\n");
    for (IShape shape : model.getShapes()) {
      output.append(shape.svgTagForShape(tempo));
    }
    output.append("</svg>");
  }

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("this view does not become visible");

  }
}
