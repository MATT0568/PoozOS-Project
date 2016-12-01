public class PCB {
	private int PID;
	private Process process;
	private State state;
	private int commandIndex;
	private int allottedTime;
	private int arrivalTime;
	private int cpuTime;
	private int waitTime;
	private int commandTime;
		
	// Constructor with process provided
	public PCB(Process p){
		this.PID = p.getPID();
		this.process = p;
		this.state = State.NEW;
		this.commandIndex = 0;
		this.cpuTime = 0;
		this.commandTime = 0;
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
	public void setCommandTime(int time){
		this.commandTime = time;
	}
	public void setAllottedTime(int time){
		this.allottedTime = time;
	}
	
	public int getAllottedTime(){
		return allottedTime;
	}
	public int getCommandTime(){
		return commandTime;
	}
	public int getCommandIndex(){
		return commandIndex;
	}
	
	public void incrementIndex(){
		if (commandIndex < process.getCommands().size()-1){
			commandIndex++;
		}
		else {
			this.setState(State.EXIT);
		}
	}
	
	//TODO
	public int getWait(){
		return waitTime;
	}
	
	//TODO
	public void setWait(int wait){
		waitTime = wait;
	}
	
	public int getArrival(){
		return arrivalTime;
	}
	
	public void setArrival(int clockTime){
		arrivalTime = clockTime;
	}
	

	public int getCPUTime(){
		return cpuTime;
	}

	public void setCPUTime(int cpuTime){
		this.cpuTime = cpuTime;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("PID: " + PID + "; ");
		sb.append("State: " + state);
		return sb.toString();
		
	}
}
