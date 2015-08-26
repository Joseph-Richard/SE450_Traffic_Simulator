package model;

import java.util.ArrayList;


/**
 * A road holds cars. when it is run, it runs all the {@link Car} Objects it is holding and then re-enqueues itself.
 */


public class Road implements CarAcceptor, Agent {
	  ArrayList<Car> cars = new ArrayList<Car>();
	  double endPosition;
	  private boolean horizontal;
	  private MP _MP;
	  private CarAcceptor _next; 
	  private TimeServer time;

	  /**
	   * Creates a new road object
	   * @param h Orientation of the created road
	   * @param m User defined parameters
	   */
	  Road(boolean h, MP m){
		  time = m.getLifeTime();
		  horizontal = h; 
		  _MP = m;
		  endPosition = _MP.createRandom(_MP.getRoadLengthMin(), _MP.getRoadLengthMax());
	  }
	  /**
	   * Cycles through all the {@link Car} Objects it is holding, and runs them. The road then re-enqueues itself. 
	   */
	  public void run() {
		  for(Car c: this.getCars()){
			c.run();
		  }
		  if(!this.getCars().isEmpty())
			time.enqueue(time.currentTime()+_MP.getTimeStep(), this);
			
	  }
	  
	  
	  /**Accepts a new {@link Car} object and places it at <code>frontPositon</code>
	   * @param c the new Car object.
	   * @param frontPositon the front position of <code>c</code>
	   * @return True if accept succeed 
	   */
	  public boolean accept(Car c, double frontPosition) {
		  if(c.isHorizontal() != horizontal)throw new NullPointerException();
		  cars.remove(c);
		  	if(frontPosition>endPosition) {
		      return _next.accept(c,frontPosition-endPosition);
		    } else {
		      c.setCurrentRoad(this);
		      c.setFrontPosition(frontPosition);
		      cars.add(c);
		      time.enqueue(time.currentTime()+_MP.getTimeStep(), this);
		      return true;
		    }
	  }
	  /**
	   * Finds the closest obstacle in front of <code>fromPosition</code>
	   * @param fromPositon The beginning point being measured 
	   * @param h The orientation of the position being measured
	   * @return The distance to the closest obstacle from <code>fromPosition</code>
	   */
	  public double distanceToObstacle(double fromPosition, boolean h) {

		    double obstaclePosition = this.distanceToCarBack(fromPosition);
		    double distanceToEnd = 0;
		    if (obstaclePosition == Double.POSITIVE_INFINITY) {
		      distanceToEnd = endPosition -fromPosition;
		    	  obstaclePosition = _next.distanceToObstacle(fromPosition-endPosition, h)+distanceToEnd;
		    	  return obstaclePosition;
		   }
		   return obstaclePosition-fromPosition;
	  }
	  
	  /**
	   * sets the next connected CarAcceptor 
	   * @param c The next CarAcceptor 
	   * @return True if <code>c</code> is set. 
	   */
	  public boolean setNext(CarAcceptor c){
		  if(c.isHorizontal(horizontal) != horizontal)throw new IllegalArgumentException();
		  _next = c;
		return true; 
	  }
	  
	  /**
	   * @param h The orientation of the next CarAcceptor Object. 
	   * @return The next connected CarAcceptor
	   */
	  public CarAcceptor getNext(boolean h){
		  return _next;
	  }
	  /**
	   * @param h the orientation of the CarAcceptor. 
	   * @return The local location of the TimeServer
	   */
	  public TimeServer getTimeServer(boolean h){
		  return time;
	  }
	  
	  /**
	   * finds the closest Car object to <code>fromPosition</code>
	   * @param fromPosition The beginning measure point 
	   * @return the closest Car object to <code>fromPosition</code>
	   */
	  private double distanceToCarBack(double fromPosition) {
		  double carBackPosition = Double.POSITIVE_INFINITY;
		  for (Car c : cars)
			  if (c.backPosition() >= fromPosition && c.backPosition() < carBackPosition)
				  carBackPosition = c.backPosition();
		  return carBackPosition;
	  }
	
	/**
	 * @return A local clone of the list of Car objects in the Road.  
	 */
	  @SuppressWarnings("unchecked")
	public ArrayList<Car> getCars(){
		return (ArrayList<Car>)cars.clone();
	}
	 /**
	  * Removes a selected Car object from the road.
	  * @param c The Car object to be removed
	  */
	public void remove(Car c){
		this.cars.remove(c);
	}
	  

	/**
	 * @return The total length of the Road
	 */
	public double getEndPosition(){
		return endPosition;
	}
	/**
	 * Sets the orientation of the Road object. 
	 * @param horizontal the new orientation of the Road
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	/**
	 * @param h (needed for intersection objects)
	 * @return The orientation of the Road object. 
	 */
	public boolean isHorizontal(boolean h){
		return horizontal;
	}


}