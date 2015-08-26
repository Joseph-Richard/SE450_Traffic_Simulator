package model;

import java.util.Observable;
import java.util.PriorityQueue;


/**
 * An implementation of {@link TimeServer} that uses a priority queue to decide when to run {@link Agent} Objects. 
 * @author Admin
 *
 */
public final class TimeServerQueue extends Observable implements TimeServer {
	private static TimeServerQueue instance = null;
  private static final class Node implements Comparable<Node> {
    final double waketime;
    final Agent agent;
    public Node(double waketime, Agent agent) {
      this.waketime = waketime;
      this.agent = agent;
    }
    public int compareTo(Node that) {
      return (int) (Math.signum(this.waketime - that.waketime));
    }
  }
  private double _currentTime;
  private PriorityQueue<Node> _queue;

  protected TimeServerQueue() {
    _queue = new PriorityQueue<Node>();
    this._currentTime = 0;
  }

  public static TimeServerQueue getInstance(){
	  if(instance == null)return instance = new TimeServerQueue();
	  else 
		  return instance; 
  }
  public static TimeServerQueue getInstaceCopy(){
	  return instance;
  }
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    String sep = "";
    Node[] nodes = _queue.toArray(new Node[0]);
    java.util.Arrays.sort(nodes);
    for (Node node : nodes) {
      sb.append(sep).append("(").append(node.waketime).append(",")
        .append(node.agent).append(")");
      sep = ";";
    }
    sb.append("]");
    return (sb.toString());
  }

  public double currentTime() {
    return _currentTime;
  }

  public void enqueue(double waketime, Agent agent)
    throws IllegalArgumentException
  {
    if (waketime < _currentTime)
      throw new IllegalArgumentException();
    _queue.add(new Node(waketime, agent));
  }

  Agent dequeue()
  {
	  Agent a = _queue.remove().agent;
	  //System.out.println("Agent: "+a+"\n"+"at current time= "+this._currentTime);
    return a;
  }

  int size() {
    return _queue.size();
  }

  boolean empty() {
    return _queue.isEmpty();
  }

  public void run(double duration) {
    double endtime = _currentTime + duration;
    while ((!empty()) && (_queue.peek().waketime <= endtime)) {
      if ((_currentTime - _queue.peek().waketime) < 1e-09) {
        super.setChanged();
        super.notifyObservers();
      }
      _currentTime =_queue.peek().waketime;
      dequeue().run();
      //super.setChanged();
      //super.notifyObservers();
    }
    _currentTime = endtime;
  }
}



