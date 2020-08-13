
public class Capability {
	public Task canRunTask;
	public String name;
	
	public Capability (String n, Task t) {
		this.name = n;
		this.canRunTask = t;
	}
	
	public String getName() {
		   return this.name;
	}
	
	public String getCanRunTask() {
		   return this.canRunTask.getName();
	}
	
	
}
