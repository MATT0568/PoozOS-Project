import java.util.PriorityQueue;

public class EventQueue {
	PriorityQueue events;
		public EventQueue(){
			events = new PriorityQueue();
		}
		
		public void add(ECB ECB){
			events.add(ECB);
		}
		
		public void remove(ECB ECB){
			events.remove(ECB);
		}
		
}
