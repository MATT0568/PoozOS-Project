import java.util.ArrayList;

public class Process {
	private int PID, arrivalTime, CPUTime, reqMem;
	private int startTime;
	private int runTime = 0;
	ArrayList<String> commands;
	private PIDGenerator pid = PIDGenerator.getInstance();
	
	public Process(int reqMem, int startTime){
		this.PID = PIDGenerator.newPID();		
		this.reqMem = reqMem;
		this.startTime = startTime;
		commands = new ArrayList<String>();
	}
	
	public Process(int reqMem, int startTime, ArrayList<String> commands){
		this.PID = PIDGenerator.newPID();		
		this.reqMem = reqMem;
		this.commands = commands;
	}
	
	public int getPID(){
		return this.PID;
	}
	
	public void setRunTime(int runTime){
		this.runTime += runTime;
	}
	
	public int getRunTime(){
		return runTime;
	}
	
	public int getRequiredMemory(){
		return reqMem;
	}
	
	public Process setRequiredMemory(int reqMem){
		this.reqMem = reqMem;
		return this;
	}
	
	public String toString(){
		return "PID: " + PID + "; " + "Memory Req: " + reqMem + "; ";
	}
	
	public void addCommand(String command){
		commands.add(command);
	}
	
	public ArrayList<String> getCommands(){
		return commands;
	}


	
	
}