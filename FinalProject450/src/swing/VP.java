package swing;

import model.MP;

/**
 * Static class for visualization parameters.
 */
final public class VP {
	private static MP _MP;
	private static  VP instance;
	/** Width of model elements, in meters */
	 private  double elementWidth;
	  /** Gap between model elements, in meters */
	 private  double gap = 1;
	  /** Width of the displayed graphics window, in pixels */
	  private  int displayWidth = 1500;
	  /** Height of the displayed graphics window, in pixels */
	  private int displayHeight = 1500;
	  /** Delay introduced into each graphics update, in milliseconds */
	  private int displayDelay = 50;
	  /** Scalefactor relating model space to pixels, in pixels/meter */
	  private double scaleFactor = 1;
  private VP(MP m) {
	  _MP = m;
	  elementWidth =_MP.getBrakeDistanceMax();
  }
  public  int getDisplayHeight() {
	return displayHeight;
}
  public static  VP getInstance(MP m){
		if(instance==null){
			instance = new VP(m);
		}
		return instance; 
	}
public  void setDisplayHeight(int _displayHeight) {
	displayHeight = _displayHeight;
}
public  int getDisplayWidth() {
	return displayWidth;
}
public  void setDisplayWidth(int _displayWidth) {
	displayWidth = _displayWidth;
}
public  double getElementWidth() {
	return elementWidth;
}
public  void setElementWidth(double _elementWidth) {
	elementWidth = _elementWidth;
}
public  double getGap() {
	return gap;
}
public  void setGap(double _gap) {
	gap = _gap;
}
public  int getDisplayDelay() {
	return displayDelay;
}
public  void setDisplayDelay(int _displayDelay) {
	displayDelay = _displayDelay;
}
public  double getScaleFactor() {
	return scaleFactor;
}
public  void setScaleFactor(double _scaleFactor) {
	scaleFactor = _scaleFactor;
}

}
