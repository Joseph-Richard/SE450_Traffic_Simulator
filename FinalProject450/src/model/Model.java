package model;

import java.util.ArrayList;
import java.util.Observable;
import swing.Animator;
import swing.AnimatorBuilder;


/**
 * 
 * The model contains roads and intersections organized in a matrix.
 * See {@link #Model(AnimatorBuilder, MP,int, int)}.
 */
public class Model extends Observable {
  private Animator _animator;
  private boolean _disposed;
  private MP _MP;
  ArrayList<CarAcceptor> roads = new ArrayList<CarAcceptor>();
  
  /** Creates a model to be visualized using the <code>builder</code>.
   *  If the builder is null, no visualization is performed.
   *  The number of <code>rows</code> and <code>columns</code>
   *  indicate the number of {@link Light}s, organized as a 2D
   *  matrix.  These are separated and surrounded by horizontal and
   *  vertical {@link Road}s.  For example, calling the constructor with 1
   *  row and 2 columns generates a model of the form:
   *  <pre>
   *     |  |
   *   --@--@--
   *     |  |
   *  </pre>
   *  where <code>@</code> is a {@link Intersection}, <code>|</code> is a
   *  vertical {@link Road} and <code>--</code> is a horizontal {@link Road}.
   *  Each road has one {@link Car}.
   *
   *  <p>
   *  The {@link AnimatorBuilder} is used to set up an {@link
   *  Animator}.
   *  {@link AnimatorBuilder#getAnimator()} is registered as
   *  an observer of this model.
   *  <p>
   */
  public Model(AnimatorBuilder builder,MP m, int rows, int columns) {
    if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
      throw new IllegalArgumentException();
    }
    _MP = m;
    setup(builder, rows, columns);
    _animator = builder.getAnimator();
    _MP.getLifeTime().addObserver(_animator);
  }

  /**
   * Run the simulation for <code>duration</code> model seconds.
   */
  public void run(double duration) {
    if (_disposed)
      throw new IllegalStateException();
    _MP.getLifeTime().run(_MP.getRunTime());
  }

  /**
   * Throw away the current model.
   */
  public void dispose() {
    _animator.dispose();
    _disposed = true;
  }

  /**
   * Construct the model, establishing correspondences with the visualizer.
   */
  private void setup(AnimatorBuilder builder, int rows, int columns) {
	  _MP.setLifeTime();  
      Intersection[][] intersections = new Intersection[rows][columns];
      boolean EW = false;
      boolean SN = false;
      for(int i=0;i<rows;i++){
    	  for(int j=0; j<columns; j++){
    		  intersections[i][j]= ComponentFactory.newIntersection(_MP);
    		  builder.addLight(intersections[i][j], i, j);	  
    	  }
      }
      for(int i=0; i<rows;i++){
    	  Source source = ComponentFactory.newSource(true, _MP);
    	  _MP.getLifeTime().enqueue(_MP.getLifeTime().currentTime(), source);
    	  if(EW){
    		  for (int j=columns; j>=0;j--){
    			  Road road = ComponentFactory.newRoad(true, _MP);
    			  if(j==columns){
    				  source.setStartRoad(road);
    				  road.setNext(intersections[i][j-1]);
    			  }
    			  else if(j==0){
    				  intersections[i][j].setNext(road, true);
    				  road.setNext(new Sink());
    			  }
    			  else{
    				  intersections[i][j].setNext(road, true);
    				  road.setNext(intersections[i][j-1]);
    			  }
    			  
    			  builder.addHorizontalRoad(road, i, j, EW);
    			  roads.add(road);
    		  }
    		  
    	  }
    	  else{
    		  for(int j =0; j<=columns;j++){
    			  Road road = ComponentFactory.newRoad(true, _MP);
    			  if(j==0){
    				  source.setStartRoad(road);
    				  road.setNext(intersections[i][j]);
    			  }
    			  else if(j==columns){
    				  intersections[i][j-1].setNext(road, true);
    				  road.setNext(new Sink());
    			  }
    			  else{
    				  intersections[i][j-1].setNext(road, true);
    				  road.setNext(intersections[i][j]);
    			  }
    			  builder.addHorizontalRoad(road, i, j, EW);
    			  roads.add(road);
    		  }
    		  
    	  }
    	  if(_MP.isAlternating())EW = !EW;
      }
      //vertical roads
      for(int i=0;i<columns;i++){
    	  Source source = ComponentFactory.newSource(false, _MP);
    	  _MP.getLifeTime().enqueue(_MP.getLifeTime().currentTime()+_MP.getTimeStep(), source);
    	  if(SN){
    		  for(int j = rows; j>=0;j--){
    			  Road road = ComponentFactory.newRoad(false, _MP);
    			  if(j==rows){
    				  source.setStartRoad(road);
    				  road.setNext(intersections[j-1][i]);
    			  }
    			  else if(j == 0){
    				  intersections[j][i].setNext(road, false);
    				  road.setNext(new Sink());
    			  }
    			  else{
    				  intersections[j][i].setNext(road, false);
    				  road.setNext(intersections[j-1][i]);
    			  }
    			  builder.addVerticalRoad(road, j, i, SN);
    			  roads.add(road);
    		  }
    	  }
    	  else{
    		  for(int j=0;j<=rows;j++){
    			  Road road = ComponentFactory.newRoad(false, _MP);
    			  if(j==rows){
    				  intersections[j-1][i].setNext(road, false);
    				  road.setNext(new Sink());
    			  }
    			  else if(j==0){
    				  source.setStartRoad(road);
    				  road.setNext(intersections[j][i]);
    			  }
    			  else{
    				  intersections[j-1][i].setNext(road, false);
    				  road.setNext(intersections[j][i]);
    			  }
    			  builder.addVerticalRoad(road, j, i, SN);
    			  roads.add(road);
    		  }
    	  }
    	  if(_MP.isAlternating())SN = !SN;
      }    
  }
}
