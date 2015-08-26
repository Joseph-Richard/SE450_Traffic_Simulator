package model;

import model.Car;


import junit.framework.Assert;
import junit.framework.TestCase;
/**
 * A test class of the {@link Car} Object class. 
 * @author Joseph Richard
 *
 */
@SuppressWarnings("deprecation")
public class CarTEST extends TestCase {
	  public CarTEST(String name) {
		    super(name);
		  }
	  public void testCar(){
		  MP mp = MP.getInstance();
		  
		  boolean horizontal = true; 
		  Road r1 = ComponentFactory.newRoad(horizontal, mp);
		  Car c = ComponentFactory.newCar(horizontal,mp);
		  c.setCurrentRoad(r1);
		  Assert.assertEquals(c.getCurrentRoad(),r1);
		  c.setFrontPosition(5.0);
		  Assert.assertEquals(c.getFrontPosition(), 5.0);
		  c.setHorizontal(false);
		  Assert.assertEquals(c.isHorizontal(), false);
		  try{c.setFrontPosition(-5);fail();}catch(IllegalArgumentException e){}
		  r1 = null;
		  try{c.setCurrentRoad(r1);;fail();}catch(IllegalArgumentException e){}
	  }
}
