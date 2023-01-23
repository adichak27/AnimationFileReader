package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;


import controller.InteractiveControllerFeatures;
import model.Animator;
import model.IShape;
import model.Interval;
import model.Transformation;

/**
 * Interactive View represents a new type of view. This view contains buttons that when pressed
 * will allow for additional functionality, such as pausing, playing, speeding up, slowing down,
 * restarting and looping the animation. With assingment 8 we also added the functionality to
 * show animation as outline, or play the animation discretely.
 */
public class InteractiveView extends VisualView implements ActionListener {
  private boolean looping;
  private boolean discrete;
  private InteractiveControllerFeatures controller;
  private JButton startButton;
  private JButton playButton;
  private JButton pauseButton;
  private JButton restartButton;
  private JButton increaseSpeedButton;
  private JButton decreaseSpeedButton;
  private JButton loopButton;
  private JButton outlineButton;
  private JButton discreteButton;


  /**
   * Constructs an Interactive view using the Visual Views super constructor. Also sets looping
   * field to true, and configures the button panel.
   *
   * @param model the model for this view.
   * @param tempo the tempo this animation will play with.
   */
  public InteractiveView(Animator model, double tempo) {
    //super constructor uses visual view to set panel preferences. Panel
    //field is held in visual view.
    super(model, tempo);
    //default set looping to true
    looping = true;
    //discrete will default to false.
    discrete = false;
    setButtonPanel();
    this.pack();

  }


  /**
   * Initializes and sets all of the necessary buttons and their action commands.
   */
  private void setButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    startButton = new JButton("START");
    buttonPanel.add(startButton);

    playButton = new JButton("RESUME");
    buttonPanel.add(playButton);

    pauseButton = new JButton("PAUSE");
    buttonPanel.add(pauseButton);

    increaseSpeedButton = new JButton("INCREASE SPEED");
    buttonPanel.add(increaseSpeedButton);

    decreaseSpeedButton = new JButton("DECREASE SPEED");
    buttonPanel.add(decreaseSpeedButton);


    restartButton = new JButton("RESTART");
    buttonPanel.add(restartButton);

    loopButton = new JButton("LOOP");
    buttonPanel.add(loopButton);

    //last 2 buttons added for assignment 8
    outlineButton = new JButton("OUTLINE");
    buttonPanel.add(outlineButton);

    discreteButton = new JButton("PLAY DISCRETE");
    buttonPanel.add(discreteButton);

    startButton.setActionCommand("START");
    playButton.setActionCommand("RESUME");
    pauseButton.setActionCommand("PAUSE");
    increaseSpeedButton.setActionCommand("INCREASE SPEED");
    decreaseSpeedButton.setActionCommand("DECREASE SPEED");
    restartButton.setActionCommand("RESTART");
    loopButton.setActionCommand("LOOP");
    outlineButton.setActionCommand("OUTLINE");
    discreteButton.setActionCommand("PLAY DISCRETE");

    this.add(buttonPanel, BorderLayout.SOUTH);
    setButtonListeners();


  }

  @Override
  public void flipLoop() {
    looping = !looping;
  }

  // called in setButtonPanel to set buttons listeners to this view.
  private void setButtonListeners() {
    startButton.addActionListener(this);
    playButton.addActionListener(this);
    pauseButton.addActionListener(this);
    increaseSpeedButton.addActionListener(this);
    decreaseSpeedButton.addActionListener(this);
    restartButton.addActionListener(this);
    loopButton.addActionListener(this);
    //added assingment 8
    outlineButton.addActionListener(this);
    discreteButton.addActionListener(this);
  }

  @Override
  public boolean getLooping() {
    return looping;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "START":
        controller.start();
        break;
      case "RESUME":
        controller.play();
        break;
      case "PAUSE":
        controller.pause();
        break;
      case "RESTART":
        controller.restart();
        break;
      case "INCREASE SPEED":
        controller.increaseSpeed();
        break;
      case "DECREASE SPEED":
        controller.decreaseSpeed();
        break;
      case "LOOP":
        controller.loop();
        break;
        //added these 2 buttons for assingment 8
      case "OUTLINE":
        panel.flipOutline();
        break;
      case "PLAY DISCRETE":
        controller.discrete();
        break;
      default:
        throw new IllegalArgumentException("Should never happen");
    }
  }

  @Override
  public void setInteractiveController(InteractiveControllerFeatures controller) {
    if (controller == null) {
      throw new IllegalArgumentException("cannot set to null controller");
    }
    this.controller = controller;
  }

  @Override
  public ArrayList<Integer> gatherAllDiscreteFrames() {
    List<IShape> shapes = model.getShapes();
    ArrayList<Integer> allTicks = new ArrayList<Integer>();
    for (IShape shape : shapes) {
      ArrayList<Transformation> transformations = shape.getAllTransformations();
      for (Transformation transfo : transformations) {
        Interval interval = transfo.getInterval();
        allTicks.add(interval.getStarting());
        allTicks.add(interval.getEnding());
      }
    }
    Collections.sort(allTicks);
    return removeDuplicates(allTicks);
  }

  //helper to remove duplicates from the list of discrete frames.
  private ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
    ArrayList<Integer> noDupes = new ArrayList<>();
    for (Integer i: list) {
      if (! noDupes.contains(i)) {
        noDupes.add(i);
      }
    }
    return noDupes;
  }

  @Override
  public void flipDiscrete() {
    discrete = ! discrete;
  }

  @Override
  public boolean getDiscrete() {
    return discrete;
  }
}
