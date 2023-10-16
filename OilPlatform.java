/**
 * NAME: Erick Colston
 * DATE: 3/9/23
 * APPLICATION: FFL Superpower Competition
 * DESCRIPTION: Oil Platform
 * 
 * 				Class implements the behavior interface, which contains methods takeControl(), action(), and 
 * 				suppress(). When touch sensor is pressed, class directs robot to the oil platform and lifts the 
 * 				lever three times to drop energy sources into the back of the truck. The takeControl() method 
 * 				returns true if touch sensor is pressed.
 */

//=== Imports ---//
import lejos.robotics.navigation.*;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


//-- Touch class that implements the behavior interface ---//
public class OilPlatform implements Behavior{

	//--- Create variables ---//
	private DifferentialPilot pilot;
	private TouchSensor touch;
	static private boolean suppressed = false;
	
	
	//--- Constructor to instantiate objects ---//
	public OilPlatform(SensorPort port){
		
		pilot = new DifferentialPilot(3.1, 14.1, Motor.A, Motor.C);
		touch = new TouchSensor(port);
	}
	
	
	//--- Method that returns true to arbitrator (if argument is met) ---//
	public boolean takeControl(){
		
		return touch.isPressed();
	}
	
	
	//--- Method thats puts behavior into action ---//
	public void action(){
			
		suppressed = false;
		Motor.B.setSpeed(500);
		
		//--- Lift oil platform arm three times and return home ---//
		pilot.stop();			//stops
		pilot.travel(-5);		
		pilot.rotate(85);
		pilot.travel(15);
		pilot.travel(-10);
		Motor.B.rotate(150);		//lift lever three times
		pilot.travel(5);
		pilot.travel(-1);
		Motor.B.rotate(-50);
		Motor.B.rotate(50);
		Motor.B.rotate(-50);
		Motor.B.rotate(50);
		Motor.B.rotate(-50);
		Motor.B.rotate(50);
		pilot.travel(-25);
		pilot.rotate(40);
		pilot.travel(75);
		
		pilot.stop();
		suppressed = true;				//Oil platform mission complete, suppress behavior
		
		Button.waitForPress();
		
		//--- Exits behavior when higher priority is detected ---//
		while(pilot.isMoving() && !suppressed) Thread.yield();
	}
	
	
	//--- Method to suppress behavior ---//
	public void suppress(){
		
		suppressed = true;
	} 
	
	
	//--- Method to get suppressed boolean ---//
	public boolean getSuppress(){
		
		return suppressed;
	}
}


