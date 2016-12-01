import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {
	private static Interpreter instance = new Interpreter();
	
	private Interpreter(){

	}
	
	public static Interpreter getInstance(){
		return instance;
	}
	
	public InstructTime interpret(String line){
		String[] command = line.split(" ");
		switch (command[0].toUpperCase()){
			case "CALCULATE":
				if (command.length > 1){
					int time = Integer.parseInt(command[1]);
					return new InstructTime(Instruct.CALCULATE, time);
				}			
			case "OUT":
				return new InstructTime(Instruct.OUT, -1);
			case "I/O":
				return new InstructTime(Instruct.IO, -1);
			case "YIELD":
				return new InstructTime(Instruct.YIELD, -1);
			default: return null;
		}
	}
}
