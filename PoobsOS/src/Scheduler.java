import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class Scheduler {

	// Data sets
	public static HashMap<Integer, Process> processes;
	public static HashMap<Integer, Long> cpuTime;
	public static HashMap<Integer, Long> waitingTime;
	public static HashMap<Integer, Long> responseTime;
	public static HashMap<Integer, Long> queueEntry;

	// Ready and waiting lists
	private static RoundRobin<PCB> ready;
	private static List<PCB> waiting;

	private static int quantum = 10;

	// Instances of singleton classes
	private static CPU cpu = CPU.getInstance();
	private static Clock clock = Clock.getInstance();
	private static Memory ram = Memory.getInstance();

	private static PCB current = null;
	

	private static Scheduler instance = new Scheduler();

	private IOBurst burst = new IOBurst();

	private Scheduler() {

		// Instantiate data sets
		processes = new HashMap<Integer, Process>();
		cpuTime = new HashMap<Integer, Long>();
		waitingTime = new HashMap<Integer, Long>();
		responseTime = new HashMap<Integer, Long>();
		queueEntry = new HashMap<Integer, Long>();

		// ready = new ExecutionQueue();
		ready = new RoundRobin<PCB>();
		waiting = new LinkedList<PCB>();
	}

	public static Scheduler getInstance() {
		return instance;
	}
	
	public PCB getCurrentPCB(){
		return current;
	}

	public void insertPCB(Process p) {
		PCB pcb = new PCB(p);

		// If memory is available, add to ready queue
		if (ram.getAvailableMemory() - p.getRequiredMemory() >= 0) {
			ready.enQueue(pcb);
			System.out.println("Programs in the ready queue: " + ready.size());
			processes.put(p.getPID(), p);
			ram.allocateMemory(pcb.getRequiredMemory());
			pcb.setState(State.READY);
			processes.get(p.getPID());
		}

		// Otherwise add to wait queue
		else {
			System.out
					.println("Not enough RAM available. Storing program in wait queue");
			waiting.add(pcb);
			System.out.println("Programs in wait queue: " + waiting.size());
			pcb.setState(State.WAIT);
		}
	}

	// Set the time quantum for Round Robin
	public void setQuantum(int q) {
		quantum = q;
	}

	public RoundRobin<PCB> getReadyList() {
		return ready;
	}

	public void execute() {
		current = ready.head();

		while (current != null) {
			if (!ready.hasNext()){
				quantum = Integer.MAX_VALUE - 1;
			}
			else {
				quantum = 10;
			}
			
			String instruction = current.getInstruction();
			System.out.println("Current instruction is " + instruction);
			if (instruction != null) {
				String[] command = instruction.split(" ");

				switch (command[0]) {
				case "CALCULATE":
					current.setState(State.RUN);
					System.out.println("Read instruction CALCULATE " + command[1]);
					int time = Integer.parseInt(command[1]);
					int i = 0;
					
					for (i = 0; i < quantum && i < time; i++) {
						System.out.println("Clock cycle " + clock.getClock());
						cpu.advanceClock();
					}
					
					if (i == time) {
						current.removeInstruction();
					} else {
						current.setInstruction("CALCULATE " + (time - i));
						current.setState(State.READY);
						current = ready.next();
					}
					break;
				case "I/O":
					current.setState(State.WAIT);
					int waitTime = burst.generateIOBurst();
					System.out.println("IO burst of " + waitTime + " cycles");
					current.setWaitTime(waitTime);
					for (int j = 0; j < waitTime; j++){
						current.decreaseWaitTime();
						cpu.advanceClock();
						System.out.println("Wait time is " + current.getWaitTime() + " cycles remaining");
					}
					current.removeInstruction();
					break;
				case "YIELD":
					System.out.println("Current instruction is yield");
					current.setState(State.READY);
					current.removeInstruction();
					current = ready.next();
					break;
				case "OUT":
					current.setState(State.READY);
					System.out.println("OUT statement.");
					current.removeInstruction();
					break;
				default:
					break;
				}
			}
			
			else {
				System.out.println("Process " + current.getPID() + " ran out of instructions");
				ready.deQueue();
				System.out.println("Process removed at clock cycle " + clock.getClock());
				current = ready.head();
			}
		}

	}

}