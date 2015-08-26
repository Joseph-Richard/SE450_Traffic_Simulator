package model;

import java.util.ArrayList;
/**
 * An interface for all Objects that can accept Car objects. 
 * @author Joseph Richard
 *
 */
public interface CarAcceptor { 
	/**
	 * 
	 * @param c The {@link Car} Object to be accepted into the CarAcceptor. 
	 * @param d The position of the {@link Car} Object in the CarAcceptor.  
	 * @return
	 */
	public boolean accept(Car c, double d);
	/**
	 * Returns the distance to the closest obstacle from <code>frontPosition</code>.
	 * @param frontPosition The front position of the current {@link Car} Object. 
	 * @param h The current orientation of the CarAcceptor. 
	 * @return the distance to the closest obstacle from <code>frontPosition</code>.
	 */
	public double distanceToObstacle(double frontPosition, boolean h);
	/**
	 * gets the list of {@link Car} Objects currently in the CarAcceptor. 
	 * @return a list of {@link Car}Objects in the CarAcceptor. 
	 */
	public ArrayList<Car> getCars();
	/**
	 * returns the end Position of the current CarAcceptor. 
	 * @return the end Position of the current CarAcceptor.
	 */
	public double getEndPosition();
	
	/** removes the <code>car</code>Object from the CarAcceptor.
	 * @param car the {@link Car}Object to be removed.
	 */
	public void remove(Car car);
	
	/** returns the next Connected CarAcceptor Object.
	 * @param horizontal The orientation of the next CarAcceptor.
	 * @return The next connected CarAcceptor Object.
	 */
	public CarAcceptor getNext(boolean horizontal);
	
	/** returns the {@link TimeServer} Object.
	 * @param horizontal The orientation of the CarAcceptor.
	 * @return The next {@link TimeServer} Object.
	 */
	public TimeServer getTimeServer(boolean h);
	/**
	 * returns true if the current CarAcceptor is horizontal. false otherwise.
	 * @param horizontal parameter for intersection CarAcceptors
	 * @return
	 */
	public boolean isHorizontal(boolean horizontal);
	}
