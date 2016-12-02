import java.util.ArrayList;

public class PCB {
	private int PID;
	private Process process;
	private State state;
	private int requiredMemory;
	private int arrival;
	private ArrayList<String> commands = new ArrayList<String>();
	private int instructionCounter;
	
	private int waitTimeLeft;
		
	// Constructor with process provided
	public PCB(Process p){
		this.PID = p.getPID();
		this.process = p;
		this.state = State.NEW;
		this.requiredMemory = p.getRequiredMemory();
		commands = p.getCommands();
		this.instructionCounter = 0;
		this.waitTimeLeft = 0;
	}
		
	public Process getProcess(){
		return process;
	}
	
	public int getPID(){
		return this.PID;
	}
	
	public State getState(){
		return this.state;
	}
	
	public PCB setState(State state){
		this.state = state;
		return this;
	}
	
	public int getRequiredMemory(){
		return requiredMemory;
	}
	
	public String getInstruction(){
		if (instructionCounter < commands.size()){
			return commands.get(instructionCounter);
		}
		else return null;
	}
	
	public void setInstruction(String s){
		if (instructionCounter < commands.size()){
			commands.set(instructionCounter, s);
		}
	}
	public void removeInstruction(){
		if (instructionCounter < commands.size()){
			commands.remove(instructionCounter);
		}
	}
	
	public void incrementInstructionCounter(){
		instructionCounter++;
	}
	
	public void setWaitTime(int t){
		waitTimeLeft = t;
	}
	
	public int getWaitTime(){
		return waitTimeLeft;
	}
	
	public void decreaseWaitTime(){
		waitTimeLeft--;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("PID: " + PID + "; ");
		sb.append("State: " + state);
		return sb.toString();
		
	}
}
