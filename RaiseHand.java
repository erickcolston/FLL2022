/**
 * NAME: Erick Colston
 * DATE: 3/9/23
 * APPLICATION: FFL Superpower Competition
 * DESCRIPTION: Raise Hand
 * 
 * 				Class implements the behavior interface, which contains methods takeControl(), action(), and 
 * 				suppress(). When touch sensor is pressed, class directs robot to the hand and pulls the 
 * 				lever to raise the hand. Robot then returns to home base for next mission. The takeControl() method 
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
public class RaiseHand implements Behavior{

	//--- Create variables ---//
	private DifferentialPilot pilot;
	private TouchSensor touch;
	static private boolean suppressed = false;
	
	
	//--- Constructor to instantiate objects ---//
	public RaiseHand(SensorPort port){
		
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
		
		//--- Maneuvers to hand ---//
		pilot.stop();		
		pilot.travel(-20);		
		pilot.rotate(-80);
		pilot.travel(60);
		pilot.rotate(75);
		Motor.B.rotate(50);
		pilot.travel(10);
		Motor.B.rotate(100);
		pilot.travel(-15);
		/*
		Motor.B.rotate(-150);
		pilot.rotate(115);
		pilot.travel(80);
		pilot.rotate(-35);
		pilot.travel(10);
		*/
				
		//Go to energy platform
		Motor.B.rotate(-150);
		pilot.rotate(160);
		pilot.travel(35);
		Motor.B.rotate(150);
		pilot.travel(-5);
		pilot.rotate(-75);
		pilot.travel(70);
		Motor.B.rotate(-150);
		
		pilot.stop();
		suppressed = true;				//Hand mission complete, suppress behavior
		
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

