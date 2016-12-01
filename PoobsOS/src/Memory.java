// This class keeps track of total memory

public final class Memory {
	int totalMemory = 256;
	int availableMemory = 256;
	
	private static Memory instance = new Memory();
	
	private Memory(){
	}
	
	public static Memory getInstance(){
		return instance;
	}
	
	public int getTotalMemory(){
		return totalMemory;
	}
	
	public void restoreMemory(int reqMem){
		availableMemory += reqMem;
	}
	
	public int getAvailableMemory(){
		return availableMemory;
	}
	
	public void allocateMemory(int reqMem){
		availableMemory -= reqMem;
	}
}
