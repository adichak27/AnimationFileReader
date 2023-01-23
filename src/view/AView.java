package view;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.InteractiveControllerFeatures;
import model.Animator;


/**
 * Represents an Abstract Class that implements IView, and holds fields of a model,
 * tempo, and output. Abstracts the views functionality that is common between all views. Also
 * extends JFrame for the Visual and Interactive Views.
 */
abstract public class AView extends JFrame implements IView {
  protected final Animator model;
  protected final double tempo;
  protected final StringBuilder output;


  /**
   * This serves as the constructor for AView.
   * @param model the Animator model.
   * @param tempo represents the ticker per second.
   */
  public AView(Animator model, double tempo) {
    if (model == null || tempo < 0) {
      throw new IllegalArgumentException("model cannot be null and temp can't be negative");
    } else {
      this.model = model;
      this.tempo = tempo;
      this.output = new StringBuilder();
    }
  }


  abstract public void render(int tick);

  public double getTempo() {
    return this.tempo;
  }


  public String getOutput() {
    return output.toString();
  }

  /**
   * This method will write a file depending on the type of view (either a txt
   * file or a svg file).
   * @param fileName name of the txt/svg file that will be written
   */
  public void writeOut(String fileName) {
    //ouputs to system if no output is given
    if (fileName == null) {
      System.out.print(output.toString());
      return;
    }
    String outputView = output.toString();

    try {
      FileWriter fileWriter = new FileWriter(fileName);
      fileWriter.write(outputView);
      fileWriter.close();
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Error writing to file");
    }
  }

  @Override
  //only interactive view will be able to do this.
  public void flipLoop() {
    throw new UnsupportedOperationException("This view cannot perform this operation");
  }

  @Override
  public boolean getLooping() {
    throw new UnsupportedOperationException("This view cannot perform this operation");
  }


  //added during assignment 6 for easy access to the final tick.
  @Override
  public int getFinalTick() {
    return model.getLastTick();
  }

  @Override
  public void setInteractiveController(InteractiveControllerFeatures controller) {
    throw new UnsupportedOperationException("This view cannot set a controller");
  }

  @Override
  public ArrayList<Integer> gatherAllDiscreteFrames() {
    throw new UnsupportedOperationException("This view cannot gather discrete frames");
  }

  @Override
  public boolean getDiscrete() {
    throw new UnsupportedOperationException("This view cannot perform this operation");
  }

  public void flipDiscrete() {
    throw new UnsupportedOperationException("This view cannot perform this operation");
  }
}
