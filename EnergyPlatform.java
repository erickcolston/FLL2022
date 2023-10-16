/**
 * NAME: Erick Colston
 * DATE: 3/9/23
 * APPLICATION: FFL Superpower Competition
 * DESCRIPTION: Energy Platform
 * 
 * 				Class implements the behavior interface, which contains methods takeControl(), action(), and 
 * 				suppress(). When touch sensor is pressed, class directs robot to the energy rig and pushes the 
 * 				lever down. It then returns to home base. The takeControl() method returns true if touch sensor is pressed.
 */

//=== Imports ---//
import lejos.robotics.navigation.*;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


//-- Touch class that implements the behavior interface ---//
public class EnergyPlatform implements Behavior{

	//--- Create variables ---//
	private DifferentialPilot pilot;
	private TouchSensor touch;
	static private boolean suppressed = false;
	
	
	//--- Constructor to instantiate objects ---//
	public EnergyPlatform(SensorPort port){
		
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
		
		//--- Maneuvers to energy rig ---//
		pilot.stop();		
		pilot.travel(-20);		
		pilot.rotate(-80);
		pilot.travel(60);
		pilot.rotate(-80);
		pilot.travel(52);
		Motor.B.setSpeed(500);
		Motor.B.rotate(150);
		pilot.travel(-5);
		pilot.rotate(-80);
		pilot.travel(65);
		Motor.B.rotate(-150);
								
		pilot.stop();
		suppressed = true;				//Energy rig mission complete, suppress behavior
		
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
