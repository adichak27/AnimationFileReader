import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import model.Animator;
import model.ChangeColor;
import model.ChangeSize;
import model.Coordinate;
import model.DoNothing;
import model.EasyAnimator;
import model.IShape;
import model.Interval;
import model.IntervalComparator;
import model.Move;
import model.Oval;
import model.Plus;
import model.Rectangle;
import model.Size;
import model.Transformation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is the testing class for our model. It tests all of the methods in the Transformation,
 * IShape, and Animator interfaces along with all of the abstract classes that implement these
 * interfaces.
 */
public class ModelTest {

  private Animator animator = new EasyAnimator();

  private Coordinate c;
  private Coordinate c2;
  private Color col;
  private Color col2;

  Size size = new Size(15, 20);
  private Size size2;

  private IShape oval;
  private IShape rect;
  private IShape rect2;
  private IShape plus;


  private Interval interval;
  private Interval equalInterval;
  private Interval interval2;
  Interval interval3 = new Interval(10, 12);
  private Interval interval4;

  private Transformation transformColor;
  private Transformation transformSize;
  private Transformation transformSizeOverlap;
  private Transformation transformSize2;
  private Transformation transformColorPlus;
  private Transformation transformSizePlus;


  private Transformation move;
  private Transformation moveOverlap;
  private Transformation move2;
  private Transformation move3;

  private Transformation movePlus;
  private Transformation move2Plus;
  private Transformation move3Plus;


  private Transformation moveRect;
  private Transformation moveRect2;



  ArrayList<Transformation> listOfTransformation = new ArrayList<>();

  @Before
  public void setValues() {
    animator = new EasyAnimator();
    c = new Coordinate(1,1);
    c2 = new Coordinate(10,20);
    col = new Color( 42, 19, 205);
    col2 = new Color( 123, 19, 205);
    // size = new Size(15, 20);
    size2 = new Size(30, 30);
    oval = new Oval("Oval1", c, col, size, 0, 10);
    rect = new Rectangle("Rectangle1", c, col2, size, 0, 10);
    rect2 = new Rectangle("Rectangle1", c, col2, size, 0, 1);
    plus = new Plus("plus", new Size(5, 5), c, col, 0, 10);

    interval = new Interval(1, 4);
    equalInterval = new Interval(1, 4);
    interval2 = new Interval(4, 6);
    //interval3 = new Interval(10, 12);
    interval4 = new Interval(3, 10);
    transformColor = new ChangeColor(oval, interval, 42, 19, 205,
            123, 19, 205);
    transformSizeOverlap = new ChangeSize(oval, new Interval(2, 5),
            new Size(1, 2), new Size(2, 3));
    transformSize = new ChangeSize(oval, interval, size, size2);
    transformSize2 = new ChangeSize(oval, interval2, size, size2);
    move = new Move(oval, interval, c, c2);
    moveOverlap = new Move(oval, new Interval(2, 3), c2, c);
    move2 = new Move(oval, interval2, c, c2);
    move3 = new Move(oval, interval3, c2, c);
    moveRect = new Move(rect, interval2, c, c2);
    moveRect2 = new Move(rect2, interval2, c, c2);

    movePlus = new Move(plus, interval, c, c2);
    move2Plus = new Move(plus, interval2, c, c2);
    move3Plus = new Move(plus, interval3, c2, c);

    transformColorPlus = new ChangeColor(plus,
            interval, 42, 19, 205,
            123, 19, 205);
    transformSizePlus = new ChangeSize(plus, interval, new Size(5, 5),
            new Size(10,10));

  }

  //test methods for intervals

  @Test
  public void testIntervalConstructor() {
    Interval i = new Interval(1, 10);
    assertEquals(1, i.getStarting());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIntervalConstructorNegativeField() {
    new Interval(-1, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIntervalConstructorInvalidInterval() {
    new Interval(5, 3);
  }

  @Test
  public void testIntervalGetters() {
    assertEquals(1, interval.getStarting());
    assertEquals(4, interval.getEnding());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCheckOverlapErrorNull() {
    interval.checkOverlap(null);
  }

  @Test
  public void testCheckOverlap() {
    assertFalse(interval.checkOverlap(interval2));
    assertTrue(interval4.checkOverlap(interval2));
  }

  @Test
  public void testIntervalEquals() {
    assertNotEquals(interval, interval2);
    assertEquals(interval, equalInterval);

  }

  @Test
  public void testIntervalHashCode() {
    assertEquals(interval.hashCode(), equalInterval.hashCode());
    assertNotEquals(interval.hashCode(), interval2.hashCode());
  }
  //end of tests for interval

  //tests for IntervalComparator

  @Test (expected = IllegalArgumentException.class)
  public void testIntervalComparatorCompareNull() {
    new IntervalComparator().compare(null, interval);
  }

  @Test
  public void testIntervalComparatorCompare() {
    assertEquals(-1, new IntervalComparator().compare(interval, interval2));
    assertEquals(1, new IntervalComparator().compare(interval2, interval));
    assertEquals(0, new IntervalComparator().compare(interval, equalInterval));
  }
  //end of testing IntervalComparator

  //Tests for Size

  @Test (expected = IllegalArgumentException.class)
  public void testFailingSizeConstructor() {
    new Size(-10, 20);
  }

  @Test
  public void testSetSize() {
    assertEquals(30, size2.getHeight(), .001);
    size2.setSize(new Size(30, 2));
    assertEquals(2, size2.getHeight(), .001);

  }



  // BEGIN TESTING METHODS IN ISHAPE
  @Test
  public void testChangeColor() {
    assertEquals(new Color(42,19,205), oval.getColor());
    oval.changeColor(col2);
    assertEquals(new Color(123,19,205), oval.getColor());

  }

  @Test
  public void testMove() {
    assertEquals(c, oval.getLocation());
    assertEquals(c.getX(), oval.getLocation().getX());

    oval.moveShape(c2);
    assertEquals(c2, oval.getLocation());

  }

  @Test
  public void testChangeSize() {
    assertEquals(20.0, oval.getHeight(), .001);
    assertEquals(15.0, oval.getWidth(), 001);
    oval.changeSize(size2);
    assertEquals(30, oval.getHeight(), .001);
    assertEquals(30, oval.getWidth(), .001);
  }

  @Test
  public void testGetNameofShape() {
    assertEquals("Oval1", oval.getNameOfShape());
  }

  @Test
  public void testAddRemoveTransformation() {
    setValues();
    animator.addShape(oval);
    animator.addTransformation(transformColor);

    assertEquals(1, animator.getTransformations().size());
    assertEquals(1, oval.getTransformationsOverInterval().size());
    animator.addTransformation(transformSize);

    //checks it was added to both the animator and actualy shape class
    assertEquals(2, animator.getTransformations().size());
    assertEquals(1, oval.getTransformationsOverInterval().size());

    animator.addTransformation(transformSize2);
    assertEquals(3, animator.getTransformations().size());
    assertEquals(2, oval.getTransformationsOverInterval().size());

    animator.removeTransformation(transformSize2);
    assertEquals(2, animator.getTransformations().size());
    assertEquals(2, oval.getTransformationsOverInterval().size());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddTransformationNonExistentShape() {
    animator.addTransformation(transformColor);

  }

  @Test (expected = IllegalArgumentException.class)
  public void testCantAddTransformationIfOverlappingTypes() {
    animator.addShape(oval);
    animator.addTransformation(move);
    animator.addTransformation(moveOverlap);
  }

  @Test (expected = IllegalArgumentException.class)
  public void CantAddTransformationDisruptsAdjacency() {
    animator.addShape(oval);
    animator.addTransformation(move);
    animator.addTransformation(move2);
  }

  @Test
  public void CanAddTransformationIfOverlapping() {
    animator.addShape(oval);
    animator.addTransformation(move);
    animator.addTransformation(transformSizeOverlap);
    assertEquals(2, animator.getTransformations().size());
  }




  @Test
  public void testGetAllTransformation() {
    // also ensuring the checkAdjacency method works since it isn't throwing any exceptions in this
    // test method
    animator.addShape(oval);
    oval.checkAdjacency(move);
    animator.addTransformation(move);
    animator.addTransformation(transformColor);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    assertEquals(4, oval.getAllTransformations().size());
  }


  @Test (expected = IllegalArgumentException.class)
  public void testCheckAdjecency() {
    oval.checkAdjacency(move);
    animator.addTransformation(move);
    // should throw exception because we are teleporting here
    oval.checkAdjacency(move2);
  }

  @Test
  public void testIShapeGetters() {
    assertEquals(c, oval.getLocation());
    assertEquals("Oval1", oval.getNameOfShape());
    assertEquals(col, oval.getColor());
    assertEquals(20.0, oval.getHeight(), .001);
    assertEquals(15.0, oval.getWidth(), .001);
    assertEquals(c, rect.getLocation());
    assertEquals("Rectangle1", rect.getNameOfShape());
    assertEquals(col2, rect.getColor());
    assertEquals(20.0, rect.getHeight(), .001);
    assertEquals(15.0, rect.getWidth(), .001);
  }
  // BEGIN TESTING METHODS IN Animator

  @Test
  public void testAddRemoveShape() {
    assertEquals(0, animator.getShapes().size());
    animator.addShape(oval);
    animator.addShape(rect);
    assertEquals(2, animator.getShapes().size());
    animator.addTransformation(transformSize);
    assertEquals(transformSize,
            oval.getTransformationsOverInterval().get(transformSize.getInterval()).get(0));
    animator.addTransformation(moveRect);
    assertEquals(2, animator.getTransformations().size());

    // this all ensures removeTransformation and getTransformations is working correctly
    animator.removeShape(oval);
    assertEquals(1, animator.getShapes().size());
    assertEquals(1, animator.getTransformations().size());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveshapeNull() {
    animator.removeShape(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveShapeNotInAnimator() {
    animator.removeShape(new Rectangle("square",
            new Coordinate(1, 2), Color.red, new Size(2,2), 0,
            10));
  }


  @Test (expected = IllegalArgumentException.class)
  public void TestAddShapeFail() {
    animator.addShape(oval);
    animator.addShape(oval);
  }


  @Test
  public void testOutputState() {

    animator.addShape(rect);
    animator.addShape(oval);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    animator.addTransformation(transformColor);
    animator.addTransformation(move);
    animator.addTransformation(moveRect);
    animator.addTransformation(move3);

    assertEquals("Create: Rectangle Rectangle1 x:1 y:1 w:15.0 h:20.0 r:123 g:19 b:205\n" +
                    "From tick 4 to 6, Rectangle1 Moves from position (1, 1) to (10, 20)\n" +
                    "\n" +
                    "Create: Oval Oval1 x:1 y:1 w:15.0 h:20.0 r:42 g:19 b:205\n" +
                    "From tick 1 to 4, Oval1 Changes size from 15.0x20.0 to size 30.0x30.0\n" +
                    "From tick 1 to 4, Oval1 Changes color from RGB values (42, 19, 205) to" +
                    " (123, 19, 205)\n" +
                    "From tick 1 to 4, Oval1 Moves from position (1, 1) to (10, 20)\n" +
                    "From tick 4 to 6, Oval1 Changes size from 15.0x20.0 to size 30.0x30.0\n" +
                    "From tick 6 to 10, Oval1 does nothing\n" +
                    "From tick 10 to 12, Oval1 Moves from position (10, 20) to (1, 1)\n\n",
            animator.outputState());

  }

  @Test
  public void testEmptyOutputState() {
    assertEquals("", animator.outputState());
  }

  @Test
  public void testGetLastTick() {

    assertEquals(0, animator.getLastTick());
    animator.addShape(rect);
    animator.addShape(oval);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    animator.addTransformation(transformColor);
    animator.addTransformation(move);
    animator.addTransformation(moveRect);
    animator.addTransformation(move3);

    assertEquals(10, animator.getLastTick());
  }

  @Test
  public void testToStringMethods() {
    animator.addShape(rect);
    animator.addShape(oval);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    animator.addTransformation(transformColor);
    animator.addTransformation(move);
    animator.addTransformation(moveRect);
    animator.addTransformation(move3);

    assertEquals("Oval1 Changes size from 15.0x20.0 to size 30.0x30.0",
            transformSize.toString());
    assertEquals("Oval1 Changes color from RGB values (42, 19, 205) to" +
            " (123, 19, 205)", transformColor.toString());
    assertEquals("Oval1 Moves from position (1, 1) to (10, 20)",
            move.toString());

    // only returns the starting information as of now
    assertEquals("Oval Oval1 x:1 y:1 w:15.0 h:20.0 r:42 g:19 b:205", oval.toString());
    assertEquals("Create: Rectangle Rectangle1 x:1 y:1 w:15.0 h:20.0 r:123 g:19 b:205\n" +
                    "From tick 4 to 6, Rectangle1 Moves from position (1, 1) to (10, 20)\n" +
                    "\n" +
                    "Create: Oval Oval1 x:1 y:1 w:15.0 h:20.0 r:42 g:19 b:205\n" +
                    "From tick 1 to 4, Oval1 Changes size from 15.0x20.0 to size 30.0x30.0\n" +
                    "From tick 1 to 4, Oval1 Changes color from RGB values (42, 19, 205) " +
                    "to (123, 19, 205)\n" +
                    "From tick 1 to 4, Oval1 Moves from position (1, 1) to (10, 20)\n" +
                    "From tick 4 to 6, Oval1 Changes size from 15.0x20.0 to size 30.0x30.0\n" +
                    "From tick 6 to 10, Oval1 does nothing\n" +
                    "From tick 10 to 12, Oval1 Moves from position (10, 20) to (1, 1)\n" +
                    "\n",
            animator.outputState());
  }


  @Test (expected = IllegalArgumentException.class)
  public void testFailingConstructorDoNothing() {
    new DoNothing(null, interval);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFailingConstructorMove() {
    new Move(oval, interval, null, c);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFailingConstructorChangeColor() {
    new ChangeColor(oval, interval, 0, 20, 100, -10,
            20, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFailingConstructorChangeSize() {
    new ChangeSize(oval, interval, size, null);
  }


  @Test
  public void fillGapWhenRemovingTransformation() {
    animator.addShape(oval);
    oval.checkAdjacency(move);
    animator.addTransformation(move);
    animator.addTransformation(transformColor);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    animator.addTransformation(move3);
    animator.removeTransformation(transformSize2);
    //shows that gap is filled
    assertEquals("Create: Oval Oval1 x:1 y:1 w:15.0 h:20.0 r:42 g:19 b:205\n" +
            "From tick 1 to 4, Oval1 Moves from position (1, 1) to (10, 20)\n" +
            "From tick 1 to 4, Oval1 Changes color from RGB values (42, 19, 205) " +
            "to (123, 19, 205)\n"
            + "From tick 1 to 4, Oval1 Changes size from 15.0x20.0 to size 30.0x30.0\n" +
            "From tick 4 to 6, Oval1 does nothing\n" +
            "From tick 6 to 10, Oval1 does nothing\n" +
            "From tick 10 to 12, Oval1 Moves from position (10, 20) to (1, 1)\n" +
            "\n", animator.outputState());
  }

  @Test
  public void testSVGTagForShape() {
    animator.addShape(oval);
    animator.addTransformation(move);
    animator.addTransformation(transformColor);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    animator.addTransformation(move3);
    animator.removeTransformation(transformSize2);

    assertEquals("<ellipse id=\"Oval1\" cx=\"1\" cy= \"1\" rx=\"15.0\" ry=\"20.0\" " +
            "fill=\"rgb(42,19,205)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"0.0\" dur=\"10.0\" " +
            "to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"x\" " +
            "from=\"1\" to=\"10\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"y\" " +
            "from=\"1\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"fill\" " +
            "from=\"rgb(42,19,205)\" to=\"rgb(123,19,205)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"x\" " +
            "from=\"15.0\" to=\"30.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"y\" " +
            "from=\"20.0\" to=\"30.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"10.0s\" dur=\"2.0s\" attributeName=\"x\" " +
            "from=\"10\" to=\"1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"10.0s\" dur=\"2.0s\" attributeName=\"y\" " +
            "from=\"20\" to=\"1\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" to=\"hidden\" " +
            "/>\n" +
            "</ellipse>\n", oval.svgTagForShape(1));
  }

  @Test
  public void testShapeColorToString() {
    animator.addShape(oval);
    animator.addTransformation(move);
    animator.addTransformation(transformColor);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);
    animator.addTransformation(move3);
    animator.removeTransformation(transformSize2);

    assertEquals("\"rgb(42,19,205)\"", oval.shapeColorToString());
  }

  @Test
  public void testSetTransformationOverInterval() {
    animator.addShape(oval);
    animator.addShape(rect);
    animator.addTransformation(move);
    animator.addTransformation(transformColor);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);

    // throws error if false
    move.validStartAndEndLife();
    assertEquals(new Oval("Oval1", new Coordinate(1,1), new Color(42, 19, 205),
            new Size(15.0, 20.0), 0 ,  10), oval.copyShape());

  }


  @Test
  public void testSetTransformations() {
    animator.addShape(rect);
    animator.addTransformation(moveRect);
    TreeMap<Interval, ArrayList<Transformation>> map = rect.getTransformationsOverInterval();

    rect2.setTransformationsOverInterval(map);


    assertEquals(new ArrayList<Transformation>(Arrays.asList(moveRect)),
            map.get(moveRect.getInterval()));

  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartAndEndLife() {
    animator.addShape(rect2);

    // should throw exception
    moveRect2.validStartAndEndLife();
  }

  @Test
  public void testInterpolate() {

    assertEquals(20.0,move3.interpolate(1, 20, 12), .001);
    assertEquals(58.0,
            transformColor.interpolate(50, 62, 3), .001);
    assertEquals(113,
            transformSize.interpolate(100, 110, 5), .001);
    assertEquals(5.0,move2.interpolate(10, 15, 2), .001);

  }

  @Test
  public void testSVGTagTransformation() {

    assertEquals("<animate attributeType=\"xml\" begin=\"4.0s\" dur=\"2.0s\" " +
            "attributeName=\"x\" from=\"1\" to=\"10\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4.0s\" dur=\"2.0s\" " +
            "attributeName=\"y\" from=\"1\" to=\"20\" " +
            "fill=\"freeze\" />\n", move2.svgTag(1));

    assertEquals("<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" " +
            "attributeName=\"fill\" from=\"rgb(42,19,205)\" to=\"rgb(123,19,205)\" " +
            "fill=\"freeze\" />\n", transformColor.svgTag(1));

    assertEquals("<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" " +
            "attributeName=\"x\" from=\"15.0\" to=\"30.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"y\"" +
            " from=\"20.0\" to=\"30.0\" fill=\"freeze\" />\n", transformSize.svgTag(1));
  }

  @Test
  public void testGetShapesAtTick() {
    animator.addShape(oval);
    animator.addShape(rect);
    animator.addTransformation(move);
    animator.addTransformation(transformColor);
    animator.addTransformation(transformSize);
    animator.addTransformation(transformSize2);

    assertEquals(new ArrayList<IShape>(Arrays.asList(oval, rect)), animator.getShapesAtTick(8));
  }

  @Test
  public void testExecute() {
    assertEquals(new Oval("Oval1", new Coordinate(1, 15),
            new Color(123, 19, 205), new Size(15, 20),
            0, 10), transformColor.execute(transformColor.getShape(), 4));

    assertEquals(new Oval("Oval1", new Coordinate(1, 15),
            new Color(123, 19, 205), new Size(15, 20),
            0, 10), transformSize.execute(transformSize.getShape(), 10));

    assertEquals(new Oval("Oval1", new Coordinate(1, 15),
            new Color(123, 19, 205), new Size(15, 20),
            0, 10), move.execute(move.getShape(), 2));
  }


  @Test (expected = IllegalArgumentException.class)
  public void testCreatePlusInvalidSize() {
    new Plus("new", new Size(10, 5), new Coordinate(100, 100),
            Color.red, 1, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCantAddIfExists() {
    animator.addShape(plus);
    animator.addTransformation(movePlus);
    animator.addTransformation(movePlus);
    assertEquals(2, animator.getTransformations().size());

  }

  @Test
  public void addRemoveTransfoRect() {
    setValues();
    animator.addShape(plus);
    animator.addTransformation(movePlus);
    animator.addTransformation(transformSizePlus);
    animator.addTransformation(transformColorPlus);
    assertEquals(3, animator.getTransformations().size());

    animator.removeTransformation(transformColorPlus);

    assertEquals(2, animator.getTransformations().size());
  }

  @Test
  public void testSvgTag() {
    setValues();
    animator.addShape(plus);
    animator.addTransformation(transformColorPlus);
    animator.addTransformation(movePlus);

    assertEquals("<rect id=\"plus1\" x=\"1\" y= \"2\" width=\"5.0\" height=\"2.5\" fill=\"rgb(42,19,205)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"0.0\" dur=\"10.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"fill\" from=\"rgb(42,19,205)\" to=\"rgb(123,19,205)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"x\" from=\"1\" to=\"10\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"y\" from=\"2\" to=\"21\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" to=\"hidden\" />\n" +
            "</rect>\n" +
            "<rect id=\"plus2\" x=\"2\" y= \"1\" width=\"2.5\" height=\"5.0\" fill=\"rgb(42,19,205)\" visibility=\"hidden\" >\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"0.0\" dur=\"10.0\" to=\"visible\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"fill\" from=\"rgb(42,19,205)\" to=\"rgb(123,19,205)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"x\" from=\"2\" to=\"11\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1.0s\" dur=\"3.0s\" attributeName=\"y\" from=\"1\" to=\"20\" fill=\"freeze\" />\n" +
            "<set attributeName=\"visibility\" attributeType='XML' begin=\"10.0\" to=\"hidden\" />\n" +
            "</rect>\n", plus.svgTagForShape(1));
  }

  @Test
  public void testToString() {
    animator.addShape(plus);
    assertEquals("Plus plus x:1 y:1 w:5.0 h:5.0 r:42 g:19 b:205", plus.toString());
  }


  // adding a plus, as well as error thrown if cant yes

  //test svg outputs with plus. yes

  // test the flipoutline on panel, cant test button ????

  // test all public facing plus methods. yes

  //test adding transfromations to plus yes

  //test error if adding size transfo with different size dimensions. yes
}
