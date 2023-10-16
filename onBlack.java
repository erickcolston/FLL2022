/**
 * NAME: Erick Colston
 * DATE: 4/20/2023
 * APPLICATION: FFL Superpower Competition
 * DESCRIPTION: Black Line behavior
 * 
 * 				Class implements the behavior interface, which contains methods takeControl(), action(), and 
 * 				suppress(). This class allows robot follow the black line to complete FFL Superpower 
 * 				Missions.
 */

//=== Imports ---//
import lejos.robotics.Color;
import lejos.robotics.navigation.*;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;


//-- OnBlue class that implements the behavior interface ---//
public class onBlack implements Behavior{

	//--- Create variables ---//
	private DifferentialPilot pilot;
	private ColorSensor color;
	private boolean suppressed = false;
	TouchSensor touch;
	EnergyStorage touch3;
	SolarFarm touch2;
	OilPlatform touch6;
	RaiseHand touch4;
	EnergyPlatform touch5;
	
	
	
	//--- Constructor to instantiate objects ---//
	public onBlack(){
		
		pilot = new DifferentialPilot(3.1, 14.1, Motor.A, Motor.C);
		color = new ColorSensor(SensorPort.S1);
		touch = new TouchSensor(SensorPort.S2);
		touch2 = new SolarFarm(SensorPort.S2);
		touch3 = new EnergyStorage(SensorPort.S2);
		touch4 = new RaiseHand(SensorPort.S2);
		touch5 = new EnergyPlatform(SensorPort.S2);
		touch6 = new OilPlatform(SensorPort.S2);
	}
	
	 
	//--- Method that returns true to arbitrator (if argument is met) ---//
	public boolean takeControl() {
		
		return color.getColorID() == Color.BLACK;
	}

	
	//--- Method thats puts behavior into action ---//
	public void action() {
		
		suppressed = false;
		
		//--- While color sensor reads black --//
		while(color.getColorID() == Color.BLACK) {
			
			pilot.forward();
			
			//-- If touch sensor is pressed ---//
			if(touch.isPressed()) {
				
				//-- If solar farm mission has not been completed --//
				if(touch2.getSuppress()==false){
					pilot.stop();
					touch2.action();
					break;
				}
				
				//-- If energy storage mission has not been completed --//
				else if(touch3.getSuppress()==false){
					pilot.stop();
					touch3.action();
					break;	
				}
				
				//-- If raise hand mission has not been completed --//
				else if(touch4.getSuppress()==false){
					pilot.stop();
					touch4.action();
					break;	
				}
				
				//-- If energy rig mission has not been completed --//
				else if(touch5.getSuppress()==false){
					pilot.stop();
					touch5.action();
					break;
				}
				
				//-- If oil platform mission has not been completed --//
				else if(touch6.getSuppress()==false){
					pilot.stop();
					touch6.action();
					break;
				}
			}
		}
	
		//--- Backs up and rotates when black line is not detected ---//
		pilot.travel(-2);
		pilot.rotate(6);
	
		//--- Exits sweeping when black line is detected ---//
		while(pilot.isMoving() && !suppressed) Thread.yield();
	}

	 
	//--- Method to suppress behavior ---//
	public void suppress() {
		
		suppressed = true;
	} 
}

