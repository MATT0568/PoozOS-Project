import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Loader {
	
	private static Loader instance = new Loader();
	private Scheduler sched = Scheduler.getInstance();
	private CPU cpu = CPU.getInstance();
	private Memory ram = Memory.getInstance();
	
	private Loader(){
	}
	
	public static Loader getInstance(){
		return instance;
	}
	
	public void load(String location, int startTime) throws IOException{
		System.out.println("Loading program \"" + location + "\"");
		
		FileReader in = new FileReader(location + ".txt");
		BufferedReader br = new BufferedReader(in);
		
		int memReq = Integer.parseInt(br.readLine());
		Process newProcess = new Process(memReq, startTime);
		
		
		String line;
		while ((line = br.readLine()) != null){
			newProcess.addCommand(line);
		}
		
		br.close();
		
		sched.insertPCB(newProcess);
		
		
		System.out.println(newProcess);
		System.out.println(ram.getAvailableMemory());
	}
}
