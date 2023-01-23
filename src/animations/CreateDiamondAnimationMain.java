package animations;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class that creates the textual description for our Diamond animation. A Rectangle is
 * surrounded by 4 ovals that rotate around it for one full rotation.
 */
public class CreateDiamondAnimationMain {


  /**
   * Constructs the textual description of the Diaomond Animation.
   * @param args The command line arguments for the main. Not necessary for this.
   */
  public static void main(String[] args) {
    StringBuilder output = new StringBuilder();
    makeCircleAnimation(output);
    makeTransformations(output);
    String outputView = output.toString();

    try {
      FileWriter fileWriter = new FileWriter("DiamondAnimation.txt");
      fileWriter.write(outputView);
      fileWriter.close();
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Error writing to file");
    }
  }



  //writes out all of the shapes for our animation


  private static void makeCircleAnimation(StringBuilder string) {
    string.append("canvas 800 800\n");
    string.append("rectangle name r0 min-x 415 min-y 415 width 10 height 10 color .3 .3 .3 " +
            "from 1 to 40\n");
    string.append("oval name o1 center-x 300 center-y 400 x-radius 40 y-radius 40 color 0 .1 .2 " +
            "from 1 to 40\n");
    string.append("oval name o2 center-x 500 center-y 400 x-radius 40 y-radius 40 color .9 .1 .2 " +
            "from 1 to 40\n");
    string.append("oval name o3 center-x 400 center-y 300 x-radius 40 y-radius 40 color .2 0 .5 " +
            "from 1 to 40\n");
    string.append("oval name o4 center-x 400 center-y 500 x-radius 40 y-radius 40 color .1 .3 .9 " +
            "from 1 to 40\n");
    //for (int i = 0; i < 5; i = i + 1) {
  }



  //Writes out the transformations for this animation.
  private static void makeTransformations(StringBuilder string) {

    string.append("move name o1 moveto 300 400 400 500 from 1 to 10\n");
    string.append("move name o1 moveto 400 500 500 400 from 10 to 20\n");
    string.append("move name o1 moveto 500 400 400 300 from 20 to 30\n");
    string.append("move name o1 moveto 400 300 300 400 from 30 to 40\n");

    string.append("move name o2 moveto 500 400 400 300 from 1 to 10\n");
    string.append("move name o2 moveto 400 300 300 400 from 10 to 20\n");
    string.append("move name o2 moveto 300 400 400 500 from 20 to 30\n");
    string.append("move name o2 moveto 400 500 500 400 from 30 to 40\n");

    string.append("move name o3 moveto 400 300 300 400 from 1 to 10\n");
    string.append("move name o3 moveto 300 400 400 500 from 10 to 20\n");
    string.append("move name o3 moveto 400 500 500 400 from 20 to 30\n");
    string.append("move name o3 moveto 500 400 400 300 from 30 to 40\n");

    string.append("move name o4 moveto 400 500 500 400 from 1 to 10\n");
    string.append("move name o4 moveto 500 400 400 300 from 10 to 20\n");
    string.append("move name o4 moveto 400 300 300 400 from 20 to 30\n");
    string.append("move name o4 moveto 300 400 400 500 from 30 to 40\n");
  }
}
