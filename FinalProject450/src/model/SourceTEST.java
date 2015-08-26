package model;

import junit.framework.TestCase;

public class SourceTEST extends TestCase {
	public SourceTEST(String name){
		super(name);
	}
	public void testSource(){
		MP mp = MP.getInstance();
		mp.setLifeTime();
		Source s1 = ComponentFactory.newSource(true, mp);
		Road r1 = null;
		Road r2 = ComponentFactory.newRoad(true, mp);
		Road r3 = ComponentFactory.newRoad(false, mp);
		try{s1.setStartRoad(r1);fail();}catch(NullPointerException e){}
		try{s1.setStartRoad(r3);fail();}catch(IllegalArgumentException e){}
		s1.setStartRoad(r2);
		assertEquals(s1.getStartRoad(),r2);
	}
}
