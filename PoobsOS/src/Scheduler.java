import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class Scheduler {

	public static HashMap<Integer, Process> processes;
	public static HashMap<Integer, Long> cpuTime;
	public static HashMap<Integer, Long> waitingTime;
	public static HashMap<Integer, Long> responseTime;
	public static HashMap<Integer, Long> queueEntry;

	// private static ExecutionQueue ready;

	private static ArrayList<PCB> ready;
	private static int pIndex;

	private static List<PCB> waiting;
	private static Interpreter interp = Interpreter.getInstance();;

	private static int quantum = 10;
	private static CPU cpu = CPU.getInstance();
	private static Clock clock = Clock.getInstance();
	private static Memory ram = Memory.getInstance();
	private static PCB cpr = null;
	private static Scheduler instance = new Scheduler();
	private IOBurst burst = new IOBurst();

	private Scheduler() {
		processes = new HashMap<Integer, Process>();
		cpuTime = new HashMap<Integer, Long>();
		waitingTime = new HashMap<Integer, Long>();
		responseTime = new HashMap<Integer, Long>();
		queueEntry = new HashMap<Integer, Long>();

		// ready = new ExecutionQueue();
		ready = new ArrayList<PCB>();
		pIndex = 0;
		waiting = new LinkedList<PCB>();
		interp = Interpreter.getInstance();
	}

	public static Scheduler getInstance() {
		return instance;
	}

	public void insertPCB(Process p) {
		PCB pcb = new PCB(p);

		// If memory is available, add to ready queue
		if (ram.getAvailableMemory() - p.getRequiredMemory() >= 0) {
			ready.add(pcb);
			System.out.println("Programs in the ready queue: " + ready.size());
			processes.put(p.getPID(), p);
			ram.allocateMemory(p.getRequiredMemory());
			pcb.setState(State.READY);
		}

		// Otherwise add to wait queue
		else {
			System.out.println("Not enough RAM available. Storing program in wait queue");
			waiting.add(pcb);
			System.out.println("Programs in wait queue: " + waiting.size());
			pcb.setState(State.WAIT);
		}
	}

	// Remove a job from the ready queue
	public void removePCB(int PID) {
		PCB pcb = ready.remove(PID);
		ram.restoreMemory(pcb.getProcess().getRequiredMemory());
		processes.remove(PID);

	}

	public PCB nextProcess() {
		PCB pcb = null;
		pIndex++;
		if (ready.size() > pIndex) {
			pcb = ready.get(pIndex);
		}

		// If there are no processes to the left, loop back around
		else if (!ready.isEmpty()) {
			pIndex = 0;
			pcb = ready.get(pIndex);
		} else {
			return null;
		}

		return pcb;
	}
	
	public boolean hasNextProcess(){
		if (ready.size() == 1){
			return false;		
		}
		else if (pIndex < ready.size()-1){
			return true;
		}
		else if ((pIndex == ready.size()-1) && ready.size() > 1){
			return true;
		}
		return false;
	}

	// Set the time quantum for Round Robin
	public void setQuantum(int q) {
		quantum = q;
	}

	public void execute() {
		PCB cpr = null;
		int burstTime;
		ArrayList<String> commands = new ArrayList<String>();
		int quantumBase = 0;

		if (pIndex < ready.size()) {
			cpr = ready.get(pIndex);
			System.out.println("Loaded process id: " + cpr.getPID());
			commands = cpr.getProcess().getCommands();
		}

		do {		
			if (cpr.getCPUTime() > cpr.getAllottedTime()){
				cpr.setCommandTime(0);
				ready.remove(cpr);
				cpr.incrementIndex();
				cpr = nextProcess();
				continue;
				
			}
			else if (cpr.getCommandTime() > quantum && hasNextProcess()) {
				cpr.setCommandTime(0);
				cpr.setState(State.READY);
				ready.set(pIndex, cpr);
				cpr = nextProcess();
			}

			if (ready.size() == 0) {
				System.out.println("No processes loaded");
				break;
			}

			if (cpr.getState() == State.READY) {
				InstructTime line = interp.interpret(commands.get(cpr.getCommandIndex()));
				Instruct command = line.instruct;
				int time = line.time;
				cpr.setAllottedTime(time);

				if (cpr.getCPUTime() > cpr.getProcess().getRunTime()) {
					cpr.setState(State.EXIT);
				}

				switch (command) {
				case CALCULATE:
					System.out.println("Read a calculate command with runtime " + time + " :" + clock.getClock());
					cpr.setState(State.RUN);
					cpr.getProcess().setRunTime(time);
					break;
				case IO:
					System.out.println("Read an IO command: " + clock.getClock());
					cpr.setState(State.WAIT);
					burstTime = burst.generateIOBurst();
					System.out.println(burstTime);
					cpr.setWait(burstTime);
					break;
				case YIELD:
					System.out.println("Read a YIELD command");
					nextProcess();
					break;
				case OUT:
					System.out.println("Hit OUT command");
					break;
				default:
					break;
				}
			}

			if (cpr.getState() == State.RUN) {
				System.out.println("Entered RUN state");
				if (cpr.getCPUTime() > cpr.getProcess().getRunTime()) {
					if (cpr.getCommandIndex() < commands.size() - 1) {
						cpr.setState(State.READY);
						cpr.incrementIndex();
					} else {
						cpr.setState(State.EXIT);
					}
				} else {
					cpr.setCPUTime(cpr.getCPUTime() + 1);
					System.out.println("Running... : " + clock.getClock());
				}

			}

			if (cpr.getState() == State.WAIT) {
				if (cpr.getWait() <= 0) {
					System.out.println("Wait time is " + cpr.getWait());
					cpr.setState(State.READY);
					cpr.incrementIndex();;
				} else {
					System.out.println("Waiting... : " + clock.getClock());
					cpr.setWait(cpr.getWait() - 1);

				}
			}

			if (cpr.getState() == State.EXIT) {
				ready.remove(pIndex);
				System.out.println("Removed process: " + cpr.getPID());
				ram.restoreMemory(cpr.getProcess().getRequiredMemory());

				// If there is available memory, load a process from the wait
				// queue
				// into the ready queue;
				System.out.println("Available memory: " + ram.getAvailableMemory());

				if (!waiting.isEmpty()
						&& (ram.getAvailableMemory() > waiting.get(0).getProcess().getRequiredMemory())) {
					PCB newProcess = waiting.get(0);
					ready.add(newProcess);
					System.out.println("Added process: " + newProcess.getProcess().getPID());
					ram.allocateMemory(newProcess.getProcess().getRequiredMemory());
					waiting.remove(0);
				}

				cpr = nextProcess();
			}
			
			cpu.advanceClock();
			cpr.setCommandTime(cpr.getCommandTime()+1);

		} while (cpr != null);

	}

	public static ArrayList<PCB> getReadyQueue() {
		return ready;
	}

	public static List<PCB> getWaitingQueue() {
		return waiting;
	}

}