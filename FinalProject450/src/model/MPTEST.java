package model;

import junit.framework.TestCase;

public class MPTEST extends TestCase {
	public MPTEST(String name){
		super(name);
	}
	public void testMP(){
		MP mp = MP.getInstance();
		
		//Brake Distance
		try{mp.setBrakeDistanceMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setBrakeDistanceMin(3.0);
		try{mp.setBrakeDistanceMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setBrakeDistanceMax(4.0);
		
		//Stop Distance
		try{mp.setStopDistanceMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setStopDistanceMin(3.0);
		try{mp.setStopDistanceMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setStopDistanceMax(4.0);
		
		//Source Time
		try{mp.setSourceTimeMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setSourceTimeMin(3.0);
		try{mp.setSourceTimeMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setSourceTimeMax(4.0);
		
		//Road length
		try{mp.setRoadLengthMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setRoadLengthMin(3.0);
		try{mp.setRoadLengthMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setRoadLengthMax(4.0);
		
		//Intersection Length
		try{mp.setIntersectionLengthMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setIntersectionLengthMin(3.0);
		try{mp.setIntersectionLengthMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setIntersectionLengthMax(4.0);
		
		//Car Length
		try{mp.setCarLengthMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setCarLengthMin(3.0);
		try{mp.setCarLengthMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setCarLengthMax(4.0);
		
		//Max Velocity
		try{mp.setMaxVelocityMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setMaxVelocityMin(3.0);
		try{mp.setMaxVelocityMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setMaxVelocityMax(4.0);
		
		//Green Duration
		try{mp.setGreenDurationMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setGreenDurationMin(3.0);
		try{mp.setGreenDurationMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setGreenDurationMax(4.0);
		
		//Yellow Duration
		try{mp.setYellowDurationMin(0);fail();}catch(IllegalArgumentException e){}
		mp.setYellowDurationMin(3.0);
		try{mp.setYellowDurationMax(2.0);fail();}catch(IllegalArgumentException e){}
		mp.setYellowDurationMax(4.0);
	}
}
