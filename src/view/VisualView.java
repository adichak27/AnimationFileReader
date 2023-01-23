package view;

import java.util.ArrayList;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JFrame;


import model.Animator;
import model.IShape;

/**
 * The visual view displays the animation to the user in the JFrame. Will render the animation
 * given the tick, and when used with controller, plays the whole animation from start to finish.
 */
public class VisualView extends AView {
  protected final AnimatorPanel panel;

  /**
   * constructs a visual view. Sets  panel to a new animator panel.
   * @param model the model that the view is using
   * @param tempo the ticks per seocnd.
   */
  public VisualView(Animator model, double tempo) {
    super(model, tempo);
    this.panel = new AnimatorPanel();

    panel.setPreferredSize(new Dimension(model.getCanvasDimensions()[2],
            model.getCanvasDimensions()[3]));
    JScrollPane pane = new JScrollPane(panel);
    add(pane);
    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();


  }


  public void makeVisible() {
    this.setVisible(true);
    panel.setVisible(true);
  }

  @Override
  public void render(int tick) {
    panel.drawView((ArrayList<IShape>) model.getShapesAtTick(tick));
    System.out.print(tick);
  }


  @Override
  public void writeOut(String fileName) {
    throw new UnsupportedOperationException("No write out for visual view");
  }

}

