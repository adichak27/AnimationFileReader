package model;


import java.io.FileNotFoundException;


import javax.swing.JFrame;
import javax.swing.JOptionPane;


import controller.FactoryController;
import controller.IController;
import view.FactoryView;
import view.IView;


/**
 * This class is what runs our main method and accepts the command line arguements. It uses the view
 * and controller to produce a txt or svg output depending on the user input.
 */
public class AnimatorMain {


  /**
   * This is the main method that we run for our animator. It reads the command line arguements and
   * outputs a either a txt file or svg file based on the run configurations.
   */
  public static void main(String[] args) {
    String viewType = null;
    Animator model = null;
    double tempo = 1.0;
    String output = null;
    JFrame frame = new JFrame();
    //read the command arguments, if there is an incorrect command argument
    //exits main
    //for each before the last one so that never runs its error of not having argument
    for (int i = 0; i < (args.length - 1); i = i + 1) {
      if (args[i].equals("-in")) {
        try {
          String file = args[i + 1];
          AnimationFileReader reader = new AnimationFileReader();
          TweenModelBuilder<Animator> builder = new BuilderImpl();
          model = reader.readFile(file,
                  builder);
        } catch (FileNotFoundException fnfe) {
          JOptionPane.showMessageDialog(frame,
                  "No file was found", "input warning",
                  JOptionPane.WARNING_MESSAGE);
          return;
        } catch (IllegalArgumentException iae) {
          JOptionPane.showMessageDialog(frame, iae.getMessage(), "Model warning",
                  JOptionPane.WARNING_MESSAGE);
        }

      }

      if (args[i].equals("-view")) {
        try {
          viewType = args[i + 1];
        } catch (IllegalArgumentException iae) {
          JOptionPane.showMessageDialog(frame, "no view type was found",
                  "view warning", JOptionPane.WARNING_MESSAGE);
          return;
        }
      }

      if (args[i].equals("-speed")) {
        try {
          tempo = Integer.valueOf(args[i + 1]);
        } catch (NumberFormatException nfe) {
          JOptionPane.showMessageDialog(frame, "Incorrect speed given, defaults to 1",
                  "speed warning", JOptionPane.WARNING_MESSAGE);
        }
      }

      if (args[i].equals("-out")) {
        try {
          output = args[i + 1];
        } catch (IllegalArgumentException iae) {
          JOptionPane.showMessageDialog(frame, " no correct output is given," +
                  "defaults to system out", "output warning", JOptionPane.WARNING_MESSAGE);
        }
      }
    }
    if (model == null || viewType == null) {
      JOptionPane.showMessageDialog(frame, "invalid commands, model or viewtype null",
              "warning", JOptionPane.WARNING_MESSAGE);
      return;
    }
    // should probably stop here if the command inputs were not chill
    try {
      IView view = new FactoryView().createView(viewType, model, tempo);
      IController controller = new FactoryController().createController(viewType, view, output);
      controller.run();
    } catch (IllegalArgumentException iae) {
      JOptionPane.showMessageDialog(frame, iae.getMessage(),
              "Error", JOptionPane.WARNING_MESSAGE);
      return;
    }
  }
}


