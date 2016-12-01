import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CPU {
	private Clock clock = Clock.getInstance();
	private PCB currentProcess = null;
	
	private static CPU instance = new CPU();
	
	private CPU(){
		
	}
	public static CPU getInstance() {
		return instance;
	}
	
	
	public void advanceClock(){
		clock.execute();
	}
	
	public void detectInterrupt(){
		
	}
	
	public void detectPreemption(){
		
	}

	

	
}
