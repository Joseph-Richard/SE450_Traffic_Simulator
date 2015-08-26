package model;

import java.util.ArrayList;
/**
 * A sink is the end point of the last CarAcceptor. It catches all of the {@link Car} Objects and removes them. 
 * @author Joseph Richard
 *
 */
public class Sink implements CarAcceptor {
	/**
	 * creates a new Sink object. 
	 */
	Sink(){};
	/**
	 * Accepts ad destroys {@link Car} objects.
	 * @param c Car to be destroyed. 
	 * @param d (needed for other CarAcceptors)
	 * @return True 
	 */
	public boolean accept(Car c, double d) {
		c = null;
		return true;
	}
	
	/**
	 * Always returns null
	 */
	public CarAcceptor getNext(boolean h) {
		return null;
	}
	/**
	 * always returns the maximum distance possible. 
	 */
	public double distanceToObstacle(double fromPosition, boolean h) {
		return Double.POSITIVE_INFINITY;
	}
	/**
	 * always returns null
	 */
	public ArrayList<Car> getCars(){
		return null;
	}
	/**
	 * A sink has no length
	 */
	public double getEndPosition()  {
		return 0;
	}
	/**
	 * Destroys any car it is given
	 * @param car The car to be destroyed 
	 */
	public void remove(Car car){
		car = null;
	}

	/**
	 * Always returns null
	 */
	public TimeServer getTimeServer(boolean h) {
		return null;
	}

	/**
	 * @param h (needed for Intersection Object)
	 * @return the orientation of the Sink object 
	 */
	@Override
	public boolean isHorizontal(boolean h) {
		// TODO Auto-generated method stub
		if(h)return true;
		return false;
	}


}
