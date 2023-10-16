/**
 * NAME: Erick Colston
 * DATE: 3/9/23
 * APPLICATION: FFL Superpower Competition
 * DESCRIPTION: Energy Storage Mission
 * 
 * 				Class implements the behavior interface, which contains methods takeControl(), action(), and 
 * 				suppress(). When touch sensor is pressed, class directs robot to place one energy unit in the 
 * 				energy storage and retrieves the storage tray. After returning to base, another energy unit is 
 * 				then placed in the energy storage. The takeControl() method returns true if touch sensor is pressed.
 */

//=== Imports ---//
import lejos.robotics.navigation.*;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


//-- Touch class that implements the behavior interface ---//
public class EnergyStorage implements Behavior{

	//--- Create variables ---//
	private DifferentialPilot pilot;
	private TouchSensor touch;
	static private boolean suppressed = false;
	
	
	//--- Constructor to instantiate objects ---//
	public EnergyStorage(SensorPort port){
		
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
		Motor.B.setSpeed(150);
		
		//--- Release energy source, grab tray and return home ---//
		pilot.stop();	
		pilot.travel(-1);
		Motor.B.rotate(150);
		
		//--- Go back home with tray of energy source ---//
		pilot.travel(-25);
		pilot.rotate(-35);
		pilot.travel(-40);
		
		Motor.B.rotate(-150);	//lift arm to load energy source
		
		Button.waitForPress();
		
		//--- Go back to energy storage to unload energy source in storage unit ---//
		pilot.travel(30);
		pilot.rotate(25);
		pilot.travel(20);
		//pilot.travel(-4);
		
		Motor.B.rotate(150);		//drop arm
		Motor.B.rotate(-150);
		pilot.travel(-40);
		pilot.rotate(-35);
		pilot.travel(-20);		//back up to release energy source
		
		pilot.stop();
		suppressed = true;		//Energy storage mission complete, suppress behavior
		
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

