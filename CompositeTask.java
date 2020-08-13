
public class CompositeTask {
	public String name;
	public String ID;
	public Task[] tasks;
	
	
	public CompositeTask (String n, String id, Task[] t) {
		this.name = n;
		this.ID = id;
		this.tasks = t;//new Task[t.length];
		System.arraycopy(this.tasks, 0, t, 0, t.length);
	}
	
	// Return name
	public String getName() { return this.name; }
	
	public String getID() { return this.ID; }
	
	public Task[] getTasks() { return this.tasks; }
	
	public int getNumberTasks() {return this.tasks.length; }
	
}
