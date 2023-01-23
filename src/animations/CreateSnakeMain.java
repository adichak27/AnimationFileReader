package animations;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class that creates the textual description for our snake animation. The body of the snake is
 * 5 circles long and crawls along the screen from left to right as it makes its way down.
 */
public class CreateSnakeMain {

  /**
   * Constructs the textual description of the snake Animation.
   * @param args The command line arguments for the main. Not necessary for this.
   */
  public static void main(String[] args) {

    StringBuilder output = new StringBuilder();
    output.append("canvas 800 800\n");

    int snakeLength = 5;
    createSnakes(output, snakeLength);

    for (int i = 0; i < snakeLength; i++) {
      createTransformation(output, i * 40, "o" + i, i);
    }

    String outputView = output.toString();

    try {
      FileWriter fileWriter = new FileWriter("SnakeMain.txt");
      fileWriter.write(outputView);
      fileWriter.close();
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Error writing to file");
    }
  }

  private static void createSnakes(StringBuilder string, int snakeLength) {
    //five ovals is the snake length
    int tailX = 0;
    int tailY = 0;
    for (int i = 0; i < snakeLength; i = i + 1) {
      string.append("oval name o" + i + " center-x " + tailX + " center-y " + tailY +
              " x-radius 20 " + "y-radius 20" +
              " color .7 .6 .5 from 0 to 90\n");
      //changes location for center of next part of snake body
      tailX += 40;
    }
  }


  private static void createTransformation(StringBuilder string, int startX, String name,
                                           int delay) {
    string.append("move name " + name + " moveto " + startX + " " + 0 +
            " " + 800 + " 0 from " + (0 + delay) + " to " + (9 + delay) + "\n");
    moveDown(string, name, 800, 0, 9 + delay, 10 + delay);
    for (int i = 0; i < 7; i = i + 1) {
      int startTime = (i + 1) * 10;
      if ( i % 2 == 0) {
        // (i + 1) * 10
        moveLeft(string, name, (i + 1) * 100, startTime + delay, startTime + 9 + delay);
        moveDown(string, name, 0, (i + 1) * 100, startTime + 9 + delay,
                startTime + 10 + delay);
      } else {
        moveRight(string, name, (i + 1) * 100, startTime + delay, startTime + 9 + delay);
        moveDown(string, name, 800, (i + 1) * 100, startTime + 9 + delay,
                startTime + 10 + delay);
      }
    }
  }


  private static void moveRight(StringBuilder string, String name, int y, int from, int to) {
    string.append("move name " + name + " moveto 0 " + y + " 800 " + y + " from " +
            from + " to " + to
            + "\n");
  }


  private static void moveLeft(StringBuilder string, String name, int y, int from, int to) {
    string.append("move name " + name + " moveto 800 " +  y + " 0 " + y + " from "  + from + " to "
            + to + "\n");
  }
  
  private static void moveDown(StringBuilder string, String name, int x, int y, int from, int to) {
    string.append("move name " + name + " moveto " + x + " " + y + " " +
            x + " " + (y + 100) + " from " + from + " to " + to
            + "\n");
  }
}




