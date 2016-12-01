public final class PIDGenerator {
	// A static variable which keeps track of the last PID used
	private static int lastPID = -1;
	
	private static PIDGenerator instance = new PIDGenerator();
	
	// Set the starting PID to 0
	private PIDGenerator(){
	}
	
	public static PIDGenerator getInstance(){
		return instance;
	}
	
	// When a new PID is created, the last PID is incremented and the new value is a new PID
	public static int newPID(){
		// Generate a new PID and return it
		lastPID++;
		return lastPID;
	}
}
