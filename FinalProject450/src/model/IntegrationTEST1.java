package model;

import junit.framework.Assert;
import junit.framework.TestCase;
/**
 * An integration Test for all the model classes that have been created. 
 * @author Admin
 *
 */
@SuppressWarnings("deprecation")
public class IntegrationTEST1 extends TestCase {
	public IntegrationTEST1(String name){
		super(name);
	}
	
	public void testIntegration(){
		MP mp = MP.getInstance();
		mp.setLifeTime();
		Source s1 = ComponentFactory.newSource(true, mp);
		Source s2 = ComponentFactory.newSource(false, mp);
		
		Road r1 = ComponentFactory.newRoad(true, mp);
		Road r2 = ComponentFactory.newRoad(true, mp);
		Road r3 = ComponentFactory.newRoad(false, mp);
		Road r4 = ComponentFactory.newRoad(false,mp);
		
		Intersection i1 = ComponentFactory.newIntersection(mp);
		
		Sink si1 = ComponentFactory.newSink();
		Sink si2 = ComponentFactory.newSink();
		
		s1.setStartRoad(r1);
		s2.setStartRoad(r3);
		
		r1.setNext(i1);
		r3.setNext(i1);
		i1.setNext(r2, true);
		i1.setNext(r4, false);
		r2.setNext(si1);
		r4.setNext(si2);
		
		Assert.assertEquals(r1.getNext(true),i1);
		Assert.assertEquals(r2.getNext(true),si1);
		Assert.assertEquals(r3.getNext(true),i1);
		Assert.assertEquals(r4.getNext(true),si2);
		Assert.assertEquals(i1.getNext(true),r2);
		Assert.assertEquals(i1.getNext(false),r4);
		
		//Horizontal Run
		s1.run();
		Assert.assertEquals(r1.getCars().size(), 1);
		i1.getLight().setLightState(LightState.RedNSGreenEW);
		while(i1.getCars(true).size()<1){r1.run();}
		Assert.assertEquals(i1.getCars(true).size(), 1);
		while(r2.getCars().size()<1)i1.run();
		Assert.assertEquals(r2.getCars().size(), 1);
		while(r2.getCars().size()>0)r2.run();
		
		Assert.assertEquals(r1.getCars().size(), 0);
		Assert.assertEquals(i1.getCars(true).size(), 0);
		Assert.assertEquals(r2.getCars().size(), 0);
		
		//Vertical Run
		s2.run();
		Assert.assertEquals(r3.getCars().size(), 1);
		i1.getLight().setLightState(LightState.GreenNSRedEW);
		while(i1.getCars(false).size()<1){r3.run();}
		Assert.assertEquals(i1.getCars(false).size(), 1);
		while(r4.getCars().size()<1)i1.run();
		Assert.assertEquals(r4.getCars().size(), 1);
		while(r4.getCars().size()>0)r4.run();
		
		Assert.assertEquals(r3.getCars().size(), 0);
		Assert.assertEquals(i1.getCars(false).size(), 0);
		Assert.assertEquals(r4.getCars().size(), 0);
	}
}
