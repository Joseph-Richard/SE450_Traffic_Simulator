package main;

import ui.UI;
import ui.UIMenu;
import ui.UIMenuAction;
import ui.UIMenuBuilder;
import ui.UIError;
import ui.UIForm;
import ui.UIFormTest;
import ui.UIFormBuilder;
import model.*;







import model.MP;
class Control {
  private static final int EXITED = 0;
  private static final int EXIT = 1;
  private static final int START = 2;
  private static final int PARAMS = 3;
  private static final int NUMSTATES = 4;
  private UIMenu[] _menus;
  private int _state;

  private UIFormTest _intTest;
  private UIFormTest _doubleTest;
 
  private UIFormTest _booleanTest;
  private UIForm _getIntForm;
  private UIForm _getDoubleForm;
  private UIForm _getBooleanForm;
  private UIForm _getGridForm;
  private Model model;  

  private UI _ui;
  private MP _MP;



  Control(Model m, UI ui) {
    model = m;
    _ui = ui;
    _MP = MP.getInstance();
    _menus = new UIMenu[NUMSTATES];
    _state = START;
    addSTART(START);
    addParameters(PARAMS);
    addEXIT(EXIT);
    
    _intTest = new UIFormTest() {
        public boolean run(String input) {
          try {
            Integer.parseInt(input);
            return true;
          } catch (NumberFormatException e) {
            return false;
          }
        }
      };
      _doubleTest = new UIFormTest() {
          public boolean run(String input) {
            try {
              Double.parseDouble(input);
              return true;
            } catch (NumberFormatException e) {
              return false;
            }
          }
        };

   _booleanTest = new UIFormTest(){
	   public boolean run(String input){
		   if(Boolean.parseBoolean(input.trim()))
			   return true;
		   return false;
	   
   }};
	   
    UIFormBuilder f = new UIFormBuilder();
    f.add("Enter min",_intTest);
    f.add("Enter max",_intTest);
    _getIntForm = f.toUIForm("Input");
    
    UIFormBuilder g = new UIFormBuilder();
    g.add("Enter min",_doubleTest);
    g.add("Enter max",_doubleTest);
    _getDoubleForm = g.toUIForm("Input");
  
    UIFormBuilder h = new UIFormBuilder();
    h.add("Enter 'true' for alternating rows/columns", _booleanTest);
    _getBooleanForm = h.toUIForm("Input");
    
    UIFormBuilder grid = new UIFormBuilder();
    grid.add("Enter number of rows", _intTest);
    grid.add("Enter number of columns", _intTest);
    _getGridForm = grid.toUIForm("Input");
  }
  


void run() {
    try {
      while (_state != EXITED) {
        _ui.processMenu(_menus[_state]);
      }
    } catch (UIError e) {
      _ui.displayError("UI closed");
    }
  }
  
  private void addSTART(int stateNum) {
    UIMenuBuilder m = new UIMenuBuilder();
    
    m.add("Default",
      new UIMenuAction() {
        public void run() {
          _ui.displayError("doh!");
        }
      });
    m.add("Run simulation",
      new UIMenuAction() {
        public void run() {
        //	model.dispose();
        	//model = new Model(new SwingAnimatorBuilder(_MP), _MP, _MP.getRows(), _MP.getColumns());
        	model.run(_MP.getRunTime());
        	model.dispose();
        	_state = START;
        }
      });
    m.add("Change simulation parameters",
      new UIMenuAction() {
        public void run() {
        	_state = PARAMS;
        }
      });        
    m.add("Exit",
      new UIMenuAction() {
        public void run() {
          _state = EXIT;
        }
      });
    
    
    _menus[stateNum] = m.toUIMenu("Simulation City");
  }
  private void addEXIT(int stateNum) {
    UIMenuBuilder m = new UIMenuBuilder();
    
    m.add("Default", new UIMenuAction() { public void run() {} });
    m.add("Yes",
      new UIMenuAction() {
        public void run() {
          _state = EXITED;
        }
      });
    m.add("No",
      new UIMenuAction() {
        public void run() {
          _state = START;
        }
      });
    
    _menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
  }
  private void addParameters(int stateNum) {
	  UIMenuBuilder m = new UIMenuBuilder();
	  
	  m.add("Default", new UIMenuAction(){public void run(){}});
	  m.add("Show Current values", 
			  new UIMenuAction(){
		  		public void run(){
		  			_ui.displayMessage(_MP.toString());
		  		}
	  });
	  
	  m.add("Simulation time step",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			_MP.setTimeStep(Double.parseDouble(result1[0]));
	  		}
	  });
	  m.add("Simulation runtime",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getIntForm);
	  			_MP.setRunTime(Integer.parseInt(result1[0]));
	  		}
	  });
	  m.add("Grid size",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getGridForm);
	  			try{
	  				_MP.setRows(Integer.parseInt(result1[0]));
	  				_MP.setColumns(Integer.parseInt(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println("Rows and Columns must be greater than zero.");
	  				this.run();}
	  		}
	  });
	  m.add("Set traffic pattern",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getBooleanForm);
	  			MP.getInstance().setAlternating(Boolean.parseBoolean(result1[0]));
	  		}
	  });
	  m.add("Set car entry rate",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setSourceTimeMin(Double.parseDouble(result1[0]));
	  				_MP.setSourceTimeMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set road lengths",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setRoadLengthMin(Double.parseDouble(result1[0]));
	  				_MP.setRoadLengthMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
		  				System.out.println(" 0 <= Min <= Max");
		  				this.run();
		  			}
	  		}
	  });
	  m.add("Set intersection lengths",new UIMenuAction(){
	  		public void run(){
	  		//TODO	  			
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setIntersectionLengthMin(Double.parseDouble(result1[0]));
	  				_MP.setIntersectionLengthMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set car Length",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setCarLengthMin(Double.parseDouble(result1[0]));
	  				_MP.setCarLengthMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set max car velocity",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setMaxVelocityMin(Double.parseDouble(result1[0]));
	  				_MP.setMaxVelocityMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set car stop distance",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setStopDistanceMin(Double.parseDouble(result1[0]));
	  				_MP.setStopDistanceMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set car brake distance",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setBrakeDistanceMin(Double.parseDouble(result1[0]));
	  				_MP.setBrakeDistanceMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set traffic light green times",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setGreenDurationMin(Double.parseDouble(result1[0]));
	  				_MP.setGreenDurationMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Set traffic light yellow times",new UIMenuAction(){
	  		public void run(){
	  		//TODO
	  			String[] result1 = _ui.processForm(_getDoubleForm);
	  			try{
	  				_MP.setYellowDurationMin(Double.parseDouble(result1[0]));
	  				_MP.setYellowDurationMax(Double.parseDouble(result1[1]));
	  			}catch(IllegalArgumentException e){
	  				System.out.println(" 0 <= Min <= Max");
	  				this.run();
	  			}
	  		}
	  });
	  m.add("Reset simulation and return to main menu",new UIMenuAction(){
	  		public void run(){
	  			//TODO
	  			_MP = MP.getInstance();
	  			_state = START;
	  		}
	  });
	  m.add("Return to main menu",new UIMenuAction(){
	  		public void run(){
	  			_state = START;
	  		}
	  });
	  _menus[stateNum] = m.toUIMenu("100 miles-per-hour switchin' lanes like whoa");
}
}
