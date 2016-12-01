import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CommandInterface implements ActionListener, Runnable{
	FlowLayout console_layout = new FlowLayout();
	protected JTextArea command_location;
	protected JTextField command_pane;
	protected JTextArea command_history;
	private Loader loader = Loader.getInstance();
	private static Scheduler sched = Scheduler.getInstance();
	
	public CommandInterface(){
		run();
	}
	//set up swing
	public void run(){
		
		JFrame frame = new JFrame("Command Interface");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(1280, 640));
		frame.getContentPane().add(content, BorderLayout.CENTER);
		
		JPanel console = new JPanel();
		console.setLayout(new BoxLayout(console, BoxLayout.Y_AXIS));
		
		frame.add(console, BorderLayout.PAGE_END);

		command_location = new JTextArea("~/USER");
		command_location.setBackground(java.awt.Color.black);
		command_location.setForeground(java.awt.Color.green);
		command_location.setEditable(false);
		command_location.setBorder(null);
		
		console.add(command_location);

		command_history = new JTextArea();
		command_history.setColumns(100);
		command_history.setBorder(null);
		command_history.setBackground(java.awt.Color.black);
		command_history.setForeground(java.awt.Color.green);
		command_history.setEditable(false);
		JScrollPane scroll = new JScrollPane(command_history);
		scroll.setPreferredSize(new Dimension(1280, 100));
		scroll.setBorder(null);
		
		console.add(scroll);
		
		command_pane = new JTextField();
		command_pane.addActionListener(this);
		command_pane.setColumns(100);
		command_pane.setBorder(null);
		command_pane.setBackground(java.awt.Color.black);
		command_pane.setForeground(java.awt.Color.green);
		console.add(command_pane);

		frame.pack();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
        String text = command_pane.getText();
        command_history.append(text + "\n");
        command_history.selectAll();
        command_history.setCaretPosition(command_history.getDocument().getLength());
        command_pane.setText(null);

		Terminal(text);
    }
	

	public void Terminal(String text){
		String[] command = text.split(" ");
		switch (command[0].toUpperCase()){
			case "PROC":
				break;
			case "MEM":
				break;
			case "LOAD":
				String location = null;
				String time = null;
				if (command.length > 2){
					location = command[1];
					time = command[2];
				}
				if (location != null && time != null){
					try {
						int startTime = Integer.parseInt(time);
						loader.load(location, startTime);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			case "EXE":
				sched.execute();
				break;
			case "RESET":
				break;
			case "EXIT":
				System.exit(0);
				break;
			default:break;
		}
	}
	
	
}
