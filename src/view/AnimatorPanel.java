package view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JPanel;

import model.IShape;
import model.Oval;
import model.Plus;
import model.Rectangle;

/**
 * Used to draw and display shapes for visual view. Extends the JPanel class
 */
public class AnimatorPanel extends JPanel {
  private  ArrayList<IShape> shapes;
  //added for assignment 8
  private boolean outline;


  /**
   * Constructs an Animator panel and sets the shapes field to null.
   */
  public AnimatorPanel() {
    this.shapes = null;
    //outline initialized to false to start.
    outline = false;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //affine transformation???
    //maybe set background
    if (shapes != null) {
      Graphics2D graphics = (Graphics2D) g;

      for (IShape s : shapes) {
        int x = s.getLocation().getX();
        int y = s.getLocation().getY();
        int width =  (int) Math.round(s.getWidth());
        int height = (int) Math.round(s.getHeight());
        Color c = s.getColor();
        if (s instanceof Rectangle) {
          graphics.setColor(c);
          graphics.drawRect(x, y, width, height);
          if (! outline) {
            graphics.fillRect(x, y, width, height);
          }
        } else if (s instanceof Oval) {
          graphics.setColor(c);
          graphics.drawOval(x, y, width, height);
          if (! outline) {
            graphics.fillOval(x, y, width, height);
          }
        } else if (s instanceof Plus) {
          graphics.setColor(c);
          int fourthHeight = height / 4;
          if (! outline) {
            graphics.drawRect(x, y + fourthHeight, width, height / 2);
            graphics.drawRect(x + fourthHeight, y, width / 2, height);
            graphics.fillRect(x, y + fourthHeight, width, height / 2);
            graphics.fillRect(x + (width / 4), y, width / 2, height);
          } else {
            //outline for plus required drawing individual lines
            graphics.drawLine(x, y + fourthHeight, x + fourthHeight, y + fourthHeight);
            graphics.drawLine(x + fourthHeight, y + fourthHeight, x + fourthHeight,
                    y);
            graphics.drawLine(x + fourthHeight, y,
                    x + (height - fourthHeight), y);
            graphics.drawLine(x + (height - fourthHeight), y, x + (height - fourthHeight),
                    y + (fourthHeight));
            graphics.drawLine(x + (height - fourthHeight), y + (fourthHeight),
                    x + height, y + (fourthHeight));
            graphics.drawLine(x + height, y + (fourthHeight),x + height,
                    y + (height - fourthHeight));
            graphics.drawLine(x + height,
                    y + (height - fourthHeight), x + (height - fourthHeight),
                    y + (height - fourthHeight));
            graphics.drawLine(x + (height - fourthHeight),
                    y + (height - fourthHeight),
                    x + (height - fourthHeight),
                    y + (height));
            graphics.drawLine(x + (height - fourthHeight),
                    y + (height),
                    x + (fourthHeight), y + height);
            graphics.drawLine(x + (fourthHeight), y + height,
                    x + fourthHeight, y + ( height - fourthHeight));
            graphics.drawLine(x + fourthHeight, y + ( height - fourthHeight),
                    x, y + (height - fourthHeight));
            graphics.drawLine(x, y + (height - fourthHeight), x, y + (fourthHeight));

          }
        }
      }
    }
  }

  /**
   * sets the shapes in this panel to the list of shapes given, and then repaints
   * the panel using those shapes.
   * @param shapes the shapes that will be painted.
   */
  public void drawView(ArrayList<IShape> shapes) {
    this.shapes = shapes;
    repaint();
  }

  /**
   * Changes the outline field in this panel to not whatever it was.
   */
  public void flipOutline() {
    outline = !outline;
  }
}
