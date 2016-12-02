import static org.junit.Assert.*;

import org.junit.Test;

public class SchedulerTest {

	@Test
	public void test() {
		try {
			Scheduler sched = Scheduler.getInstance();
			Loader loader = Loader.getInstance();

			loader.load("program1", 0);
			loader.load("program2", 0);
			
			sched.execute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
