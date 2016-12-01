
public class IOBurst {
	int burst = 10;
	
	public IOBurst(){
		
	}
	
	
	// Use random number generator
	public int generateIOBurst(){
		return (int) ((Math.random() * burst) + 0.5);
	}

}
