/**
 * NAME: Erick Colston
 * DATE: 4/20/2023
 * APPLICATION: FFL Superpower Competition
 * DESCRIPTION: Program to complete FFL Superpower missions 3, by implementing the java Behavior Class and 
 * 				the java Arbitrator Class.
 */

//--- Imports ---//
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

//--- FFL DEMO CLASS ---//
public class demo {

	static DifferentialPilot pilot;
	
	//--- MAIN ---//
	public static void main(String[] args) {
		
		//--- Create new behaviors ---//
		Behavior b1 = new onBlack();
		Behavior b2 = new SolarFarm(SensorPort.S2);
		Behavior b3 = new EnergyStorage(SensorPort.S2);
		Behavior b4 = new RaiseHand(SensorPort.S2);
		Behavior b5 = new EnergyPlatform(SensorPort.S2);
		Behavior b6 = new OilPlatform(SensorPort.S2);
		
		
		//--- Create behavior array and new arbitrator to manage behaviors ---//
		Behavior [] bArray = {b1,b2,b3,b4,b5,b6};
		Arbitrator arby = new Arbitrator(bArray);
		
		//-- Create differential pilot to lift lever on energy rig --//
		pilot = new DifferentialPilot(3.1, 14.1, Motor.A, Motor.C);
		Motor.B.rotate(-50);
		pilot.travel(35);
		Motor.B.setSpeed(500);
		Motor.B.rotate(-100);
		pilot.travel(-35);
		
		Button.waitForPress();
		
		//-- Start arbitrator to complete missions --//
		arby.start();
	}
}
