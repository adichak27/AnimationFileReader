import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Timer;

import controller.InteractiveController;
import controller.InteractiveControllerFeatures;
import model.Animator;
import model.AnimatorListener;
import model.BuilderImpl;

import model.Coordinate;

import model.Interval;

import model.Oval;
import model.Rectangle;
import model.Size;
import model.Transformation;
import model.TransformationType;
import model.TweenModelBuilder;
import view.FactoryView;
import view.IView;
import view.InteractiveView;
import view.SVGView;
import view.TextView;
import view.VisualView;


import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests everything necessary for the view part of our design.
 */
public class ViewTest {

  private TweenModelBuilder<Animator> builder;
  private TweenModelBuilder<Animator> builder2;
  private Animator builtModel;
  private FactoryView factory = new FactoryView();
  private TweenModelBuilder<Animator> emptyBuilder;
  private Animator modelNoTransfos;

  private Animator ModelWithPlus;


  private ArrayList<Transformation> listOfTransformation = new ArrayList<>();

  @Before
  public void setValues() {
    builder = new BuilderImpl();
    builder2 = new BuilderImpl();
    emptyBuilder = new BuilderImpl();
    builtModel = builder.setBounds(500, 500).addOval("firstC", 50, 50,
                    10, 10, .23f, 0.43f, 0.1f, 10,
                    20).
            addRectangle("firstR", 100, 100, 10, 20, .1f, .5f,
                    .10f, 1, 25).addOval("secondC",190, 190,
                    10, 10, .3f, 0.43f, 0.1f, 10,
                    20).
            addRectangle("secondR", 21, 40, 10, 20, .1f, .5f,
                    .10f, 1, 25).addMove("firstR", 100,
                    100, 20, 20, 2, 20).addColorChange(
                    "firstR", .1f, .5f,
                    .10f, .2f, .5f,
                    .10f, 15, 23).addScaleToChange("firstC", 10,
                    10, 100, 100, 12, 16).build();
    modelNoTransfos = builder2.setBounds(500, 500).addOval("firstC",
            50, 50, 10, 10, .23f,
            .24f, .1f, 10, 20).build();

    ModelWithPlus = builder.setBounds(500, 500).
            addPlus("plusSign", 10,10,10,10,
                    .4f,.1f, .2f, 1, 100).addColorChange(
                    "plusSign", .1f, .5f,
                    .10f, .2f, .5f,
                    .10f, 15, 23).addScaleToChange("plusSign", 10,
                    10, 100, 100, 12, 16).build();



  }

  //tests for factory view
  @Test
  public void testFactoryCreateView() {
    assertTrue(factory.createView("text", builtModel, 2) instanceof TextView);
    assertTrue(factory.createView("svg", builtModel, 2) instanceof SVGView);
    assertTrue(factory.createView("visual", builtModel, 2) instanceof VisualView);
    assertTrue(factory.createView("interactive", builtModel, 2)
            instanceof InteractiveView);

  }

  @Test (expected = IllegalArgumentException.class)
  public void testFactoryCreateViewInvalidModel() {
    factory.createView("text",
            null, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFactoryCreateViewInvalidTempo() {
    factory.createView("text",
            builtModel, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public  void testFactoryCreateViewInvalidView() {
    factory.createView("not cool",
            builtModel, 1);
  }

  //tests for the builderimpl

  //setbounds
  @Test
  public void testBuilderSetBounds() {
    builder.setBounds(10, 10);
    assertEquals(10, builder.build().getCanvasDimensions()[2]);
    assertEquals(10, builder.build().getCanvasDimensions()[3]);
  }

  @Test
  public void testBuilderAddRectangle() {
    //refer to built model in before method
    assertEquals(new Rectangle("firstR", new Coordinate(100, 100), Color.red,
            new Size(10, 20), 1, 25),
            builtModel.getShapes().get(1));
    //checks that shape was added to list at correct spot
  }


  //addoval
  @Test
  public void testBuilderAddOval() {
    //refer to built model in before method
    assertEquals(new Oval("firstC", new Coordinate(50, 50),
            Color.BLACK, new Size(10, 10), 10, 20
    ), builtModel.getShapes().get(0));
    //checks that shape was added to list at correct spot
  }

  @Test
  public void testBuilderAddMove() {
    //refer to built model in before method

    assertEquals(TransformationType.Move,
            builtModel.getShapes().get(1).getAllTransformations().get(0).getType());
    assertEquals(new Interval(2, 20),
            builtModel.getShapes().get(1).getAllTransformations().get(0).getInterval());
    assertEquals("firstR",
            builtModel.getShapes().get(1).getAllTransformations().get(0).
                    getShape().getNameOfShape());

  }


  @Test
  public void testBuilderAddColorChange() {
    //refer to built model in before method

    assertEquals(TransformationType.ChangeColor,
            builtModel.getShapes().get(1).getAllTransformations().get(1).getType());
    assertEquals(new Interval(15, 23),
            builtModel.getShapes().get(1).getAllTransformations().get(1).getInterval());
    assertEquals("firstR",
            builtModel.getShapes().get(1).getAllTransformations().get(1).
                    getShape().getNameOfShape());

  }


  @Test
  public void testBuilderAddScaleToChange() {
    //refer to built model in before method

    assertEquals(TransformationType.ChangeSize,
            builtModel.getShapes().get(0).getAllTransformations().get(0).getType());
    assertEquals(new Interval(12, 16),
            builtModel.getShapes().get(0).getAllTransformations().get(0).getInterval());
    assertEquals("firstC",
            builtModel.getShapes().get(0).getAllTransformations().get(0).
                    getShape().getNameOfShape());

  }
  //build

  @Test
  public void testBuilderBuild() {
    Animator test = emptyBuilder.setBounds(500, 500).addOval("firstC",
                    50, 50,
                    10, 10, .23f, 0.43f, 0.1f, 10,
                    20).
            addRectangle("firstR", 100, 100, 10, 20, .1f, .5f,
                    .10f, 1, 25).addOval("secondC",190, 190,
                    10, 10, .3f, 0.43f, 0.1f, 10,
                    20).
            addRectangle("secondR", 21, 40, 10, 20, .1f, .5f,
                    .10f, 1, 25).addMove("firstR", 100,
                    100, 20,
                    20, 2, 20).addColorChange(
                    "firstR", .1f, .5f,
                    .10f, .2f, .5f,
                    .10f,
                    15, 23).addScaleToChange("firstC", 10,
                    10, 100, 100, 12, 16).build();
    assertEquals(4, test.getShapes().size());
    assertEquals(3, test.getTransformations().size());
    //asserts that a new model with 4 shapes and 3 transformations is built. rest is tested in
    // in above methods

  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuildFails() {
    emptyBuilder.setBounds(-100, 10).build();
    //since the builder method implementations just use our methods that we created in the model,
    // we do not see the reason in testing that each of the above tests throws errors,
    // since we know that they will.
  }

  //tests for text view

  //render is also tested with these methods
  @Test
  public void testTextOutput() {
    IView view = factory.createView("text", builtModel, 1);
    view.render(1);
    assertEquals("Create: Oval firstC x:50 y:50 w:10.0 h:10.0 r:59 g:110 b:26; " +
            "Appears at time 10.0\n" +
            "From second 12.0 to 16.0, firstC Changes size from 10.0x10.0 to size 100.0x100.0\n" +
            "Disappears at time 20.0\n" +
            "Create: Rectangle firstR x:100 y:100 w:10.0 h:20.0 r:26 g:128 b:26; " +
            "Appears at time 1.0\n" +
            "From second 2.0 to 20.0, firstR Moves from position (100, 100) to (20, 20)\n" +
            "From second 15.0 to 23.0, firstR Changes color from RGB values (26, 128, 26) to" +
            " (51, 128, 26)\n" +
            "Disappears at time 25.0\n" +
            "Create: Oval secondC x:190 y:190 w:10.0 h:10.0 r:77 g:110 b:26; " +
            "Appears at time 10.0\n" +
            "Disappears at time 20.0\n" +
            "Create: Rectangle secondR x:21 y:40 w:10.0 h:20.0 r:26 g:128 b:26;" +
            " Appears at time 1.0\n" +
            "Disappears at time 25.0\n", view.getOutput());
    //also checks that the string is changed to represent different tempos
    IView view2 = factory.createView("text", builtModel, 2);
    view2.render(2);
    assertEquals("Create: Oval firstC x:50 y:50 w:10.0 h:10.0 r:59 g:110 b:26; " +
            "Appears at time 5.0\n" +
            "From second 6.0 to 8.0, firstC Changes size from 10.0x10.0 to size 100.0x100.0\n" +
            "Disappears at time 10.0\n" +
            "Create: Rectangle firstR x:100 y:100 w:10.0 h:20.0 r:26 g:128 b:26; " +
            "Appears at time 0.5\n" +
            "From second 1.0 to 10.0, firstR Moves from position (100, 100) to (20, 20)\n" +
            "From second 7.5 to 11.5, firstR Changes color from RGB values (26, 128, 26) to " +
            "(51, 128, 26)\n" +
            "Disappears at time 12.5\n" +
            "Create: Oval secondC x:190 y:190 w:10.0 h:10.0 r:77 g:110 b:26;" +
            " Appears at time 5.0\n" +
            "Disappears at time 10.0\n" +
            "Create: Rectangle secondR x:21 y:40 w:10.0 h:20.0 r:26 g:128 b:26;" +
            " Appears at time 0.5\n" +
            "Disappears at time 12.5\n", view2.getOutput());
    IView emptyView = factory.createView("text", builtModel, 2);
    //dont render the view
    assertEquals("", emptyView.getOutput());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testErrorConstructingViewNullModel() {
    new TextView(null, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testErrorConstructingViewNegativeTempo() {
    new TextView(builtModel, -1);
  }



  @Test
  public void testSVGOutput() {
    IView svgView = factory.createView("svg", builtModel, 1);
    svgView.render(1);
    assertEquals("<svg width=\"500\" height=\"500\" version = \"1.1\" \n" +
            "   xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"firstC\" cx=\"50\" cy= \"50\" rx=\"10.0\" ry=\"10.0\"" +
            " fill=\"rgb(59,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\"" +
            " dur=\"10.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" attributeName=\"width\" " +
            "from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" attributeName=\"height\" "
            +
            "from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"20.0\" to=\"hidden\"" +
            " />\n" +
            "</ellipse>\n" +
            "<rect id=\"firstR\" x=\"100\" y= \"100\" width=\"10.0\" height=\"20.0\"" +
            " fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"1.0\" dur=\"24.0\" " +
            "to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2.0s\" dur=\"18.0s\" attributeName=\"x\" " +
            "from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2.0s\" dur=\"18.0s\" attributeName=\"y\" " +
            "from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"15.0s\" dur=\"8.0s\" attributeName=\"fill\" " +
            "from=\"rgb(26,128,26)\" to=\"rgb(51,128,26)\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"25.0\"" +
            " to=\"hidden\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"secondC\" cx=\"190\" cy= \"190\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(77,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" dur=\"10.0\" " +
            "to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"20.0\"" +
            " to=\"hidden\" />\n" +
            "</ellipse>\n" +
            "<rect id=\"secondR\" x=\"21\" y= \"40\" width=\"10.0\" height=\"20.0\"" +
            " fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"1.0\" dur=\"24.0\" " +
            "to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"25.0\" " +
            "to=\"hidden\" />\n" +
            "</rect>\n" +
            "</svg>", svgView.getOutput());
    //
    IView svgview2 = factory.createView("svg", builtModel, 2);
    svgview2.render(20);
    assertEquals("<svg width=\"500\" height=\"500\" version = \"1.1\" \n" +
            "   xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"firstC\" cx=\"50\" cy= \"50\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(59,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"5.0\" dur=\"5.0\"" +
            " to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"6.0s\" dur=\"2.0s\" attributeName=\"width\" " +
            "from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"6.0s\" dur=\"2.0s\" attributeName=\"height\" " +
            "from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\"" +
            " to=\"hidden\" />\n" +
            "</ellipse>\n" +
            "<rect id=\"firstR\" x=\"100\" y= \"100\" width=\"10.0\" height=\"20.0\" " +
            "fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"0.5\" dur=\"12.0\" " +
            "to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"9.0s\" attributeName=\"x\" " +
            "from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"9.0s\" attributeName=\"y\" " +
            "from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"7.5s\" dur=\"4.0s\" attributeName=\"fill\" " +
            "from=\"rgb(26,128,26)\" to=\"rgb(51,128,26)\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"12.5\" " +
            "to=\"hidden\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"secondC\" cx=\"190\" cy= \"190\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(77,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"5.0\" dur=\"5.0\" " +
            "to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" " +
            "to=\"hidden\" />\n" +
            "</ellipse>\n" +
            "<rect id=\"secondR\" x=\"21\" y= \"40\" width=\"10.0\" height=\"20.0\" " +
            "fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"0.5\" d" +
            "ur=\"12.0\" to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"12.5\" " +
            "to=\"hidden\" />\n" +
            "</rect>\n" +
            "</svg>", svgview2.getOutput());
    IView emptysvg = factory.createView("svg", builtModel, 1);
    //dont render
    assertEquals("", emptysvg.getOutput());

  }

  //MAKE VISIBLE
  //test that this doesnt work for the other 2 views
  @Test (expected = UnsupportedOperationException.class)
  public void testErrorMakeVisibleTestText() {
    factory.createView("text", builtModel, 2).makeVisible();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testErrorMakeVisibleTestSVG() {
    factory.createView("svg", builtModel, 2).makeVisible();
  }

  //not testing getters
  //write out gets tested by files in resources


  //view tests added assignment 6 for Interactive View methods


  @Test
  public void testFlipLoopGetLoop() {
    IView view = factory.createView("interactive", builtModel, 2);
    assertTrue(view.getLooping());
    view.flipLoop();
    assertFalse(view.getLooping());
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testFlipLoopUnsupported() {
    factory.createView("svg", builtModel,
            2).flipLoop();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testGetLoopingUnsupported() {
    factory.createView("text", builtModel, 2).getLooping();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testSetInteractiveControllerUnsupported() {
    factory.createView("visual", builtModel, 2).
            setInteractiveController(new InteractiveController(factory.createView(
                    "interactive", builtModel, 2)));
  }





  @Test (expected = IllegalArgumentException.class)
  public void testInteractiveConstructorNull() {
    new InteractiveView(null, 23);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInteractiveConstructorNegTempo() {
    new InteractiveView(builtModel, -2);
  }


  //tests for animatorListener

  @Test (expected = IllegalArgumentException.class)
  public void createAnimatorListenerNoView() {
    new AnimatorListener(new Timer(200,
            null), null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void createAnimatorListenerNoTimer() {
    new AnimatorListener(null, new VisualView(builtModel, 2));
  }

  @Test
  public void testGetTickSetTickAnimatorListener() {
    Timer timer = new Timer(200, null);
    AnimatorListener listener = new AnimatorListener(timer,
            new FactoryView().createView("visual", builtModel, 200));
    timer.addActionListener(listener);
    assertEquals(0, listener.getTick());
    VisualView view = new VisualView(builtModel, 1);
   listener.resetTick();
    assertEquals(1, listener.getTick());
  }

  @Test
  public void testGetFinalTick() {
    assertEquals(25,
            new VisualView(builtModel, 2).getFinalTick());
  }

  @Test (expected = NullPointerException.class)
  public void testErrorWhenNoControllerSet() {
    InteractiveView view = new InteractiveView(builtModel, 2);
    view.actionPerformed(new ActionEvent(view, 1, "START"));
  }

  @Test
  public void TestNoErrorWithSetController() {
    InteractiveView view = new InteractiveView(builtModel, 2);
    InteractiveControllerFeatures controller = new InteractiveController(view);
    view.setInteractiveController(controller);
    view.actionPerformed(new ActionEvent(view, 1, "START"));
    assertEquals("", view.getOutput());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetControllerNoInput() {
    InteractiveView view = new InteractiveView(builtModel, 2);
    view.setInteractiveController(null);
  }

  //Tests for interactive View after assingment 8

  @Test (expected = UnsupportedOperationException.class)
  public void testGatherDiscreteFrameWrongView() {
    VisualView view = new VisualView(builtModel, 2);
    view.gatherAllDiscreteFrames();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testGatherDiscreteFrameWrongView2() {
    TextView view = new TextView(builtModel, 2);
    view.gatherAllDiscreteFrames();
  }

  @Test
  public void testGatherDiscreteFrames() {
    InteractiveView view = new InteractiveView(builtModel, 2);
    assertEquals(new ArrayList<Integer>(Arrays.asList(2, 12, 15, 16, 20, 23)),
            view.gatherAllDiscreteFrames());
    InteractiveView view2 = new InteractiveView(modelNoTransfos, 2);
    assertEquals(new ArrayList<Integer>(), view2.gatherAllDiscreteFrames());
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testFlipDiscreteWrongview() {
    TextView view = new TextView(builtModel, 2);
    view.flipDiscrete();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testFlipDiscreteWrongview2() {
    VisualView view = new VisualView(builtModel, 2);
    view.flipDiscrete();
  }

  @Test
  public void testFlipDiscreteAndGetDiscrete() {
    InteractiveView view = new InteractiveView(builtModel, 2);
    assertFalse(view.getDiscrete());
    view.flipDiscrete();
    assertTrue(view.getDiscrete());
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testGetDiscreteWrongView() {
    VisualView view = new VisualView(builtModel, 2);
    view.getDiscrete();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testGetDiscreteWrongView2() {
    TextView view = new TextView(builtModel, 2);
    view.getDiscrete();
  }

  //Listener tests with new functionality for discrete playing.
  @Test
  public void resetTickInteractiveDiscrete() {
    Timer timer = new Timer(200, null);
    InteractiveView view = new InteractiveView(builtModel, 2);
    AnimatorListener listener = new AnimatorListener(timer,
            view);
    timer.addActionListener(listener);
    assertEquals(0, listener.getTick());
    listener.resetTick();
    assertEquals(1, listener.getTick());
    view.flipDiscrete();
    listener.resetTick();
    assertEquals(2, listener.getTick());
  }

  @Test (expected =  IllegalArgumentException.class)
  public void testResetTickToDiscreteNoTransformations() {
    Timer timer = new Timer(200, null);
    InteractiveView view = new InteractiveView(modelNoTransfos, 2);
    AnimatorListener listener = new AnimatorListener(timer,
            view);
    timer.addActionListener(listener);
    view.flipDiscrete();
    listener.resetTick();
  }

  @Test
  public void testSVGOutputPlus() {
    IView svgView = factory.createView("svg", ModelWithPlus, 1);
    svgView.render(1);

    assertEquals("<svg width=\"500\" height=\"500\" version = \"1.1\" \n" +
            "   xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"firstC\" cx=\"50\" cy= \"50\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(59,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" " +
            "dur=\"10.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"width\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"height\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"20.0\" to=\"hidden\" />\n" +
            "</ellipse>\n" +
            "<rect id=\"firstR\" x=\"100\" y= \"100\" width=\"10.0\" " +
            "height=\"20.0\" fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"1.0\" dur=\"24.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2.0s\" dur=\"18.0s\" " +
            "attributeName=\"x\" from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2.0s\" dur=\"18.0s\" " +
            "attributeName=\"y\" from=\"100\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"15.0s\" dur=\"8.0s\" " +
            "attributeName=\"fill\" from=\"rgb(26,128,26)\" to=\"rgb(51,128,26)\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"25.0\" to=\"hidden\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"secondC\" cx=\"190\" cy= \"190\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(77,110,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"10.0\" dur=\"10.0\" to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"20.0\" to=\"hidden\" />\n" +
            "</ellipse>\n" +
            "<rect id=\"secondR\" x=\"21\" y= \"40\" width=\"10.0\" " +
            "height=\"20.0\" fill=\"rgb(26,128,26)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"1.0\" dur=\"24.0\" to=\"visible\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"25.0\" to=\"hidden\" />\n" +
            "</rect>\n" +
            "<rect id=\"plusSign1\" x=\"10\" y= \"12\" width=\"10.0\" " +
            "height=\"5.0\" fill=\"rgb(102,26,51)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"1.0\" dur=\"99.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" attributeName=\"width\" " +
            "from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"height\" from=\"5.0\" to=\"50.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"x\" from=\"10\" to=\"10\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"y\" from=\"12\" to=\"35\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"15.0s\" dur=\"8.0s\" " +
            "attributeName=\"fill\" from=\"rgb(26,128,26)\" to=\"rgb(51,128,26)\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"100.0\" to=\"hidden\" />\n" +
            "</rect>\n" +
            "<rect id=\"plusSign2\" x=\"12\" y= \"10\" width=\"5.0\" height=\"10.0\" " +
            "fill=\"rgb(102,26,51)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"1.0\" dur=\"99.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"width\" from=\"5.0\" to=\"50.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"height\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"x\" from=\"12\" to=\"35\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"12.0s\" dur=\"4.0s\" " +
            "attributeName=\"y\" from=\"10\" to=\"10\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"15.0s\" dur=\"8.0s\" " +
            "attributeName=\"fill\" from=\"rgb(26,128,26)\" to=\"rgb(51,128,26)\" " +
            "fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' " +
            "begin=\"100.0\" to=\"hidden\" />\n" +
            "</rect>\n" +
            "</svg>", svgView.getOutput());
  }

}
