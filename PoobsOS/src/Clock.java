
public class Clock {
	private static int clockCycle;
	
	private static Clock instance = new Clock();
	
	private Clock(){
		clockCycle = 0;
	}
	
	public static Clock getInstance(){
		return instance;
	}
	
	public static void execute(){
		clockCycle++;
	}
	
	public int getClock(){
		return clockCycle;
	}
}
