package model;






/**
 * A car remembers its position from the beginning of its road.
 * Cars have random carVelocity and random movement pattern. 
 * 
 */
public class Car implements Agent {
	private double brakeDistance; 
	private java.awt.Color _color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255)); 
	private CarAcceptor currentRoad;
	private double frontPosition;
	private boolean horizontal;
	private double carLength;
	private double maxVelocity;
	private MP _MP;
	private double stopDistance; 
	private double carVelocity; 
		
     /**
      * Constructor creates a new car object, and sets its Length, max Velocity, Brake distance, and stop distance. 
      * @param h tells the car if it is on a horizontal road or vertical road. 
      * @param m gives the car access to all the user selected parameters. 
      */
 	Car(boolean h, MP m){
 		horizontal = h;
 		_MP = m;
 		carLength = _MP.createRandom(_MP.getCarLengthMin(), _MP.getCarLengthMax());
 		maxVelocity = _MP.createRandom(_MP.getMaxVelocityMin(),_MP.getMaxVelocityMax());
 		brakeDistance = _MP.createRandom(_MP.getBrakeDistanceMin(), _MP.getBrakeDistanceMax());
 		stopDistance = _MP.createRandom(_MP.getStopDistanceMin(), _MP.getStopDistanceMax());
	}   
 	
	/**
	 * calls the current {@link CarAcceptor} that is holding the Car object, and determines the new <code>velocity</code> based on the location of
	 * obstacles in front of the car object. 
	 * @return the new velocity of the car object. 
	 */
	private double changeVelocity(){
		double distanceToObstacle = currentRoad.distanceToObstacle(frontPosition, horizontal);
		if(distanceToObstacle == Double.POSITIVE_INFINITY)
			return maxVelocity*_MP.getTimeStep() + this.frontPosition;
		
		if((distanceToObstacle > brakeDistance||distanceToObstacle > stopDistance) && distanceToObstacle < maxVelocity){
			carVelocity = distanceToObstacle/2;
		while(carVelocity > distanceToObstacle)carVelocity = distanceToObstacle/2;}
		else if(distanceToObstacle == 0)carVelocity = 0;
		else{
			carVelocity =(maxVelocity/(brakeDistance - stopDistance)*(distanceToObstacle - stopDistance));}
		carVelocity = Math.max(0.0, carVelocity);
		carVelocity = Math.min(maxVelocity, carVelocity);
		return frontPosition + Math.round(carVelocity*_MP.getTimeStep());
		 
	}
	
	/**
	 * calls the <code>changeVelocity</code> method and uses the new <code>velocity</code> to set the new <code>frontPositon</code>.
	 */
	public void run() {
		setFrontPosition(changeVelocity());
	}
	
	/**
	 * 
	 * @return the current CarAcceptor the Car object is located on. 
	 */
 	public CarAcceptor getCurrentRoad(){
 		return currentRoad;
 	}
 	
 	/**
 	 * 
 	 * @return the length of the current Car object.
 	 */
	public double getCarLength(){
		return carLength;
	}
	
	/**
	 * 
	 * @return the front position of the car object. 
	 */
    public double getFrontPosition(){
    	 return frontPosition;
     }
    
     /**
      * 
      * @return the velocity of the Car object.  
      */
     public double getcarVelocity(){
    	 return carVelocity;
     }
     

     /**
      * 
      * @return the front position of the Car object. 
      */
     public double getPosition() {
		    return frontPosition;
     }
     
     /**
      * 
      * @return the visual color of the Car object. 
      */
	 public java.awt.Color getColor() {
		    return _color;
	 }
	 
	 /**
	  * sets the <code>currentRoad</code> to r. 
	  * @param r the new CarAcceptor to hold the Car object. 
	  * @throws IllegalArgumentException()
	  */
	public void setCurrentRoad(CarAcceptor r){
		if(r == null)throw new IllegalArgumentException();
		currentRoad = r;
	}
	
	/**
	 * 
	 * @return the stopDistance of the Car object.
	 */
	public double getStopDistance(){
		return stopDistance;
	}
	
	/**
	 * 
	 * @return the brakDistance of the Car object.
	 */
	public double getBrakeDistance(){
		return brakeDistance;
	}
	
	/**
	 * Sets the car object to a new front position. 
	 * @param f the new front position for the Car object. 
	 * @throws IllegalArgumentException();
	 */
	public void setFrontPosition(double f){
		if(f < 0)throw new IllegalArgumentException();
		double end = currentRoad.getEndPosition();
		if(f > end){
			currentRoad.remove(this);
			currentRoad.getNext(this.horizontal).accept(this, f - end);
		}
		else{
		frontPosition = f;
		}
	}
	
	/**
	 * calculates the backPosition of the Car object. 
	 * @return the back positon of the Car object. 
	 */
	public double backPosition() {
		return frontPosition-carLength;
	}

	/**
	 * 
	 * @return the orientation of the Car object. 
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * sets the new orientation of the Car object.
	 * @param horizontal the new orientation of the Car object.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}


}
