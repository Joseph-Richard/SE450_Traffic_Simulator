package model;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * A test class for the {@link Road} Object class. 
 * @author Admin
 *
 */
@SuppressWarnings("deprecation")
public class RoadTEST extends TestCase {
	 public RoadTEST(String name) {
		    super(name);
		  }

	public void testRoad(){
		  MP mp = MP.getInstance();
		  mp.setLifeTime();
		  Car c1 = null;
		  Car c2 = ComponentFactory.newCar(true, mp);
		  Road r1 = ComponentFactory.newRoad(true,mp);
		  Road r2 = null;
		  Road r3 = ComponentFactory.newRoad(false, mp);
		  Road r4 = ComponentFactory.newRoad(true, mp);
		  Intersection i1 = ComponentFactory.newIntersection(mp);
		  
		  try{r1.accept(c1, 0);fail();}catch(NullPointerException e){}
		  try{r1.accept(c2, -5);fail();}catch(IllegalArgumentException e){}
		  Assert.assertTrue(r1.accept(c2, 5.0));
		  try{r1.setNext(r2);fail();}catch(NullPointerException e){}
		  try{r1.setNext(r3);fail();}catch(IllegalArgumentException e){}
		  Assert.assertTrue(r1.setNext(r4));
		  Assert.assertTrue(r4.setNext(i1));
	}
}
