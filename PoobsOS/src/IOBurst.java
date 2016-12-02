
public class IOBurst {
	int burstRange = 25;
	
	public IOBurst(){
		
	}
	
	
	// Use random number generator
	public int generateIOBurst(){
		return (int) ((Math.random() * burstRange) + 25.5);
	}

}
