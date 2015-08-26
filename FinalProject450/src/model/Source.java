package model;

/**
 * Creates {@link Car} Objects and places them onto the next {@link CarAcceptor} Object. 
 * @author Admin
 *
 */
public class Source implements Agent {
    private boolean horizontal;
    private MP _MP;
    private double sourceTime;
	private CarAcceptor startRoad;
	private TimeServer time; 
	
	/**
	 * Creates a new source object
	 * @param h The orientation of the created Source
	 * @param m User defined parameters 
	 */
    Source(boolean h, MP m){
        horizontal = h;
        _MP = m;
        time = _MP.getLifeTime();
        sourceTime = _MP.createRandom(_MP.getSourceTimeMin(), _MP.getSourceTimeMax());
        time.enqueue(time.currentTime()+this.sourceTime, this);
    }
    
    /**
     * creates a new {@link Car} object whenever it is run 
     */
    public void run() {
    	Car c = ComponentFactory.newCar(horizontal, _MP);
    	boolean flag = false;
    	for(Car e :startRoad.getCars()){
    		if(e.getFrontPosition()<= c.getCarLength() + c.getStopDistance())
    			flag = true;
    	}
    	if(flag==false){
    		startRoad.accept(c, 0.0);
    	}
    	time.enqueue(time.currentTime()+this.sourceTime, this);
    }
  
    /**
     * 
     * @return The start road connected to the Source object
     */
    public CarAcceptor getStartRoad(){
    	return startRoad;
    }
  
    /**
     * 
     * @return the orientation of the Source object 
     */
    public boolean isHorizontal(){
    	return horizontal; 
    }
    
    /**
     * Sets the orientation of the Source object
     * @param b The new orientation of the Source object
     */
    public void setHoriztonal(boolean b){
    	horizontal = b; 
    }
    /**
     * 
     * @return The time step between car creations
     */
    public double getSourceTime(){
    	return sourceTime;
    }
    
    /**
     * Sets the new start road of the Source object.
     * @param c The new start road
     */
    public void setStartRoad(CarAcceptor c){
    	if(c== null)throw new NullPointerException();
    	if(c.isHorizontal(true) != horizontal)throw new IllegalArgumentException();
    	startRoad = c; 
    }
    
    
    
}
