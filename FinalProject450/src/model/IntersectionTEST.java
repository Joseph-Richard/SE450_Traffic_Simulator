package model;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * A test class for the {@link Intersection} Object class. 
 * @author Joseph Richard
 *
 */
@SuppressWarnings("deprecation")
public class IntersectionTEST extends TestCase {
	 public IntersectionTEST(String name) {
		    super(name);
		  }
	public void testIntersection(){
		 MP mp = MP.getInstance();
		 mp.setLifeTime();
		 Intersection i1 = ComponentFactory.newIntersection(mp);
		 Car c1 = ComponentFactory.newCar(false, mp);
		 Car c2 = null; 
		 Road r1 = ComponentFactory.newRoad(true, mp);
		 Road r2 = ComponentFactory.newRoad(false, mp);
		 Road r3 = null; 
		 try{i1.setNext(r3, true);fail();}catch(NullPointerException e){}
		 i1.setNext(r1, r1.isHorizontal(true));
		 i1.setNext(r2, r2.isHorizontal(true));
		 Assert.assertEquals(i1.getNext(true), r1);
		 Assert.assertEquals(i1.getNext(false), r2);
		 try{i1.accept(c2, 0);fail();}catch(NullPointerException e){}
		 i1.accept(c1, 0.0);
		 Assert.assertEquals(c1.getCurrentRoad(), i1);
	 }
}
