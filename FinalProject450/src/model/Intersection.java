package model;

import java.util.ArrayList;
/**
 * The Intersection is a crossroads between to CarAcceptor Objects running North/South, and 
 * two CarAcceptor Objects running East/West. The Intersection contains a {@link Light} Object
 * to control the visual changes to the intersection light. 
 * @author Joseph Richard
 *
 */
public class Intersection implements Agent, CarAcceptor {
	private double endPosition;
	private ArrayList<Car> carsNS;
	private ArrayList<Car> carsEW; 
	private Light light;
	private MP _MP;
	private CarAcceptor nextNS;
	private CarAcceptor nextEW;
	private TimeServer time;
/**
 * creates a new Intersection Object and sets up the local {@link TimeServer}.
 * @param m the MP parameters for the Intersection Object. 
 */
	Intersection(MP m){
		time = m.getLifeTime(); 
		light = ComponentFactory.newLight(m);
		carsNS = new ArrayList<Car>();
		carsEW = new ArrayList<Car>();
		_MP = m;
		endPosition = _MP.createRandom(_MP.getIntersectionLengthMin(), _MP.getIntersectionLengthMax());
		time.enqueue(time.currentTime()+_MP.getTimeStep(), this);
	}
	/**
	 * Calls the run method in all the {@link Car} Objects currently inside the Intersection Object. 
	 * It then ReEnqueues itself into the {@link TimeServer}.
	 */
	public void run() {
		if(!carsNS.isEmpty()){
			for(Car c: this.getCars(false)){c.run();}
		}
		if(!carsEW.isEmpty()){
			for(Car c: this.getCars(true)){c.run();}
		}
		time.enqueue(time.currentTime()+_MP.getTimeStep(), this);
	}
	/**
	 * Gets the local {@link Light} Object. 
	 * @return The local {@link Light} Object. 
	 */
	public Light getLight(){
		return light;
	}
	/**
	 * Sets the local {@link Light} Object to <code>light</code>.
	 * @param light The new {@link Light} Object. 
	 */
	public void setLight(Light light){
		this.light = light;
	}
	
	/** Puts the {@link Car} <code>c</code> into the Intersection Object. 
	 * @param c The {@link Car} Object to be accepted. 
	 * @param frontPosition The front position of <code>c</code>.
	 * @return True if the Accept worked, or false otherwise. 
	 */
	public boolean accept(Car c, double frontPosition) {
		if(c.isHorizontal()&&(light.getLightState()==LightState.RedNSGreenEW||light.getLightState() == LightState.RedNSYellowEW||c.getCurrentRoad().getEndPosition()>frontPosition)){
			if(frontPosition>endPosition) {
				boolean flag = false;
			 	for(Car e:this.getCars(true)){if(e.getFrontPosition()<=c.getCarLength()+c.getStopDistance())flag = true;}
		    	for(Car e:nextEW.getCars()){if(e.getFrontPosition()<= c.getCarLength() + c.getStopDistance())flag = true;}
		    	if(flag ==false)
		    		return nextEW.accept(c,frontPosition-endPosition);
		    } else{
		    	c.setCurrentRoad(this);
		    	c.setFrontPosition(frontPosition);
		    	carsEW.add(c);
		    	time.enqueue(time.currentTime()+_MP.getTimeStep(), this);
		    	return true;
		    }
		}
		else if(light.getLightState()==LightState.GreenNSRedEW||light.getLightState() == LightState.YellowNSRedEW||c.getCurrentRoad().getEndPosition()>frontPosition){
			if(frontPosition>endPosition){
				boolean flag = false;
				for(Car e:this.getCars(false)){if(e.getFrontPosition()<=c.getCarLength()+c.getStopDistance())flag = true;}
		    	for(Car e:nextNS.getCars()){if(e.getFrontPosition()<= c.getCarLength() + c.getStopDistance())flag = true;}
		    	if(flag ==false)
		    		return nextNS.accept(c, frontPosition - endPosition);
			} else{
				c.setCurrentRoad(this);
				c.setFrontPosition(frontPosition);
				carsNS.add(c);
				time.enqueue(time.currentTime()+_MP.getTimeStep(), this);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets the next vertical or horizontal {@link CarAcceptor} Object after the Intersection Object. 
	 * @param c The {@link CarAcceptor} Object to be set. 
	 * @param horizontal The orientation of <code>c</code>.
	 */
	public void setNext(CarAcceptor c, boolean horizontal){
		if(c == null)throw new NullPointerException();
		if(horizontal)nextEW = c;
		else nextNS = c;
	}
	
	/**Finds the closest obstacle ahead of <code>frontPosition</code> and returns it. 
	 * @param frontPosition The front position of the current{@link Car} Object. 
	 * @param horizontal The orientation of the current {@link Car} Object. 
	 * @return The distance to the closest obstacle ahead of <code>frontPositon</code>.
	 */
	public double distanceToObstacle(double frontPosition, boolean horizontal) {

		if(horizontal){
			if(light.getLightState()==LightState.RedNSGreenEW||light.getLightState()==LightState.RedNSYellowEW){
				double distanceToObstacle = this.distanceToCarBack(frontPosition, carsEW);
				if(distanceToObstacle == Double.POSITIVE_INFINITY){
					double endPoint = endPosition - frontPosition;
					distanceToObstacle = nextEW.distanceToObstacle(frontPosition, horizontal)+endPoint;
				}
				return distanceToObstacle - frontPosition;
			}
			else return 0.0;
		} else{
			if(light.getLightState()==LightState.GreenNSRedEW||light.getLightState()==LightState.YellowNSRedEW){
				double distanceToObstacle = this.distanceToCarBack(frontPosition, carsNS);
				if(distanceToObstacle == Double.POSITIVE_INFINITY){
					double endPoint = endPosition - frontPosition;
					distanceToObstacle = nextNS.distanceToObstacle(frontPosition, horizontal)+endPoint;
				}
				return distanceToObstacle - frontPosition;
			}
			else return 0.0;
		}
		
	}
	/**
	 * Finds the distance from <code>fromPosition</code> to all of the other {@link Car} Objects in the Intersection Object.
	 * @param fromPosition The position of a {@link Car} Object.
	 * @param cars A list of all other {@link Car} Objects in the Intersection Object.
	 * @return the of the closest {@link Car} Object if there is one ahead of <code>fromPosition</code>. 
	 */
	private double distanceToCarBack(double fromPosition, ArrayList<Car>cars) {
	    double carBackPosition = Double.POSITIVE_INFINITY;
	    for (Car c : cars)
	    	if (c.backPosition() >= fromPosition && c.backPosition() < carBackPosition)
	    		carBackPosition = c.backPosition();
	    return carBackPosition;
	}
	
	/**
	 * Returns a list of all the {@link Car} Objects inside the Intersection depending on the orientation <code>h</code>.
	 * @param h The orientation of the {@link Car} Objects to be returned. 
	 * @return a list of the {@link Car} Objects inside the Intersection Object depending on the orientation <code>h</code>.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Car> getCars(boolean h) {
		if(h)return (ArrayList<Car>)carsEW.clone();
		return (ArrayList<Car>)carsNS.clone();
	}
	
	public ArrayList<Car> getCars() {
		return null;
	}
	
	/**
	 * @return the EndPosition of the current Intersection Object. 
	 */
	public double getEndPosition() {

		return endPosition;
	}
	/**
	 * Removes the specified {@link Car} <code>car</code> from the Intersection.
	 * @param car The {@link Car} to be removed. 
	 */
	public void remove(Car car) {
		if(car.isHorizontal())carsEW.remove(car);
		else carsNS.remove(car);
		
	}
	
	/**
	 * Gets the next vertical or horizontal {@link CarAcceptor} Object after the Intersection.  
	 * @param h The orientation of the next @link CarAcceptor}.
	 * @return The next vertical or horizontal {@link CarAcceptor} Object after the Intersection. 
	 */
	public CarAcceptor getNext(boolean h){
		if(h)return nextEW;
		else return nextNS;
	}
	
	/**Returns the local TimeServer Object inside the Intersection. 
	 * @return The local TimeServer Object. 
	 */
	public TimeServer getTimeServer(boolean h) {
		return time;
	}
	
	/**
	 * Returns true if <code>h</code> is true, otherwise returns false.
	 * @param h The orientation of the previous {@link CarAcceptor}.
	 */
	public boolean isHorizontal(boolean h) {
		if(h)return true;
		return false;
	}
	/**
	 * Sets the local {@link Light Object} to a new state. 
	 * @param l The new lightState
	 */
	public void setLightState(LightState l){
		light.setLightState(l);
	}

}
