import java.util.ArrayList;
import java.util.Collections;


public class RoundRobin<T> {
	private ArrayList<T> list;
	
	public RoundRobin(){
		list = new ArrayList<T>();
	}
	
	public void enQueue(T e){
		list.add(e);
	}
	
	public T deQueue(){
		if (!list.isEmpty()){
			list.remove(0);
		}
		return null;
	}
	
	public boolean hasNext(){
		return list.size() > 1;
	}
	
	public T next(){
		Collections.rotate(list, -1);
		return list.get(0);
	}
	
	public T head(){
		if (!list.isEmpty()){
			return list.get(0);
		}
		else return null;
	}
	
	public int size(){
		return list.size();
	}
	
	public boolean isEmpty(){
		return isEmpty();
	}
}
