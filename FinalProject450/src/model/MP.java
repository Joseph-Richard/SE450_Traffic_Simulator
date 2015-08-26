package model;

/**
 * A singleton class that holds all of the user set parameters for the Traffic Simulator. 
 * @author Joseph Richard
 *
 */
final public class MP {
	private static  MP instance; 
	private  TimeServer lifeTime;

	
	private double timeStep;//This indicates how much model time elapses between each simulation step.  
	
	private int runTime;//the length of the simulation in model seconds.
	
	private  int rows; //number of rows for the grid. 
	private  int columns;//number of columns for the grid. 
	
	private boolean alternating = false;// this determines whether the road is alternating or not.  
	
	private double sourceTimeMin;//this indicates the minimum time that elapses between car creations.
	private double sourceTimeMax;//this indicates the the maximum time that elapses between car creations. 
	
	private double roadLengthMin; //min length of road segments, in meters.
	private double roadLengthMax;//max length of road segments, in meters.
	
	private double intersectionLengthMin;//min length of intersections in meters. 
	private double intersectionLengthMax;//max length of intersection, in meters.
	
	private double carLengthMin; //minimum length of cars, in meters.
	private double carLengthMax; //maximum length of cars, in meters.
	
	private double maxVelocityMin;//min for top car velocity, in meters/second.
	private double maxVelocityMax;//Max for top car velocity, in meters/second.
	
	private double stopDistanceMin;//min distance away when car must stop, in meters; 
	private double stopDistanceMax;//max distance away when car must stop, in meters;
	
	private double brakeDistanceMin; //min distance away when car must start breaking, in meters. 
	private double brakeDistanceMax;//max distance away when car must start breaking, in meters.
	
	private double greenDurationMin;//min green light durations for intersections, in seconds.
	private double greenDurationMax; //max greenlight durations for intersections, in seconds. 
	
	private double yellowDurationMin;//Min yellow light durations for intersections, in seconds.
	private double yellowDurationMax;//Max yellow light durations for intersections, in seconds.

	
	/**
	 * Creates a new MP class, and sets all of the parameters with default values. 
	 */
	private MP(){
		timeStep = 0.1;
		runTime = 20;
		rows = 1; 
		columns = 1;
		alternating = true;
		sourceTimeMin = 2.0;
		sourceTimeMax = 5.0;
		
		roadLengthMin = 100.0;
		roadLengthMax = 110.0;
		
		intersectionLengthMin = 10.0;
		intersectionLengthMax = 15.0;
		
		carLengthMin = 5.0;
		carLengthMax = 10.0;
		
		maxVelocityMin = 20.0; 
		maxVelocityMax = 30.0;
		
		stopDistanceMin = 0.5;
		stopDistanceMax = 5.0;
		
		brakeDistanceMin = 9.0;
		brakeDistanceMax = 10.0;
		
		greenDurationMin = 3.0; 
		greenDurationMax = 5.0;
		
		yellowDurationMin = 1.0;
		yellowDurationMax = 2.0;
		
	}
	/**
	 * Creates a new MP class or returns the current MP class if one already exists. 
	 * @return The MP instance.
	 */
	public static  MP getInstance(){
		if(instance==null){
			instance = new MP();
		}
		return instance; 
	}
	
	/**
	 * @return a String with all the values of MP listed in order, with their Minimum and Maximum ranges when necessary. 
	 */
	public String toString() {
	    StringBuilder buffer = new StringBuilder();
	    buffer.append("Simulation time step(seconds)["+timeStep+"]\n");
	    buffer.append("Simulation run time(seconds)["+runTime+"]\n");
	    buffer.append("Grid size (number of roads)[row="+rows+",columns="+columns+"]\n");
	    buffer.append("Alternating traffic["+alternating+"]\n");
	    buffer.append("Car entry rate(seconds/car)[min="+sourceTimeMin+",max="+sourceTimeMax+"]\n");
	    
	    buffer.append("Road segment length(meters)[min="+roadLengthMin+",max="+roadLengthMax+"]\n");
	    buffer.append("Intersection length(meters)[min="+intersectionLengthMin+",max="+intersectionLengthMax+"]\n");
	   
	    buffer.append("Car length(meters)[min="+carLengthMin+",max="+carLengthMax+"]\n");
	    
	    buffer.append("Car maximum velocity(meters/second)[min="+maxVelocityMin+",max="+maxVelocityMax+"]\n");
	    buffer.append("Car stop distance(meters)[min="+stopDistanceMin+",max="+stopDistanceMax+"]\n");
	    buffer.append("Car break distance(meters)[min="+brakeDistanceMin+",max="+brakeDistanceMax+"]\n");

	    buffer.append("Traffic light green time (seconds)[min="+greenDurationMin+",max="+greenDurationMax+"]\n");
	    buffer.append("Traffic light yellow time (seconds)[min="+yellowDurationMin+",max="+yellowDurationMax+"]\n");
	    


	    return buffer.toString();
	  }
	/**
	 * 
	 * @return the TimeServer in MP. 
	 */
	public TimeServer getLifeTime() {
		return this.lifeTime;
	}
	
	/**
	 * sets the TimeServer in MP to the Singleton {@link TimeServerQueue} instance. 
	 */
	public void setLifeTime() {
		this.lifeTime = TimeServerQueue.getInstance();
	}
	
	/**
	 * 
	 * @return the Time Step between actions in the simulator.
	 */
	public double getTimeStep() {
		return timeStep;
	}
	
	/**
	 * sets the time step variable to <code>timeStep</code>
	 * @param timeStep The new time step.
	 */
	public void setTimeStep(double timeStep) {
		if(timeStep < 0.01|| timeStep > this.getRunTime())throw new IllegalArgumentException();
		this.timeStep = timeStep;
	}
	
	/**
	 * 
	 * @return Traffic simulator runtime
	 */
	public int getRunTime() {
		return runTime;
	}
	
	/**
	 * Sets the runtime to <code>runTime</code>
	 * @param runTime the new run time. 
	 */
	public void setRunTime(int runTime) {
		if(runTime < 0.01)throw new IllegalArgumentException();
		this.runTime = runTime;
	}
	
	/**
	 * 
	 * @return The number of horizontal roads 
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Sets the number of rows to <code>rows</code>
	 * @param rows The new number of rows. 
	 */
	public void setRows(int rows) {
		if(rows<1)throw new IllegalArgumentException();
		this.rows = rows;
	}
	
	/**
	 * 
	 * @return The number of vertical roads. 
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * Sets the number of columns to <code>columns</code>
	 * @param columns The new number of columns. 
	 */
	public void setColumns(int columns) {
		if(columns < 1)throw new IllegalArgumentException();
		this.columns = columns;
	}
	
	/**
	 * 
	 * @return True if the roads are alternating or not
	 */
	public boolean isAlternating() {
		return alternating;
	}
	
	/**
	 * Sets the roads alternating if <code>alternating</code> is true.
	 * @param alternating the new boolean value 
	 */
	public void setAlternating(boolean alternating) {
		this.alternating = alternating;
	}
	
	/**
	 * 
	 * @return Minimum time between car creations. 
	 */
	public double getSourceTimeMin() {
		return sourceTimeMin;
	}
	
	/**
	 * Sets the minimum source time  <code>sourceTimeMin</code>
	 * @param sourceTimeMin The new min value. 
	 */
	public void setSourceTimeMin(double sourceTimeMin) {
		if(sourceTimeMin < 0.01)throw new IllegalArgumentException();
		this.sourceTimeMin = sourceTimeMin;
	}
	
	/**
	 * 
	 * @return Maximum time between car creations. 
	 */
	public double getSourceTimeMax() {
		return sourceTimeMax;
	}
	
	/**
	 * Sets the maximum source time  <code>sourceTimeMax</code>
	 * @param sourceTimeMax The new max value. 
	 */
	public void setSourceTimeMax(double sourceTimeMax) {
		if(sourceTimeMax <= this.getSourceTimeMin())throw new IllegalArgumentException();
		this.sourceTimeMax = sourceTimeMax;
	}
	
	/**
	 * 
	 * @return Minimum Road Length.
	 */
	public double getRoadLengthMin() {
		return roadLengthMin;
	}
	
	/**
	 * Sets the minimum road length to  <code>roadLengthMin</code>
	 * @param roadLengthMin The new min value. 
	 */
	public void setRoadLengthMin(double roadLengthMin) {
		if(roadLengthMin < 0.01)throw new IllegalArgumentException();
		this.roadLengthMin = roadLengthMin;
	}
	
	/**
	 * 
	 * @return the Maximum Road Length. 
	 */
	public double getRoadLengthMax() {
		return roadLengthMax;
	}
	
	/**
	 * Sets the maximum road length to <code>roadLengthMax</code>
	 * @param roadLengthMax The new max value. 
	 */
	public void setRoadLengthMax(double roadLengthMax) {
		if(roadLengthMax <= this.getRoadLengthMin())throw new IllegalArgumentException();
		this.roadLengthMax = roadLengthMax;
	}
	
	/**
	 * 
	 * @return Minimum Intersection Length
	 */
	public double getIntersectionLengthMin() {
		return intersectionLengthMin;
	}
	
	/**
	 * Sets the minimum intersection length to <code>intersectionLengthMin</code>
	 * @param intersectionLengthMin The new min value. 
	 */
	public void setIntersectionLengthMin(double intersectionLengthMin) {
		if(intersectionLengthMin < 0.01)throw new IllegalArgumentException();
		this.intersectionLengthMin = intersectionLengthMin;
	}
	
	/**
	 * 
	 * @return Maximum Intersection length
	 */
	public double getIntersectionLengthMax() {
		return intersectionLengthMax;
	}
	
	/**
	 * Sets the maximum intersection length to <code>intersectionLengthMax</code>
	 * @param intersectionLengthMax The new max value. 
	 */
	public void setIntersectionLengthMax(double intersectionLengthMax) {
		if(intersectionLengthMax <= this.getIntersectionLengthMin())throw new IllegalArgumentException();
		this.intersectionLengthMax = intersectionLengthMax;
	}
	
	/**
	 * 
	 * @return Minimum car length
	 */
	public double getCarLengthMin() {
		return carLengthMin;
	}
	
	/**
	 * Sets the minimum car length to <code>carLengthMin</code>
	 * @param carLengthMin The new min value. 
	 */
	public void setCarLengthMin(double carLengthMin) {
		if(carLengthMin< 0.01)throw new IllegalArgumentException();
		this.carLengthMin = carLengthMin;
	}
	
	/**
	 * 
	 * @return Maximum car length
	 */
	public double getCarLengthMax() {
		return carLengthMax;
	}
	
	/**
	 * Sets the maximum car length to <code>carLengthMax</code>
	 * @param carLengthMax The new max value. 
	 */
	public void setCarLengthMax(double carLengthMax) {
		if(carLengthMax <= this.getCarLengthMin())throw new IllegalArgumentException();
		this.carLengthMax = carLengthMax;
	}
	
	/**
	 * 
	 * @return Minimum end of the maximum car velocity
	 */
	public double getMaxVelocityMin() {
		return maxVelocityMin;
	}
	
	/**
	 * Sets the minimum max car velocity to <code>maxVelocityMin</code>
	 * @param maxVelocityMin The new min value. 
	 */
	public void setMaxVelocityMin(double maxVelocityMin) {
		if(maxVelocityMin < 0.01)throw new IllegalArgumentException();
		this.maxVelocityMin = maxVelocityMin;
	}
	
	/**
	 * 
	 * @return Maximum end of the maximum car velocity
	 */
	public double getMaxVelocityMax() {
		return maxVelocityMax;
	}
	
	/**
	 * Sets the maximum max car velocity to <code>maxVelocityMax</code>
	 * @param maxVelocityMax The new max value. 
	 */
	public void setMaxVelocityMax(double maxVelocityMax) {
		if( maxVelocityMax <= this.getMaxVelocityMin())throw new IllegalArgumentException();
		this.maxVelocityMax = maxVelocityMax;
	}
	
	/**
	 * 
	 * @return Minimum car stop distance
	 */
	public double getStopDistanceMin() {
		return stopDistanceMin;
	}
	
	/**
	 * Sets the minimum car stop distance to <code>stopDistanceMin</code>
	 * @param stopDistanceMin The new min value. 
	 */
	public void setStopDistanceMin(double stopDistanceMin) {
		if(stopDistanceMin < 0.01)throw new IllegalArgumentException();
		this.stopDistanceMin = stopDistanceMin;
	}
	
	/**
	 * 
	 * @return Maximum car stop distance
	 */
	public double getStopDistanceMax() {
		return stopDistanceMax;
	}
	
	/**
	 * Sets the maximum car stop distance to <code>stopDistanceMax</code>
	 * @param stopDistanceMax The new max value. 
	 */
	public void setStopDistanceMax(double stopDistanceMax) {
		if(stopDistanceMax <= this.getStopDistanceMin())throw new IllegalArgumentException();
		this.stopDistanceMax = stopDistanceMax;
	}
	
	/**
	 * 
	 * @return Minimum car brake distance
	 */
	public double getBrakeDistanceMin() {
		return brakeDistanceMin;
	}
	
	/**
	 * Sets the minimum car brake distance to <code>brakeDistanceMin</code>
	 * @param brakeDistanceMin The new min value. 
	 */
	public void setBrakeDistanceMin(double brakeDistanceMin) {
		if(brakeDistanceMin < 0.01)throw new IllegalArgumentException();
		this.brakeDistanceMin = brakeDistanceMin;
	}
	
	/**
	 * 
	 * @return Maximum car brake distance
	 */
	public double getBrakeDistanceMax() {
		return brakeDistanceMax;
	}
	
	/**
	 * Sets the maximum car brake distance to <code>brakeDistanceMax</code>
	 * @param brakeDistanceMax The new max value. 
	 */
	public void setBrakeDistanceMax(double brakeDistanceMax) {
		if(brakeDistanceMax <= this.getBrakeDistanceMin())throw new IllegalArgumentException();
		this.brakeDistanceMax = brakeDistanceMax;
	}
	
	/**
	 * 
	 * @return Minimum green light duration
	 */
	public double getGreenDurationMin() {
		return greenDurationMin;
	}
	
	/**
	 * Sets the minimum green light duration to <code>greenDurationMin</code>
	 * @param greenDurationMin The new min value. 
	 */
	public void setGreenDurationMin(double greenDurationMin) {
		if(greenDurationMin < 0.01)throw new IllegalArgumentException();
		this.greenDurationMin = greenDurationMin;
	}
	
	/**
	 * 
	 * @return Maximum green light duration
	 */
	public double getGreenDurationMax() {
		return greenDurationMax;
	}
	
	/**
	 * Sets the maximum green light duration to <code>greenDurationMax</code>
	 * @param greenDurationMax The new max value. 
	 */
	public void setGreenDurationMax(double greenDurationMax) {
		if(greenDurationMax <= this.getGreenDurationMin())throw new IllegalArgumentException();
		this.greenDurationMax = greenDurationMax;
	}
	
	/**
	 * 
	 * @return Minimum yellow light duration
	 */
	public double getYellowDurationMin() {
		return yellowDurationMin;
	}
	
	/**
	 * Sets the minimum yellow light duration to <code>yellowDurationMin</code>
	 * @param yellowDurationMin The new min value. 
	 */
	public void setYellowDurationMin(double yellowDurationMin) {
		if(yellowDurationMin < 0.01)throw new IllegalArgumentException();
		this.yellowDurationMin = yellowDurationMin;
	}
	
	/**
	 * 
	 * @return Maximum yellow light duration
	 */
	public double getYellowDurationMax() {
		return yellowDurationMax;
	}
	
	/**
	 * Sets the maximum yellow light duration to <code>yellowDurationMax</code>
	 * @param yellowDurationMax The new max value. 
	 */
	public void setYellowDurationMax(double yellowDurationMax) {
		if(yellowDurationMax <= this.getYellowDurationMin())throw new IllegalArgumentException();
		this.yellowDurationMax = yellowDurationMax;
	}
	
	/**
	 * 
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return Random number between <code>min</code> and <code>max</code>
	 */
	public double createRandom(double min, double max){
		return min+(int)(Math.random() * ((max - min) + 1));
	}
}
