package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import model.Car;
import model.Intersection;
import model.LightState;
import model.MP;
import model.Road;

/** 
 * A class for building Animators.
 */
public class SwingAnimatorBuilder implements AnimatorBuilder {
  MyPainter _painter;
  private static MP _MP;
  private static VP _VP;
  private static double skipInit;
  private static double skipRoad;
  private static double skipCar;
  private static double skipRoadCar;
  public SwingAnimatorBuilder(MP m) {
	    _MP = m;
	    _VP = VP.getInstance(m);
	    skipInit = _VP.getGap();
	    skipRoad = _VP.getGap() + _MP.getRoadLengthMin();
	    skipCar = _VP.getGap() + _VP.getElementWidth();
	    skipRoadCar = skipRoad + skipCar;
    _painter = new MyPainter();

  }
  public Animator getAnimator() {
    if (_painter == null) { throw new IllegalStateException(); }
    Animator returnValue = new SwingAnimator(_painter, "Traffic Simulator",
                                             _VP.getDisplayWidth(), _VP.getDisplayHeight(), _VP.getDisplayDelay());
    _painter = null;
    return returnValue;
  }

  public void addLight(Intersection d, int i, int j) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = new TranslatorWE(x, y, _MP.getCarLengthMax(), _VP.getElementWidth(), _VP.getScaleFactor());
    _painter.addLight(d,t);
  }
  public void addHorizontalRoad(Road l, int i, int j, boolean eastToWest) {
    double x = skipInit + j*skipRoadCar;
    double y = skipInit + skipRoad + i*skipRoadCar;
    Translator t = eastToWest ? new TranslatorEW(x, y, _MP.getRoadLengthMin(), _VP.getElementWidth(), _VP.getScaleFactor())
                              : new TranslatorWE(x, y, _MP.getRoadLengthMin(), _VP.getElementWidth(), _VP.getScaleFactor());
    _painter.addRoad(l,t);
  }
  public void addVerticalRoad(Road l, int i, int j, boolean southToNorth) {
    double x = skipInit + skipRoad + j*skipRoadCar;
    double y = skipInit + i*skipRoadCar;
    Translator t = southToNorth ? new TranslatorSN(x, y, _MP.getRoadLengthMin(), _VP.getElementWidth(), _VP.getScaleFactor())
                                : new TranslatorNS(x, y, _MP.getRoadLengthMin(), _VP.getElementWidth(), _VP.getScaleFactor());
    _painter.addRoad(l,t);
  }

  /** Class for drawing the Model. */
  private static class MyPainter implements SwingAnimatorPainter {

    /** Pair of a model element <code>x</code> and a translator <code>t</code>. */
    private static class Element<T> {
      T x;
      Translator t;
      Element(T x, Translator t) {
        this.x = x;
        this.t = t;
      }
    }
    
    private List<Element<Road>> _roadElements;
    private List<Element<Intersection>> _lightElements;
    MyPainter() {
      _roadElements = new ArrayList<Element<Road>>();
      _lightElements = new ArrayList<Element<Intersection>>();
    }    
    void addLight(Intersection x, Translator t) {
      _lightElements.add(new Element<Intersection>(x,t));
    }
    void addRoad(Road x, Translator t) {
      _roadElements.add(new Element<Road>(x,t));
    }

    
    public void paint(Graphics g) {
      // This method is called by the swing thread, so may be called
      // at any time during execution...

      // First draw the background elements
      for (Element<Intersection> e : _lightElements) {
        if (e.x.getLight().getLightState().equals(LightState.GreenNSRedEW)) {
          g.setColor(Color.GREEN);
        } else if(e.x.getLight().getLightState().equals(LightState.YellowNSRedEW)) {
          g.setColor(Color.YELLOW);
        }
        else
        	g.setColor(Color.RED);
        XGraphics.fillOval(g, e.t, 0, 0, _MP.getCarLengthMax(), _VP.getElementWidth());
      }
      g.setColor(Color.BLACK);
      for (Element<Road> e : _roadElements) {
        XGraphics.fillRect(g, e.t, 0, 0, _MP.getRoadLengthMin(), _VP.getElementWidth());
      }
      
      // Then draw the foreground elements
      for (Element<Road> e : _roadElements) {
        // iterate through a copy because e.x.getCars() may change during iteration...
        for (Car d : e.x.getCars().toArray(new Car[0])) {
          g.setColor(d.getColor());
          double r = (_MP.getRoadLengthMin()/d.getCurrentRoad().getEndPosition());
          XGraphics.fillOval(g, e.t, (d.getFrontPosition()*r)-(d.getCarLength()*r), 0, _MP.getCarLengthMax(), _VP.getElementWidth());
        }
      }
    }
  }
}

