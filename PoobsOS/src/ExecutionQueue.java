// This is a circular linked list used for Round Robin
// It stores Process Control Blocks

public class ExecutionQueue {

	// Inner node class. Each element of the list is a node
	protected class Node {
		
		// Each node stores a pcb and a pointer to the next node
		private PCB pcb;
		public Node next;

		public Node() {
			pcb = null;
			next = null;
		}

		public Node(PCB pcb) {
			this.pcb = pcb;
		}

		public Node(PCB pcb, Node next) {
			this.pcb = pcb;
			this.next = next;
		}

		public PCB getPCB() {
			return pcb;
		}
	}

	protected Node head;
	protected Node tail;
	private int size;

	public ExecutionQueue() {
		head = null;
		tail = null;
		size = 0;
	}

	protected void setHead(PCB pcb) {
		// If there is no head, make new pcb into the new head
		if (head == null) {
			head = new Node(pcb);
			tail = head;
			tail.next = head;
			size++;
		}
		// Otherwise replace the head with the new pcb
		else {
			head = new Node(pcb, head);
			tail.next = head;
		}
	}

	public void enQueue(PCB pcb) {
		// If head is null, make new pcb the new head
		if (head == null) {
			setHead(pcb);
		}

		// Otherwise set the new pcb to be the tail
		else {
			tail.next = new Node(pcb, head);
			tail = tail.next;
			size++;
		}
	}

	public PCB deQueue() {
		if (tail == null && head == null) {
			return null;
		}

		else {
			Node temp = head;

			// If there is only one pcb, make both head and tail null
			if (head == tail) {
				tail = null;
				head = null;
			}

			// Otherwise remove the head
			else {
				head = head.next;
				tail.next = head;
			}

			size--;

			// Return the removed head
			return temp.getPCB();
		}
	}

	public PCB find(int PID){
		if(head == null){ return null;	}
		//We have a list:
		Node node = head;
		if(head == tail){
			//Special case of 1 list item
			if(head.getPCB().getPID()==PID){
				return head.getPCB();
			}else{
				return null;
			}
		}
		//For when we have items in the list
		for(;node != tail; node = node.next){
			if(node.getPCB().getPID()==PID){
				return node.getPCB();
			}
		}
		//Check the tail becuase the above traversel will not.
		if(tail.getPCB().getPID()==PID){
			return node.getPCB();
		}
		return null;
	}

	public PCB remove(int PID) {
		PCB pcb = null;
		// If queue is empty
		if (isEmpty()) {
			return pcb;
		}

		// If queue has one element
		if (head == tail) {
			if (head.getPCB().getPID() == PID) {
				pcb = head.getPCB();
				head = null;
				tail = null;
				size--;
				return pcb;
			} else {
				return pcb;
			}
		}

		// If PID is at head
		if (head.getPCB().getPID() == PID) {
			pcb = head.getPCB();
			head = head.next;
			tail.next = head;
			size--;
			return pcb;
		}

		else {
			Node current = head;
			Node previous = null;
			boolean found = false;

			while (current.getPCB().getPID() != PID && current.next != head) {
				previous = current;
				current = current.next;
			}

			if (current.getPCB().getPID() == PID) {
				pcb = current.getPCB();
				if (current == tail) {
					previous.next = head;
					tail = previous;
				}

				else {
					previous.next = current.next;
				}
				size--;
				return pcb;
			}

			else {
				return pcb;
			}
		}
	}

	// True
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getSize() {
		return size;
	}
	
	public String toString(){
		if (!isEmpty()) {
			StringBuilder str = new StringBuilder();
			Node current = head;
			while (true) {
				str.append(current.getPCB() + "; ");
				current = current.next;
				if (current == head)
					break;
			}
			return str.toString();
		}
		return "";
	}
	public void printQueue() {
		if (!isEmpty()) {
			StringBuilder str = new StringBuilder();
			Node current = head;
			while (true) {
				str.append(current.getPCB() + "; ");
				current = current.next;
				if (current == head)
					break;
			}
			System.out.println(str.toString());
		}
	}
}