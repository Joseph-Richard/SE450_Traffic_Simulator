package model;

/**
 * A Factory class that creates all the necessary CarAcceptor and Agent Objects for the Traffic Simulation.
 * @author Joseph Richard
 *
 */
public class ComponentFactory {
	
	/**Creates a new Car Object with <code>horizontal</code> orientation. 
	 * @param horizontal The orientation of the Car Object.
	 * @param m The MP parameters for the Car Object.
	 * @return A new Car Object with <code>horizontal</code> orientation. 
	 */
	public static Car newCar(boolean horizontal, MP m){
		return new Car(horizontal, m);
	}
	/**Creates a new Road Object with <code>horizontal</code> orientation. 
	 * @param horizontal The orientation of the Road Object. 
	 * @param m The MP parameters for the Road Object.
	 * @return A new Road Object with <code>horizontal</code> orientation.
	 */
	public static Road newRoad(boolean horizontal, MP m){
		return new Road(horizontal, m);
	}
	
	/**Creates a new Light Object. 
	 * @param m The MP parameters for the Light Object.
	 * @return A new Light Object. 
	 */
	public static Light newLight(MP m){
		return new Light(m);
	}
	
	/**Creates a new Source Object with <code>horizontal</code> orientation. 
	 * @param horizontal The orientation of the Road Object. 
	 * @param m The MP parameters of the Source Object.
	 * @return A new Source Object with <code>horizontal</code> orientation.
	 */
	public static Source newSource(boolean horizontal, MP m){
		return new Source(horizontal, m);
	}
	
	/**Creates a new Intersection Object. 
	 * @param m The MP parameters of the Intersection Object. 
	 * @return A new Intersection Object.
	 */
	public static Intersection newIntersection(MP m){
		return new Intersection(m);
	}
	/**
	 * Creates a new Sink Object.
	 * @return A new Sink Object.
	 */
	public static Sink newSink(){
		return new Sink();
	}
}
