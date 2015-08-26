package model;

/**
 * The Enum class that takes care of all the possible combinations of light colors at in {@link Intersection}. 
 * @author Joseph Richard
 *
 */
public enum LightState {
		GreenNSRedEW{public String toString(){return "GreenNSRedEW";}} , 
		YellowNSRedEW{public String toString(){return "YellowNSRedEW";}},
		RedNSGreenEW{public String toString(){return "RedNSGreenEW";}},
		RedNSYellowEW{public String toString(){return "RedNSYellowEW";}}
}
