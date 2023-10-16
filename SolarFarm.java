/**
 * NAME: Erick Colston
 * DATE: 04/13/2023
 * APPLICATION: Final Project 2
 * DESCRIPTION: Solar Farm
 * 
 * 				Class implements the behavior interface, which contains methods takeControl(), action(), and 
 * 				suppress(). When touch sensor is pressed, class directs robot to the solar farm to gather three
 * 				energy sources. The robot will then relocate the three energy sources to the black circle in the 
 * 				to complete another mission. The takeControl() method returns true if touch sensor is pressed.
 */

import lejos.nxt.*;
import lejos.robotics.navigation.*;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.Button;
import lejos.nxt.LCD; 
import lejos.nxt.Motor;
import lejos.nxt.SensorPort; 
import lejos.nxt.TouchSensor;

public class SolarFarm implements Behavior {
	
		//--- Create variables ---//
		private DifferentialPilot pilot;
		private TouchSensor touch;
		static private boolean suppressed = false;
		
		
		//--- Constructor to instantiate objects ---//
		public SolarFarm(SensorPort port){
			
			pilot = new DifferentialPilot(3.1, 14.1, Motor.A, Motor.C);
			touch = new TouchSensor(port);
		}
		
		
		//--- Method that returns true to arbitrator (if argument is met) ---//
		public boolean takeControl(){
			
			return touch.isPressed();
		}
		
		
		//--- Method thats puts behavior into action ---//
		public void action(){
			
			//Go to power plant
			pilot.travel(-15);
			pilot.rotate(-25);
			Motor.B.rotate(150);
			pilot.travel(13);
			pilot.setTravelSpeed(50);
			pilot.rotate(-40);
			pilot.travel(20);
			pilot.setTravelSpeed(500);
			pilot.travel(-5);
			Motor.B.rotate(-75);
			pilot.travel(10);
			Motor.B.rotate(75);
			pilot.travel(-10);
			pilot.rotate(40);
			pilot.travel(-80);
		
			
			pilot.stop();
			suppressed = true;		//Solar farm mission and mission 13 complete, suppress behavior
			
			Motor.B.rotate(-150);
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

		
		
		


