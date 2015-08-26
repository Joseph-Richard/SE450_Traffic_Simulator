package model;





/**
 * A light has a {@link LightState} that determines which cars can pass through an {@link Intersection} Object. 
 */
public class Light implements Agent {
	private LightState light;
	private MP _MP;
	private TimeServer time;
	private double greenDuration;
	private double yellowDuration;
	Light(MP m) {
		light = LightState.GreenNSRedEW;
		_MP = m;
		time = _MP.getLifeTime();
		yellowDuration = _MP.createRandom(_MP.getYellowDurationMin(), _MP.getYellowDurationMax());
		greenDuration = _MP.createRandom(_MP.getGreenDurationMin(), _MP.getGreenDurationMax());
		time.enqueue(time.currentTime()+greenDuration, this);
	  } 
  
	/**
	 * Depending on the current state of the Light Object, the {@link LightState} is changed, and the Light is reEnqueued. 
	 */
	public void run() {
		switch(light.toString()){
	    	case "GreenNSRedEW":
	    		light = LightState.YellowNSRedEW;
	    		time.enqueue(time.currentTime()+this.yellowDuration, this);
	    		break;
	    	case "YellowNSRedEW":
	    		light = LightState.RedNSGreenEW;
	    		time.enqueue(time.currentTime()+this.greenDuration, this);
	    		break;
	    	case "RedNSGreenEW":
	    		light = LightState.RedNSYellowEW;
	    		time.enqueue(time.currentTime()+this.yellowDuration, this);
	    		break;
	    	case "RedNSYellowEW":
	    		light = LightState.GreenNSRedEW;
	    		time.enqueue(time.currentTime()+this.greenDuration, this);
	    		break;
	   }
  }
	/**
	 * 
	 * @return The current {@link LightState} of the Light. 
	 */
  public LightState getLightState(){
  	return light;
  }

	public void setLightState(LightState l) {
		light = l;
		
	}
  
}
  