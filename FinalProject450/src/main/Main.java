package main;

import model.MP;
import model.Model;
import ui.UI;
import swing.SwingAnimatorBuilder;


/**
 * A static class to demonstrate the visualization aspect of
 * simulation.
 */
public class Main {
  private Main() {}
  public static void main(String[] args) {
	  UI ui;
	  Model m;
	  MP _MP = MP.getInstance();
	  ui = new ui.TextUI();
	  m = new Model(new SwingAnimatorBuilder(_MP),_MP, _MP.getRows(), _MP.getColumns());
	  Control control = new Control(m,ui);
	  control.run();
  }
}

