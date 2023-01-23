import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import controller.FactoryController;
import controller.IController;
import controller.InteractiveController;
import controller.InteractiveControllerFeatures;
import controller.TextualController;
import controller.VisualController;
import model.Animator;
import model.AnimatorListener;
import model.BuilderImpl;

import model.TweenModelBuilder;
import view.IView;
import view.InteractiveView;
import view.SVGView;
import view.TextView;
import view.VisualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests written for the controller part of our design for the Animator.
 */

public class ControllerTest {
  IView txt;
  IView svg;
  IView interactive;
  IView visual;
  Animator simpleModel;
  FactoryController factory;
  MockInteractiveController mockInteractive;
  InteractiveView interactiveForMock;



  @Before
  public void setValues() {
    TweenModelBuilder<Animator> builder = new BuilderImpl();
    simpleModel = builder.setBounds(500, 500).addOval("firstC", 50, 50,
                    10, 10, .23f, 0.43f, 0.1f, 10,
                    20).
            addRectangle("firstR", 100, 100, 10, 20, .1f,
                    .5f,
                    .10f, 1, 25).addOval("secondC",190,
                    190,
                    10, 10, .3f, 0.43f, 0.1f, 10,
                    20).
            addRectangle("secondR", 21, 40, 10, 20, .1f,
                    .5f,
                    .10f, 1, 25).addMove("firstR",
                    100,
                    100, 20, 20, 2, 20)
            .addColorChange(
                    "firstR", .1f, .5f,
                    .10f, .2f, .5f,
                    .10f, 15, 23).addScaleToChange("firstC",
                    10,
                    10, 100, 100, 12, 16).build();
    txt = new TextView(simpleModel, 2);
    svg = new SVGView(simpleModel, 1);
    interactive = new InteractiveView(simpleModel, 3);
    visual =  new VisualView(simpleModel, 2);
    factory = new FactoryController();
    interactiveForMock = new InteractiveView(simpleModel, 20);
    mockInteractive = new MockInteractiveController(interactiveForMock);
  }


  //tests constructors for all views.

  @Test (expected = IllegalArgumentException.class)
  public void testTextControllerNullView() {
    new TextualController(null, "string");
  }

  //throws error not svg or text
  @Test (expected = IllegalArgumentException.class)
  public void testTextControllerWrongView() {
    new TextualController(visual,"string");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testTextControllerWrongView2() {
    new TextualController(interactive, "String");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testVisualControllerNullView() {
    new VisualController(null);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testVisualControllerInteractiveView() {
    new VisualController(interactive);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testVisualControllerWrongView2() {
    new VisualController(svg);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInteractiveControllerNullView() {
    new InteractiveController(null);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testInteractiveControllerWrongView() {
    new InteractiveController(visual);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInteractiveControllerWrongView2() {
    new InteractiveController(txt);
  }



  //tests for the factoryController create method

  @Test
  public void testCreateController() {
    assertTrue(factory.createController("visual", visual, null)
            instanceof VisualController);
    assertTrue(factory.createController("text", txt, null)
            instanceof TextualController);
    assertTrue(factory.createController("svg", svg, null)
            instanceof TextualController);
    assertTrue(factory.createController("interactive", interactive, null)
            instanceof InteractiveController);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateControllerNullView() {
    factory.createController("few",
            null, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateControllerNullType() {
    factory.createController(null,
            svg, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateControllerWrongTypeForView() {
    factory.createController("text", visual, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateControllerInvalidType() {
    factory.createController("few",
            visual, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCreateControllerWrongTypeForView2() {
    factory.createController("svg", interactive, null);
  }


  //mocks are not neccessary to test the TextualController.
  @Test
  public void testTextualControllerRunTxtView() {
    IController textController = factory.createController("text", txt, null);
    assertEquals("", txt.getOutput());
    textController.run();
    assertEquals("Create: Oval firstC x:50 y:50 w:10.0 h:10.0 r:59 g:110 b:26; " +
            "Appears at time 5.0\n" +
            "From second 6.0 to 8.0, firstC Changes size from 10.0x10.0 to size 100.0x100.0\n" +
            "Disappears at time 10.0\n" +
            "Create: Rectangle firstR x:100 y:100 w:10.0 h:20.0 r:26 g:128 b:26; Appears at time " +
            "0.5\n" +
            "From second 1.0 to 10.0, firstR Moves from position (100, 100) to (20, 20)\n" +
            "From second 7.5 to 11.5, firstR Changes color from RGB values (26, 128, 26) to " +
            "(51, 128, 26)\n" +
            "Disappears at time 12.5\n" +
            "Create: Oval secondC x:190 y:190 w:10.0 h:10.0 r:77 g:110 b:26; Appears at time" +
            " 5.0\n" +
            "Disappears at time 10.0\n" +
            "Create: Rectangle secondR x:21 y:40 w:10.0 h:20.0 r:26 g:128 b:26; " +
            "Appears at time 0.5\n" +
            "Disappears at time 12.5\n", txt.getOutput());
  }

  @Test
  public void testTextualControllerRunSVGView() {
    IController textController = factory.createController("svg", svg, null);
    assertEquals("", svg.getOutput());
    textController.run();
    assertEquals("<svg width=\"500\" height=\"500\" version = \"1.1\" \n" +
            "   xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"firstC\" cx=\"50\" cy= \"50\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(59,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\"" +
            " dur=\"10.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" attributeName=\"x\"" +
            " from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" attributeName=\"y\"" +
            " from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"20.0\" to=\"hidden\" " +
            "/>\n" +
            "</ellipse>\n" +
            "<rect id=\"firstR\" x=\"100\" y= \"100\" width=\"10.0\" height=\"20.0\" " +
            "fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"1.0\" dur=\"24.0\" " +
            "to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2.0s\" dur=\"18.0s\" attributeName=\"x\" " +
            "from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2.0s\" dur=\"18.0s\" attributeName=\"y\" " +
            "from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"15.0s\" dur=\"8.0s\" attributeName=\"fill\" " +
            "from=\"rgb(26,128,26)\" to=\"rgb(51,128,26)\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"25.0\" " +
            "to=\"hidden\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"secondC\" cx=\"190\" cy= \"190\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(77,110,26)\" " +
            "visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" dur=\"10.0\" " +
            "to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"20.0\" " +
            "to=\"hidden\" />\n" +
            "</ellipse>\n" +
            "<rect id=\"secondR\" x=\"21\" y= \"40\" width=\"10.0\" height=\"20.0\" " +
            "fill=\"rgb(26,128,26)\" " +
            "visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"1.0\" " +
            "dur=\"24.0\" to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"25.0\" " +
            "to=\"hidden\" />\n" +
            "</rect>\n" +
            "</svg>", svg.getOutput());
  }

  //cant test VisualController

  //tests for interactiveController
  @Test
  public void testButtons() {
    assertEquals("", mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(new ActionEvent(interactiveForMock, 1, "START"));
    assertEquals("Controllers timer and view are started",
            mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(new ActionEvent(interactiveForMock, 2,
            "RESTART"));
    assertEquals("Controllers timer and view are startedAnimation is restarted",
            mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(
            new ActionEvent(interactiveForMock, 3, "RESUME"));
    assertEquals("Controllers timer and view are startedAnimation is " +
                    "restartedAnimation is played",
            mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(
            new ActionEvent(interactiveForMock, 4, "PAUSE"));
    assertEquals("Controllers timer and view are startedAnimation is restartedAnimation" +
                    " is playedAnimation is paused",
            mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(
            new ActionEvent(interactiveForMock, 4, "LOOP"));
    assertEquals("Controllers timer and view are startedAnimation is restartedAnimation" +
                    " is playedAnimation is pausedViews loop is flipped",
            mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(
            new ActionEvent(interactiveForMock, 5, "INCREASE SPEED"));
    assertEquals("Controllers timer and view are startedAnimation is restartedAnimation" +
                    " is playedAnimation is pausedViews loop is flippedSpeed is increased",
            mockInteractive.returnLog().toString());
    interactiveForMock.actionPerformed(
            new ActionEvent(interactiveForMock, 6, "DECREASE SPEED"));
    assertEquals("Controllers timer and view are startedAnimation is" +
                    " restartedAnimation is playedAnimation is pausedViews loop is " +
                    "flippedSpeed is increasedSpeed is decreased",
            mockInteractive.returnLog().toString());

    //testing for assingment 8 for discrete button. Not a way to test Outline button
    // as button click effects panel.
    interactiveForMock.actionPerformed(new ActionEvent(interactiveForMock, 7,
            "PLAY DISCRETE"));
    assertEquals("Controllers timer and view are startedAnimation is " +
            "restartedAnimation is " +
            "playedAnimation is pausedViews loop is flippedSpeed is increasedSpeed is " +
            "decreasedViews discrete field is flipped", mockInteractive.returnLog().toString());

  }


  @Test
  public void testInteractiveControllerMethods() {
    Timer timer = new Timer(200, null);
    AnimatorListener listener = new AnimatorListener(timer, interactive);
    timer.addActionListener(listener);
    InteractiveControllerFeatures controller = new InteractiveController(interactive, timer);
    assertFalse( timer.isRunning());
    controller.start();
    assertTrue( timer.isRunning());
    controller.pause();
    assertFalse( timer.isRunning());

    //tests that only pauses if timer is running
    controller.pause();
    assertFalse( timer.isRunning());
    controller.play();
    assertTrue( timer.isRunning());
    assertEquals(200, timer.getDelay());
    controller.increaseSpeed();
    assertEquals(150, timer.getDelay());

    //tests that it cant decrease past 50
    controller.increaseSpeed();
    controller.increaseSpeed();
    assertEquals(50, timer.getDelay());
    controller.increaseSpeed();
    assertEquals(50, timer.getDelay());

    //tests that timer can increase delay
    controller.decreaseSpeed();
    assertEquals(100, timer.getDelay());
    controller.decreaseSpeed();
    assertEquals(150, timer.getDelay());

    //tests restart
    assertEquals(0, listener.getTick());
    controller.restart();
    assertEquals(1, listener.getTick());

    //tests loop
    assertTrue(interactive.getLooping());
    controller.loop();
    assertFalse(interactive.getLooping());

    //tests discrete
    assertFalse(interactive.getDiscrete());
    controller.discrete();
    assertTrue(interactive.getDiscrete());

  }



}
