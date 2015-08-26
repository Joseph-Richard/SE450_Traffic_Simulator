package model;



/**
 * An interface class that works as a way of running {@link Agent} Objects over a set span of time. 
 * @author Admin
 *
 */
public interface TimeServer {
  public double currentTime();
  public void enqueue(double waketime, Agent thing);
  public void run(double duration);
  public void addObserver(java.util.Observer o);
  public void deleteObserver(java.util.Observer o);
}
